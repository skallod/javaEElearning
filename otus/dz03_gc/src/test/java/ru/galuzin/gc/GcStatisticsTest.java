package ru.galuzin.gc;

import org.junit.Assert;
import org.junit.Test;


public class GcStatisticsTest {
    @Test
    public void test1(){
        System.out.println("hello !");
        GcStatistics statistics = new GcStatistics();
        Assert.assertEquals(statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts(),0);
        statistics.update(1,1,GcStatistics.Gc.MINOR);
        Assert.assertEquals(statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts(),1);
        GcStatistics cloned = statistics.cloneInstance();
        cloned.update(2,2,GcStatistics.Gc.MINOR);
        Assert.assertEquals(statistics.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts(),1);
        Assert.assertEquals(cloned.getGcStatistic(GcStatistics.Gc.MINOR).getFullCounts(),2);
    }
}
