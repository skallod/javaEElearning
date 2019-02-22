package ru.galuzin.concurrent_test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    //private static final Logger log = LoggerFactory.getLogger(TimerTest.class);
    //@Test
    public void testTask() {
        tt();
    }

    public static void main(String[] args) {
        tt();
    }

    private static void tt() {
        System.out.println("main start");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId()+" task 1 start");
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId()+" task 2 start");
            }
        };
        Timer timer = new Timer(false);
        timer.schedule(timerTask,2000,2000);
        timer.schedule(timerTask2,1000,1000);
    }
}
