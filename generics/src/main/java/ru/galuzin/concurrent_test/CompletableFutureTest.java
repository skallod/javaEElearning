package ru.galuzin.concurrent_test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) {
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(120_000);
                return "good";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });

        final String result = future.join();
        System.out.println("result = " + result);

    }
}
