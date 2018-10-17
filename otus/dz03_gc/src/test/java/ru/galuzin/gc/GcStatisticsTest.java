package ru.galuzin.gc;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Future;


public class GcStatisticsTest {
    @Test
    public void test1() throws Exception{
        System.out.println("hello !");
        GcStatistics statistics = new GcStatistics();
        GcStatisticsUpdateService updateService = new GcStatisticsUpdateService(statistics);
        Assert.assertEquals(0,statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts());
        Future<?> update = updateService.update(1, 1, GcStatistics.Gc.MINOR);
        update.get();
        Assert.assertEquals(1,statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts());
        GcStatistics cloned = statistics.cloneInstance();
        GcStatisticsUpdateService updateServiceCloned = new GcStatisticsUpdateService(cloned);
        Future<?> updateCloned = updateServiceCloned.update(2, 2, GcStatistics.Gc.MINOR);
        updateCloned.get();
        Assert.assertEquals(1,statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts());
        Assert.assertEquals(2,cloned.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts());
    }
}
