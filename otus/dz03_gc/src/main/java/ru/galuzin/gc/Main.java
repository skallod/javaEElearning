package ru.galuzin.gc;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        log.info("gc test start");
        SharedObject sharedObject = new SharedObject();
        GcNotification.installGCMonitoring(sharedObject);
        try(ReportMaker reportMaker = new ReportMaker(sharedObject)) {
            reportMaker.start();
            startMemoryFilling();
        }finally {
            log.info("=====================");
            log.info("out of memory time "+TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-startTime));

        }
    }

    static void startMemoryFilling() throws InterruptedException {
        ArrayList<MyObject> myObjects = new ArrayList<>();
        Random random = new Random();
        while (true){
            for (int i = 0; i < 1000; i++) {
                myObjects.add(new MyObject(new String(new char[0])
                        ,random.nextInt(),random.nextDouble(),random.nextDouble()));
            }
            TimeUnit.MILLISECONDS.sleep(25);
            int size = myObjects.size();
            for (int j = size-500; j < size; j++) {
                myObjects.set(j,null);
            }
        }
    }
}
