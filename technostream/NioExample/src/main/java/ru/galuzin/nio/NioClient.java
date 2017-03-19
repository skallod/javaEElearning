package ru.galuzin.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.*;

/**
 *
 */
public class NioClient {
    private static final Logger LOG= LoggerFactory.getLogger(NioClient.class);
    public static final int PORT = 9090;
    public static final String ADDRESS = "localhost";
    static String clientName;
    private ByteBuffer buffer = allocate(16);

    private void run() throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, OP_CONNECT);
        channel.connect(new InetSocketAddress(ADDRESS, PORT));
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            //while (true) {
            for (int i=0; i<2; i++){
                //String line = scanner.nextLine();
                String line = "client ";
                LOG.info("line got");
//                if ("q".equals(line)) {
//                    System.exit(0);
//                }
                try {
                    queue.put(line);
                    LOG.info("puted in queue");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SelectionKey key = channel.keyFor(selector);
                key.interestOps(OP_WRITE);
                LOG.info("interested");
                selector.wakeup();
                LOG.info("wakeup");
                try {
                    Thread.sleep(1_000);
                    LOG.info("sleep ended");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (true) {
            LOG.info("before select");
            selector.select();
            LOG.info("after select");
            for (SelectionKey selectionKey : selector.selectedKeys()) {
                LOG.info("for cicle");
                try {
                    if (selectionKey.isConnectable()) {
                        LOG.info("is connectable");
                        channel.finishConnect();
                        selectionKey.interestOps(OP_WRITE);
                        LOG.info("connectable finish");
                    } else if (selectionKey.isReadable()) {
                        LOG.info("is readable");
                        buffer.clear();
                        channel.read(buffer);
                        LOG.info("Recieved = " + new String(buffer.array()));
                    } else if (selectionKey.isWritable()) {
                        LOG.info("is writable");
                        String line = queue.poll();
                        if (line != null) {
                            LOG.info("line not null");
                            channel.write(ByteBuffer.wrap(line.getBytes()));
                        }
                        selectionKey.interestOps(OP_READ);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        clientName = args[0];
        new NioClient().run();
    }
}
