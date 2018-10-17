package ru.galuzin.gc;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@AllArgsConstructor
public class GcStatisticsUpdateService implements AutoCloseable{
    private final ExecutorService executorMinor = Executors.newSingleThreadExecutor();
    private final ExecutorService executorMajor = Executors.newSingleThreadExecutor();
    private final GcStatistics gcStatistics;
    Future<?> update(long duration, long counts, @NonNull GcStatistics.Gc type){
        switch (type){
            case MINOR:
                return executorMinor.submit(()->{
                    gcStatistics.update(duration,counts,type);
                });
            case MAJOR:
                return executorMajor.submit(()->{
                    gcStatistics.update(duration,counts,type);
                });
            default:
                throw new IllegalStateException("update service unknown case");
        }
    }

    @Override
    public void close() throws Exception {
        executorMinor.shutdown();
        executorMajor.shutdown();
    }
}
