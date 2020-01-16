package ru.galuzin.thread.network;

import ru.galuzin.thread.network.httpclient.HttpClientAdapter;
import ru.galuzin.thread.network.httpclient.HttpResponse;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NetWork {

    public static void main(String[] args) throws Exception {
        System.out.println("test");
        HttpClientAdapter client = new HttpClientAdapter();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
            HttpResponse response = null;
            try {
                response = client.makeGetAndSend("http://host.docker.internal:58088/api/logout");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("response.getCode() = " + response.getCode());
        },1, 1, TimeUnit.SECONDS);

    }
}
