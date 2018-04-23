package ru.galuzin;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

public class QueueTest {
    @Test
    public void test1() {
        final SynchronousQueue<String> strings = new SynchronousQueue<>();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(()->{
            for (int i=0; i<10; i++){
                String s = UUID.randomUUID().toString();
                System.out.println("before add "+s);
                boolean offer = false;
                try {
                    strings.put(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("after add "+s + " done "+offer);
            }
        }).start();
        new Thread(()->{
            try {
                String poll = strings.take();
                System.out.println("poll = " + poll);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    @Test
    public void test2() {
        final BlockingQueue<String> strings = new ArrayBlockingQueue<String>(5);
        new Thread(()->{
            for (int i=0; i<10; i++){
                String s = UUID.randomUUID().toString();
                System.out.println("before add "+s);
                boolean offer = false;
                try {
                    strings.put(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("after add "+s + " done "+offer);
            }
        }).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new Thread(()->{
//            try {
//                String poll = strings.take();
//                System.out.println("poll = " + poll);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
    }
}
