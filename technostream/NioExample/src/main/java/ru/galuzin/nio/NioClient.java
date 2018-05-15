package ru.galuzin.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 *
 */
public class NioClient {
    private static final Logger LOG= LoggerFactory.getLogger(NioClient.class);
    public static final int PORT = 9999;
    public static final String ADDRESS = "localhost";
    static String clientName;
    private ByteBuffer buffer = allocate(16);

    private void run() throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, OP_READ+OP_WRITE+SelectionKey.OP_CONNECT);
        channel.connect(new InetSocketAddress(ADDRESS, PORT));
        LOG.info("channel = "+channel);
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                for (int i = 0; i < 2; i++) {
                    String line = scanner.nextLine();
                    //String line = "client ";
                    LOG.info("line got");
                    if ("q".equals(line)) {
                        System.exit(0);
                    }
                    try {
                        queue.put(line);
                        LOG.info("puted in queue");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SelectionKey key = channel.keyFor(selector);
                    key.interestOps(OP_WRITE);
                    selector.wakeup();
                    try {
                        Thread.sleep(1_000);
                        LOG.info("sleep ended");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        while (true) {
            //LOG.info("before select");
            selector.select();
            //LOG.info("after select");
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                LOG.info("keys "+selector.selectedKeys().stream().map(key->key.readyOps()).collect(Collectors.toList()));
                LOG.info("keys "+selector.selectedKeys().stream().map(key->key.interestOps()).collect(Collectors.toList()));
                SelectableChannel channelFromKey = selectionKey.channel();
                if(channelFromKey!=channel){
                    LOG.info("DIFFERENT CHANNELS");
                }
                //System.out.println("channelFromKey = " + channelFromKey);
                try {
                    if (selectionKey.isConnectable()) {
                        LOG.info("is connectable");
                        channel.finishConnect();
                        iterator.remove();
                    } else if (selectionKey.isReadable()) {
                        LOG.info("is readable");
                        //buffer.clear();
                        if (channel.read(buffer)!=-1) {
                            buffer.flip();
                            byte[] bytes = Arrays.copyOfRange(buffer.array(), buffer.position(), buffer.remaining());
                            if (bytes.length>0){
                                LOG.info("Recieved = " + new String(bytes));
                            }
                            // buffer.compact(); not work hear
                            buffer.clear();
                        }
                        //else {
                        //    selectionKey.interestOps(OP_WRITE);
                        //}
                    } else if (selectionKey.isWritable()) {
                        //LOG.info("is writable");
                        String line = queue.poll();
                        if (line != null) {
                            //LOG.info("line not null");
                            channel.write(ByteBuffer.wrap(line.getBytes()));
                            LOG.info("sended");
                        }else{
                            channel.write(ByteBuffer.wrap("first line".getBytes()));
                            LOG.info("first line");
                        }
                        //iterator.remove();
                        selectionKey.interestOps(OP_READ);
                        //selector.wakeup();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length!=0)clientName = args[0];
        else clientName= UUID.randomUUID().toString()+"name";
        new NioClient().run();
    }
}
