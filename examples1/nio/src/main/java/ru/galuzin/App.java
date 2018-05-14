package ru.galuzin;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(3000));
        serverChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (true){
            int select = selector.select();
            if(select==0){
                continue;
            }
            for (Iterator<SelectionKey> iterator1 = selector.selectedKeys().iterator(); iterator1.hasNext(); ) {
                SelectionKey next = iterator1.next();
                try {
                    if (next.channel()==serverChannel){
                        SocketChannel acceptedChannel = serverChannel.accept();
                        acceptedChannel.configureBlocking(false);
                        acceptedChannel.register(selector,SelectionKey.OP_READ);
                        System.out.println("accepted");
                    }else {
                        SocketChannel channel = (SocketChannel) next.channel();
                        int readCount = channel.read(buffer);
                        if(readCount > 0) {
                            buffer.flip();
                            byte[] bytes = Arrays.copyOfRange(buffer.array(), buffer.position(), buffer.remaining());
                            System.out.println("bytes = " + new String(bytes));

                            //do {
                                channel.write(buffer);
                                System.out.println("write " + count++);
                                //count++;
                            //}while (buffer.hasRemaining());
                            //buffer.clear();
                            buffer.compact();
                        }else if(readCount == -1){
                            System.out.println("conn close");
                            channel.close();
                        }else {
                            System.out.println("something");
                        }
                    }
                }finally {
                    iterator1.remove();
                }
            }
        }
    }
}
