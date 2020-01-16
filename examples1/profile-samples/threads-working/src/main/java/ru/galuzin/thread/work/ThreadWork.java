package ru.galuzin.thread.work;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadWork {

    public static void main(String [] arg){
        System.out.println("test");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
                BigDecimal v = longOper();
                System.out.println("v = " + v);
            },10, 50, TimeUnit.MILLISECONDS);
    }

    static BigDecimal longOper(){
        long timeMillis = System.currentTimeMillis();
        BigDecimal newResult = BigDecimal.ZERO;
        for(int i=0; i<1000;i++){
            newResult=newResult.add(new BigDecimal(Math.random(), MathContext.DECIMAL128).multiply(BigDecimal.valueOf(Math.PI))
                    .divide(BigDecimal.valueOf(Math.random()),30,1));
        }
        System.out.println(Thread.currentThread().getId()+" timeMillis = " + (System.currentTimeMillis() - timeMillis));
        return newResult;
    }
}
