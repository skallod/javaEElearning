package ru.galuzin.concurrent_test;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurHashMapTest {

    private static final ConcurrentHashMap<String, TaskStatus> chm = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, LocalDateTime> chm1 = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        chm.put("zone0", TaskStatus.STARTED);
        chm.computeIfPresent("zone0", (key, value)->{
            System.out.println("key = " + key);
            System.out.println("value = " + value);
            return TaskStatus.STARTED;
        });
//        chm.compute("zone1",(key, value)->{
//            if(value == null)
//        });
        if(needUpdate()){
            System.out.println("update");
        }
    }

    static boolean needUpdate(){
        final LocalDateTime ldt = chm1.get(1);
        return ldt == null || ldt.isBefore(LocalDateTime.now());
    }
}
enum TaskStatus {STARTED, PAUSED, FINISHED}
