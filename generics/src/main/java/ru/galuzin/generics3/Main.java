package ru.galuzin.generics3;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionExecutor txExec = new TransactionExecutor();
        PreparedExecutor preparedExecutor = new PreparedExecutor();
        List<String> res = txExec.execute((txContext)->{
            return preparedExecutor.execute(
                    (input)->{
                        return input+" handled!";
                    },
                    txContext
            );
        });
        System.out.println("res = " + res);
    }
}
