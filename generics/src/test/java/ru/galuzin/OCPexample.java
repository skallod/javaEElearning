package ru.galuzin;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class OCPexample {
    public static void main(String[] args) {
        System.out.println("start");
        CyclicBarrier cb = new CyclicBarrier(3,()-> System.out.println("clean"));
        ExecutorService service = Executors.newScheduledThreadPool(3);
        IntStream.iterate(1,i->1).limit(12)
                .forEach(i-> service.submit(()->await(cb)));
        service.shutdown();
    }

    private static void await(CyclicBarrier cb) {
        try{
            cb.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
