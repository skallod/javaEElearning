package ru.galuzin.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemInfo {

    static void info(){
        Thread memInfoT = new Thread(() -> {
            while (true) {
                try {
                    memInfo();
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


    static void memInfo() {
        final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        final MemoryUsage heap = memBean.getHeapMemoryUsage();
        //final MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();
        //log.info("==================================== Mem usage ==========================");
        // init: Amount of memory in bytes that the JVM initially requests from the OS.
        // used: Amount of memory used.
        // committed: Amount of memory that is committed for the JVM to use.
        // max: Maximum amount of memory that can be used for memory management.
        log.info("Heap: Used percent: " + percentage(heap.getUsed(), heap.getCommitted()));
        //log.info(String.format("Non-Heap: Used: %d, Committed: %d",nonHeap.getUsed(), nonHeap.getCommitted()));
    }

    static String percentage(long used, long all){
        long temp = used*1000L/all;
        return "" + temp/10L + "." + temp%10L;
    }
}
