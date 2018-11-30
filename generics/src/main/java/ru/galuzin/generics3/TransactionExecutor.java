package ru.galuzin.generics3;

import java.util.List;

public class TransactionExecutor {
    <T> List<T> execute(ExecuteSQL<T> executeSQL){
        TransactionContext txContext = new TransactionContext("mars meteo connection");
        List<T> exec = executeSQL.exec(txContext);
        return exec;
    }
}
