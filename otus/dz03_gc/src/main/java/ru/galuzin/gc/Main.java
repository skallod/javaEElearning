package ru.galuzin.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        log.info("gc test start");
        GcNotification.installGCMonitoring();
        //info();
        try {
            startMemoryFilling();
        }finally {
            log.info("out of memory time "+TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-startTime));
        }
    }

    static void info(){
        Thread memInfoT = new Thread(() -> {
            while (true) {
                try {
                    memInfo();
                    gcInfo();
                    TimeUnit.SECONDS.sleep(20);
                } catch (Exception e) {
                    log.error("dump threads ", e);
                }
            }
        });
        memInfoT.setDaemon(true);
        memInfoT.setName("info");
        memInfoT.start();
    }

    static void gcInfo(){

    }

    private static void memInfo() {
        final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        final MemoryUsage heap = memBean.getHeapMemoryUsage();
        final MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();
        log.info("==================================== Mem usage ==========================");
        // init: Amount of memory in bytes that the JVM initially requests from the OS.
        // used: Amount of memory used.
        // committed: Amount of memory that is committed for the JVM to use.
        // max: Maximum amount of memory that can be used for memory management.
        log.info(String.format("Heap: Used: %d, Committed: %d",heap.getUsed(), heap.getCommitted()));
        log.info(String.format("Non-Heap: Used: %d, Committed: %d",nonHeap.getUsed(), nonHeap.getCommitted()));
    }

    static void startMemoryFilling() throws InterruptedException {
        ArrayList<MyObject> myObjects = new ArrayList<>();
        Random random = new Random();
        while (true){
            for (int i = 0; i < 1000; i++) {
                myObjects.add(new MyObject(new String(new char[0])
                        ,random.nextInt(),random.nextDouble(),random.nextDouble()));
            }
            int size = myObjects.size();
            for (int j = size-500; j < size; j++) {
                myObjects.set(j,null);
            }
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}
