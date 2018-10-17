package ru.galuzin.gc;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
@AllArgsConstructor
public class GcStatistics {
    private volatile GcStatistic minor = new GcStatistic(0L,0L);
    private volatile GcStatistic major = new GcStatistic(0L,0L);

    GcStatistics cloneInstance(){
        return new GcStatistics(this.minor, this.major);
    }

    /**
     * Invoke only through GcStaticsUpdateService
     */
    void update(long duration, long counts, @NonNull Gc type){
        switch (type){
            case MINOR:
                minor = new GcStatistic(minor.getFullDuration()+duration,counts);
                break;
            case MAJOR:
                major = new GcStatistic(major.getFullDuration()+duration,counts);
                break;
        }
    }

    GcStatistic getGcStatistic(@NonNull Gc type){
        switch (type){
            case MINOR:
                return minor;
            case MAJOR:
                return major;
        }
        return null;
    }

    @Getter
    @Value
    class GcStatistic{
        private final long fullDuration;
        private final long fullCounts;
    }

    enum Gc{MINOR,MAJOR}
}