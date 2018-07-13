package ru.galuzin.gc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportMaker implements AutoCloseable{
    final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    long prevMinorDuration =0;
    long prevMinorCount =0;
    long prevMajorDuration =0;
    long prevMajorCount =0;
    SharedObject sharedObject;

    ReportMaker(SharedObject so){
        this.sharedObject = so;
    }

    void start(){
        schedule.scheduleAtFixedRate(()->{
            SharedObject object = sharedObject.getObject();
            log.info("===========================minute left===================================");
            //TODO heap usage percent
            log.info("minor count per minute "+(object.getMinorFullCounts()- prevMinorCount));
            prevMinorCount = object.getMinorFullCounts();
            log.info("minor duration per minute "+(object.getMinorFullDuration()- prevMinorDuration));
            log.info("minor duration percent per minute "+percentagePerMinute(object.getMinorFullDuration()- prevMinorDuration));
            prevMinorDuration = object.getMinorFullDuration();

            log.info("major count per minute "+(object.getMajorFullCounts()- prevMajorCount));
            prevMajorCount = object.getMajorFullCounts();
            log.info("mijor duration per minute "+(object.getMajorFullDuration()- prevMajorDuration));
            log.info("mijor duration percent per minute "+percentagePerMinute(object.getMajorFullDuration()- prevMajorDuration));
            prevMajorDuration = object.getMajorFullDuration();

        },1,1, TimeUnit.MINUTES);
    }

    String percentagePerMinute(long durationPerMinute){
        long temp = durationPerMinute*1000L/60_000L;
        return "" + temp/10L + "." + temp%10L;
    }

    @Override
    public void close() throws Exception {
        schedule.shutdownNow();
    }
}
