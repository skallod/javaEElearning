package ru.galuzin.completable_future;

import java.util.concurrent.atomic.AtomicInteger;

public class SleepJob {

    AtomicInteger result;

    public AtomicInteger getResult() {
        return result;
    }

    public void incrementResult() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.result.incrementAndGet();
    }
}
