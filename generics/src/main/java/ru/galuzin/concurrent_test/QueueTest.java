package ru.galuzin.concurrent_test;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by galuzin on 25.05.2017.
 */
public class QueueTest {

    BlockingQueue<TaskParameters> queue = new LinkedBlockingQueue<TaskParameters>(3);
    final Thread processingThread;

    QueueTest() {
        processingThread = new Thread(() ->
        {
            TaskParameters tp = null;
            boolean interrupted = false;
            try {
                do {
                    try {
                        tp = queue.take();
                    } catch (InterruptedException e) {
                        interrupted = true;
                        e.printStackTrace();
                    }
                    processing(tp);
                    tp = null;
                } while (!Thread.currentThread().isInterrupted());
            } finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        //processingThread.setDaemon(true);
        processingThread.setName("queue processing");
        processingThread.start();
    }

    private void receive(TaskParameters taskParameters) {
        try {
            queue.put(taskParameters);
            System.out.println("added to queue " + taskParameters.count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processing(TaskParameters tp) {
        System.out.println("processing " + tp.count);
        long nanoTime = System.nanoTime();
        tp.setStartedDate(new Date());
        ThreadInterrupt.longOper();
        System.out.println("processingTime = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime));
        System.out.println("tp = " + tp);
    }

    void stop() {
        processingThread.interrupt();
    }

    public static void main(String[] args) {
        QueueTest queueTest = new QueueTest();
        for (int i = 0; i < 10; i++) {
            queueTest.receive(new TaskParameters("name", i, new Date()));
            System.out.println("received i = " + i);
        }
    }
}

class TaskParameters {
    String name;
    int count;
    Date addedDate;
    Date startedDate;

    TaskParameters(String name, int count, Date date) {
        this.name = name;
        this.count = count;
        this.addedDate = date;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    @Override
    public String toString() {
        return "TaskParameters{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", addedDate=" + addedDate +
                ", startedDate=" + startedDate +
                '}';
    }
}
