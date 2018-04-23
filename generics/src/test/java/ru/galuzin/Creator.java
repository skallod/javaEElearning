package ru.galuzin;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Creator {
    public static void main(String[] args) throws Exception{
        Creature crt = new Creature();
        AtomicInteger longAdder = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            crt.add(()->{
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " ready "+longAdder.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            });
        }
        System.out.println("main.finish");
        crt.dispose();
    }
}
class Creature {
    private static long numCreated = 0;
    ThreadPoolExecutor pool;
    LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(105);
    int counter;
    public Creature() {
        pool = new ThreadPoolExecutor(1, 5, 1L, TimeUnit.MINUTES, new SynchronousQueue()
                , new ThreadFactory() {
            AtomicLong threadNumber = new AtomicLong();
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("test-pool-"+threadNumber.getAndIncrement());
                //thread.setDaemon(true);
                return thread;
            }}
        ,(Runnable r, ThreadPoolExecutor executor) ->{
            System.out.println(Thread.currentThread().getName() + "rejected r = " + r + "ex = "+executor);
            try {
                //System.out.println("reject");
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        );
        //pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        new Thread(()->{
            while (true){
                try {
                    Runnable take = queue.take();
                    pool.submit(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        numCreated++;
    }
    public static long numCreated() {
        return numCreated;
    }

    public void add(Runnable r) throws Exception{
        System.out.println("add");
        queue.put(r);
        System.out.println("counter = " + (counter++));
    }
    void dispose(){
        pool.shutdown();
    }
}
