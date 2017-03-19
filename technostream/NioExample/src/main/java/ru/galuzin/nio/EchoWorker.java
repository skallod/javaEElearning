package ru.galuzin.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
class EchoWorker implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(EchoWorker.class);
    private final List<ServerDataEvent> queue = new LinkedList<>();

    void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized (queue) {
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    public void run() {
        ServerDataEvent dataEvent;
        while (true) {
            String recievedData = null;
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                recievedData = new String(queue.get(0).data);
                LOG.info("Recieved data = " + recievedData);
                dataEvent = queue.remove(0);
            }
            String result = operation(recievedData);
            dataEvent.data = result.getBytes();
            dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }

    private String operation(String receivedData){
        long time  = System.nanoTime();
        double result= Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random()*Math.PI)))),2d)))),2d)),2d));
        for(int i=0; i<1_00_000;i++) {
            result += Math.log(Math.pow(Math.log(Math.pow(Math.log(Math.log(Math.log(Math.pow(Math.log(Math.log(Math.sqrt(Math.log(Math.random() * Math.PI)))), 2d)))), 2d)), 2d));
        }
        time = System.nanoTime()-time;
        String resultStr = receivedData + " handled " + time + " nanos";
        LOG.info(resultStr);
        return resultStr;
    }
}
