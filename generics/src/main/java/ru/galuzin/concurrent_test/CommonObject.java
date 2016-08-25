package ru.galuzin.concurrent_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by galuzin on 24.08.2016.
 */
public class CommonObject {

    private static volatile CommonObject instance = new CommonObject();

    private static AtomicReference<CommonObject> ar = new AtomicReference<>(instance);

    private CommonObject(){

    }

    public static CommonObject getInstance(){
        return instance;
    }

    public String name;
    public String soname;
    public int passNumb;
    private long time;
    private CountDownLatch cdl = new CountDownLatch(3);
    //private Semaphore smph = new Semaphore();

    public void setParams(String name, String soname){
        //smph.
//        cdl.countDown();
//        try {
//            cdl.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(Thread.currentThread().getName());
        this.name = name;
        this.soname = soname;
        time = System.currentTimeMillis();
        double result= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        for(int i=0; i<10000;i++){
            result+= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        }
    }

    private void part2(int passNumb){
        this.passNumb = passNumb;
        System.out.println(String.format("%s , %d",this.toString(),System.currentTimeMillis() - time));
//        if(cdl.getCount()==0){
//            double result=0;
//            for(int i=0; i<10000;i++){
//                result+= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
//            }
//            cdl = new CountDownLatch(3);
//            System.out.println("new count down");
//        }
    }

    @Override
    public String toString() {
        return "CommonObject{" +
                "name='" + name + '\'' +
                ", soname='" + soname + '\'' +
                ", passNumb=" + passNumb +
                '}';
    }
}
