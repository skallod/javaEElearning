package ru.galuzin.concurrent_test;

import java.util.concurrent.*;

/**
 * Created by galuzin on 26.05.2017.
 */
public class ExecutorsTest {

    public static void main(String[] args) throws InterruptedException {

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        System.out.println("gal start");
        Thread.sleep(120_000);
        executor.submit(()-> sleep(60_000));//start grow
        executor.submit(()-> sleep(60_000));
        executor.submit(()-> sleep(60_000));
        executor.submit(()-> sleep(60_000));
        executor.submit(()-> sleep(60_000));
        executor.submit(()-> {
            while(true) {
                sleep(60_000);//other threads kill after some time
            }
        });
    }

    private static void sleep(long time) {
        System.out.println("gal thread "+Thread.currentThread().getId());
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gal thread finishg"+Thread.currentThread().getId());
    }

//    @Override
//    public <T> Future<T> submit(Callable<T> task) {
//        return target.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
//    }
//
//    private <T> Callable<T> wrap(final Callable<T> task, final Exception clientStack, String clientThreadName) {
//        return () -> {
//            try {
//                return task.call();
//            } catch (Exception e) {
//                log.error("Exception {} in task submitted from thrad {} here:", e, clientThreadName, clientStack);
//                throw e;
//            }
//        };
//    }




}
