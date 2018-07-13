package ru.galuzin.gc;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "gc-notification-details")
public class GcNotification {
    public static void installGCMonitoring(SharedObject sharedObject){
        //get all the GarbageCollectorMXBeans - there's one for each heap generation
        //so probably two - the old generation and young generation
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        //Install a notifcation handler for each bean
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            log.info(gcbean.toString());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            //use an anonymously generated listener for this example
            // - proper code should really use a named class
            NotificationListener listener = new NotificationListener() {
                //keep a count of the total time spent in GCs
                long totalGcDuration = 0;

                //implement the notifier callback handler
                @Override
                public void handleNotification(Notification notification, Object handback) {
                    //we only handle GARBAGE_COLLECTION_NOTIFICATION notifications here
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                        //get the information associated with this notification
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        //get all the info and pretty print it
                        long duration = info.getGcInfo().getDuration();
                        String gctype = info.getGcAction();
                        if ("end of minor GC".equals(gctype)) {
                            gctype = "Young Gen GC";
                            sharedObject.updateMinor(duration, info.getGcInfo().getId());
                        } else if ("end of major GC".equals(gctype)) {
                            gctype = "Old Gen GC";
                            sharedObject.updateMajor(duration, info.getGcInfo().getId());
                        }
                        log.info("");
                        log.info(gctype + ": - " + info.getGcInfo().getId()+ " " + info.getGcName() + " (from " + info.getGcCause()+") "+duration
                                + " milliseconds; start-end times " + sdf.format(info.getGcInfo().getStartTime())+ " -> " + sdf.format(info.getGcInfo().getEndTime()));
                        
                        //Get the information about each memory space, and pretty print it
                        Map<String, MemoryUsage> membefore = info.getGcInfo().getMemoryUsageBeforeGc();
                        Map<String, MemoryUsage> mem = info.getGcInfo().getMemoryUsageAfterGc();
                        for (Map.Entry<String, MemoryUsage> entry : mem.entrySet()) {
                            String name = entry.getKey();
                            MemoryUsage memdetail = entry.getValue();
                            long memInit = memdetail.getInit();
                            long memCommitted = memdetail.getCommitted();
                            long memMax = memdetail.getMax();
                            long memUsed = memdetail.getUsed();
                            MemoryUsage before = membefore.get(name);
                            //long beforepercent = ((before.getUsed() * 1000L) / before.getCommitted()==0?1:before.getCommitted());
                            //long percent = ((memUsed*1000L)/before.getCommitted()==0?1:before.getCommitted()); //>100% when it gets expanded
                            //log.info(name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used: "+(beforepercent/10)+"."+(beforepercent%10)+"%->"+(percent/10)+"."+(percent%10)+"%("+((memUsed/1048576)+1)+"MB) / ");
                            log.info(name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used before (Kb): "+before.getUsed()/1024+" ->after used "+memUsed/1024);
                        }
                        log.info("");
//                        totalGcDuration += info.getGcInfo().getDuration();
//                        long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();
//                        log.info("GC cumulated overhead "+(percent/10)+"."+(percent%10)+"%");
                    }
                }
            };

            //Add the listener
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
