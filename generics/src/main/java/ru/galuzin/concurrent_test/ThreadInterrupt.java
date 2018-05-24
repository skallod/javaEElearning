package ru.galuzin.concurrent_test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by galuzin on 12.04.2017.
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws Exception {
//        {//case1 simple thread interrupt
//            Thread thread1 = new Thread(() -> {
//                System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread().isInterrupted());
//                do {
//                    longOper();
//                } while (true);//(!Thread.currentThread().isInterrupted());
//                //System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread().isInterrupted());
//            });
//            thread1.start();
//            try {
//                Thread.sleep(1000);
//                thread1.interrupt();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        {//case2 executor shutdown

            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<String> submit = executorService.submit(new Collable1());
            executorService.shutdown();
//            try {
//                Thread.sleep(10000);
//                //submit.cancel(true);
//                executorService.shutdown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        //    System.out.println("submit.get() = " + submit.get());
//            executorService.submit(new Collable1());
        }
    }

    static double longOper(){
        long timeMillis = System.nanoTime();
        double result= 0;
        for(int i=0; i<10000000;i++){
            result+= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        }
        System.out.println("timeMillis = " + (System.nanoTime() - timeMillis));
        return result;
    }

    static class Collable1 implements Callable<String>{
        //T x;
        @Override
        public String call() throws Exception {
            System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread().isInterrupted());
            int i=0;
            do {
                longOper();
                i++;
            }while
                (i<1);
//                (!Thread.currentThread().isInterrupted());
            //System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread().isInterrupted());
            return "gfad fgs";
        }
    }
}
