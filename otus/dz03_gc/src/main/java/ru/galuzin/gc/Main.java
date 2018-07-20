package ru.galuzin.gc;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        long appStartTime = System.nanoTime();
        log.info("gc test start");
        GcStatistics sharedObject = new GcStatistics();
        GcNotification.installGCMonitoring(sharedObject);
        try(ReportMaker reportMaker = new ReportMaker(sharedObject,appStartTime)) {
            reportMaker.start();
            startMemoryFilling();
        }finally {
            log.info("=====================");
            log.info("out of memory time sec "+TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-appStartTime));

        }
    }

    static void startMemoryFilling() throws InterruptedException {
        ArrayList<MyObject> myObjects = new ArrayList<>();
        Random random = new Random();
        while (true){
            for (int i = 0; i < 500; i++) {
                myObjects.add(new MyObject(new String(new char[0])
                        ,random.nextInt(),random.nextDouble(),random.nextDouble()));
            }
            TimeUnit.MILLISECONDS.sleep(30);
            int size = myObjects.size();
            for (int j = size-250; j < size; j++) {
                myObjects.set(j,null);
            }
        }
    }
}
