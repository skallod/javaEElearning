package ru.galuzin.gc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportMaker implements AutoCloseable{
    final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private final long appStartTime;
    GcStatistics gcStatistics;
    GcStatistics prevGcStatistics;
    private int i = 0;

    ReportMaker(GcStatistics so, long appStartTime){
        this.gcStatistics = so;
        this.prevGcStatistics = new GcStatistics();
        this.appStartTime = appStartTime;
    }

    void start(){
        schedule.scheduleAtFixedRate(()->{
            GcStatistics object = gcStatistics.cloneInstance();
            log.info("===========================minute left "+(++i)+"; ms "
                    +TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-appStartTime)+"==================================");
            MemInfo.memInfo();
            printGcStatistic(object, GcStatistics.Gc.MINOR);
            printGcStatistic(object, GcStatistics.Gc.MAJOR);
            prevGcStatistics = object;
        },1,1, TimeUnit.MINUTES);
    }

    private void printGcStatistic(GcStatistics object, GcStatistics.Gc type) {
        GcStatistics.GcStatistic gcStatistic = object.getGcStatistic(type);
        GcStatistics.GcStatistic prevGcStat = prevGcStatistics.getGcStatistic(type);
        log.info(type.name() + " count per minute "+(gcStatistic.getFullCounts()- prevGcStat.getFullCounts()));
        log.info(type.name() + " duration per minute ms "+ (   gcStatistic.getFullDuration() - prevGcStat.getFullDuration()  ));
        log.info(type.name() + " duration percent per minute "
                +percentagePerMinute(gcStatistic.getFullDuration() - prevGcStat.getFullDuration()));
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
