package ru.galuzin.gc;

import java.util.concurrent.locks.ReentrantLock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SharedObject {
    private final ReentrantLock lock = new ReentrantLock();
    long minorFullDuration = 0;
    long minorFullCounts = 0;
    long majorFullDuration = 0;
    long majorFullCounts = 0;

    SharedObject getObject(){
        SharedObject result;
        lock.lock();
        result = new SharedObject(minorFullDuration,minorFullCounts
                ,majorFullDuration,majorFullCounts);
        lock.unlock();
        return result;
    }

    void updateMinor(long duration, long counts){
        lock.lock();
        this.minorFullDuration += duration;
        this.minorFullCounts = counts;
        lock.unlock();
    }

    void updateMajor(long duration, long counts){
        lock.lock();
        this.majorFullDuration += duration;
        this.majorFullCounts = counts;
        lock.unlock();
    }
}