package ru.galuzin.completable_future;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        CompletableFuture<Integer>.completedFuture(10);
    }
}
