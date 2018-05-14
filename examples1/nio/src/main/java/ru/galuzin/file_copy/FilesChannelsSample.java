package ru.galuzin.file_copy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class FilesChannelsSample {

    public static void main(String[] args) throws IOException {
        long time = System.nanoTime();
        //chan();
        asyncChan();
        System.out.println("time = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time));
        try {
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void chan() throws IOException{
        FileChannel in = FileChannel.open(Paths.get("c:/dev/video/Angular Applied by Yakov Fain [720p].mp4"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("c:/dev/video/temp.mp4"), StandardOpenOption.WRITE
                ,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE);
        //ByteBuffer buffer = ByteBuffer.allocate(1024 * 16);//31403 mili sec
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 16);//5888, 33500 mili sec
        while (in.read(buffer)!=-1 || buffer.position()>0){
            buffer.flip();
            out.write(buffer);
            buffer.compact();
        }
    }

    static void asyncChan() throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(1024 * 16);
        AsynchronousFileChannel afcin = AsynchronousFileChannel.open(Paths.get("c:/temp/hotels/pricing-1504100588542.xml"), StandardOpenOption.READ);
        final AsynchronousFileChannel afcout = AsynchronousFileChannel.open(Paths.get("c:/temp/hotels/temp.xml"), StandardOpenOption.WRITE
                , StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        copyStep(afcin,afcout,buffer);
    }
    static int posread = 0;
    static int poswright = 0;
    static void copyStep(final AsynchronousFileChannel afcin, final AsynchronousFileChannel afcout, final ByteBuffer buffer){
        System.out.println("buffer pos b r = " + buffer.position());
        afcin.read(buffer, posread, afcout, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                if(result!=-1){
                    posread+=result;
                }else{
                    System.out.println("read -1");
                }
                if(result!=-1 || buffer.position()>0) {
                    buffer.flip();
                    System.out.println("flip "+Thread.currentThread().getName());
                    System.out.println("buffer pos b w = " + buffer.position());
                    afcout.write(buffer, poswright, afcin, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            if(result!=-1) {
                                poswright+=result;
                                buffer.compact();
                                //try {
                                //byte[] bytes = Arrays.copyOfRange(buffer.array(), buffer.position(), buffer.remaining());
                                //System.out.println("compact " + new String(bytes));
                                //} catch (UnsupportedEncodingException e) {
                                //    e.printStackTrace();
                                //}
                                copyStep(afcin, afcout, buffer);
                            }else {
                                System.out.println("write -1");
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            exc.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }
}
