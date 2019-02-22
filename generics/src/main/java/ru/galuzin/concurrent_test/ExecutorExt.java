package ru.galuzin.concurrent_test;

import ru.galuzin.generics.ValueHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * grow immediatly, but not more than maximumPoolSize
 * @param <T>
 */
public class ExecutorExt<T> {

    BlockingQueue<Callable<T>> queue = new LinkedBlockingQueue<>(10);

    Thread processingThread;

    ExecutorExt(){
        processingThread = new Thread(() ->
        {
            boolean interrupted = false;
            ValueHolder<Boolean> rejectedVh = new ValueHolder<>(false);
            ExecutorService es = new ThreadPoolExecutor(1, 3, 10, TimeUnit.MINUTES
                    , new SynchronousQueue<Runnable>(), (Runnable r, ThreadPoolExecutor executor)->rejectedVh.setValue(true));
            Callable<T> r = null;
            try {
                do {
                    //System.out.println("before take");
                    try {
                        r = queue.take();
                        //System.out.println("after take");
                        es.submit(r);
                        //System.out.println("after submit");
                        while (rejectedVh.getValue() && !Thread.currentThread().isInterrupted()) {
                            TimeUnit.MILLISECONDS.sleep(50);
                            rejectedVh.setValue(false);
                            es.submit(r);
                            //System.out.println("another try");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("WARN processing queue interrupted");
                        interrupted = true;
                    }
                } while (!(interrupted||Thread.currentThread().isInterrupted()));
            }finally {
                if(interrupted){
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("shutdown");
            es.shutdownNow();
            if(rejectedVh.getValue()) {
                System.out.println("warn lost rejected "+r.toString());
            }
            queue.forEach(qe -> System.out.println("warn lost queue element "+qe.toString()));
        });
        //processingThread.setDaemon(true);
        processingThread.setName("queue processing");
        processingThread.start();
    }

    void submit(Callable<T> r){
        try {
            queue.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void shutdown(){
        processingThread.interrupt();
    }

    public static void main(String[] args) {
        System.out.println("processors "+Runtime.getRuntime().availableProcessors());
        ExecutorExt<Double> ext = new ExecutorExt<>();
        for(int i =0 ;i <12; i++){
            final int idx = i;
            ext.submit(()->{
                double v = ThreadInterrupt.longOper();
                System.out.println("handled = " + idx);
                return v;
            });
            System.out.println("submited i = " + i);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ext.shutdown();
    }
}