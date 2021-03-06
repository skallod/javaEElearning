package ru.galuzin.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;
import static ru.galuzin.nio.NioClient.ADDRESS;
import static ru.galuzin.nio.NioClient.PORT;

public class NioServer {
    private static Logger log = LoggerFactory.getLogger(NioServer.class);
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(18192);
    private EchoWorker worker = new EchoWorker();
    private final List<ChangeRequest> changeRequests = new LinkedList<>();
    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    private NioServer() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(ADDRESS, PORT);
        serverChannel.socket().bind(isa);
        selector = SelectorProvider.provider().openSelector();
        serverChannel.register(selector, OP_ACCEPT);
        new Thread(worker).start();
    }

    public static void main(String[] args) throws IOException {
        Properties props = new java.util.Properties();
        props.load(NioServer.class.getResourceAsStream("/log4j.properties"));
        PropertyConfigurator.configure(props);
        new NioServer().run();
    }

    private void run() throws IOException {
        while (true) {
            log.info("changerequests before read critical");
            synchronized (changeRequests) {
                log.info("changerequests in read critical");
                for (ChangeRequest change : changeRequests) {
                    switch (change.type) {
                        case ChangeRequest.CHANGEOPS:
                            log.info("change ops");
                            SelectionKey key = change.socket.keyFor(selector);
                            key.interestOps(change.ops);
                            break;
                        default:
                    }
                }
                changeRequests.clear();
            }
            log.info("befor select");
            selector.select();
            log.info("after select");
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();
                if (!key.isValid()) {
                    continue;
                }
                try {
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    void send(SocketChannel socket, byte[] data) {
        log.info("before changeRequests write critical sec");
        synchronized (changeRequests) {
            log.info("in changeRequests write critical sec");
            changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, OP_WRITE));
            log.info("op_write added");
            synchronized (pendingData) {
                log.info("pending data write in critical sec");
                List<ByteBuffer> queue = pendingData.get(socket);
                if (queue == null) {
                    queue = new ArrayList<>();
                    pendingData.put(socket, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        log.info("before wakeup");
        selector.wakeup();
        log.info("after wakup");
    }

    private void accept(SelectionKey key) throws IOException {
        log.info("accept");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, OP_READ);
        log.info("accept end");
    }

    private void read(SelectionKey key) throws IOException {
        log.info("read");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        try {
            int numRead = socketChannel.read(readBuffer);
            if (numRead == -1) {
                socketChannel.close();
            }
            worker.processData(this, socketChannel, readBuffer.array(), numRead);
        }catch (IOException e){
            log.warn(e.getMessage());
            socketChannel.close();
        }
        log.info("read end");
    }

    private void write(SelectionKey key) throws IOException {
        log.info("write");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (pendingData) {
            log.info("pending data read in critical sec");
            List<ByteBuffer> queue = pendingData.get(socketChannel);
            while (!queue.isEmpty()) {
                ByteBuffer buf = queue.get(0);
                socketChannel.write(buf);
                if (buf.remaining() > 0) {
                    break;
                }
                log.info("Send echo = " + new String(queue.get(0).array()));
                queue.remove(0);
            }
            if (queue.isEmpty()) {
                key.interestOps(OP_READ);
            }
        }
        log.info("write end");
    }
}
