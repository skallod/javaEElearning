package ru.galuzin.concurrent_test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * grow immediatly,
 * @param <T>
 */
public class ExecutorExt<T> {
    BlockingQueue<Callable<T>> queue = new LinkedBlockingQueue<>(10);
    boolean rejected = false;
    ExecutorService es = new ThreadPoolExecutor(1, 3, 10, TimeUnit.MINUTES
            , new SynchronousQueue<Runnable>(), (Runnable r, ThreadPoolExecutor executor)->rejected = true);
    Thread processingThread;
    volatile boolean finish = false;

    ExecutorExt(){
        processingThread = new Thread(() ->
        {
            Callable<T> r = null;
            do {
                try {
                    //System.out.println("before take");
                    r = queue.take();
                    //System.out.println("after take");
                    es.submit(r);
                    //System.out.println("after submit");
                    while (rejected && !finish){
                        TimeUnit.MILLISECONDS.sleep(100);
                        rejected = false;
                        es.submit(r);
                        //System.out.println("another try");
                    }
                } catch (InterruptedException e) {
                    System.out.println("WARN processing queue interrupted");
                }
            } while (!finish);
            System.out.println("shutdown");
            es.shutdownNow();
            if(rejected) {
                System.out.println("warn lost rejected "+r.toString());
            }
            queue.forEach(qe -> System.out.println("warn lost queue element "+qe.toString()));
        });
        //processingThread.setDaemon(true);
        processingThread.setName("queue processing");
        processingThread.start();
    }

    void receive(Callable<T> r){
        try {
            queue.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void shutdown(){
        finish=true;
        processingThread.interrupt();
    }

    public static void main(String[] args) {
        System.out.println("processors "+Runtime.getRuntime().availableProcessors());
        ExecutorExt<Double> ext = new ExecutorExt<>();
        for(int i =0 ;i <12; i++){
            final int idx = i;
            ext.receive(()->{
                double v = ThreadInterrupt.longOper();
                System.out.println("handled = " + idx);
                return v;
            });
            System.out.println("received i = " + i);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ext.shutdown();
    }
}
