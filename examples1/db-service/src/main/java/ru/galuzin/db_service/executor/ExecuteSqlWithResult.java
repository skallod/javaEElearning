package ru.galuzin.db_service.executor;

import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ExecuteSqlWithResult <T>{
    List<T> exec(TransactionContext transactionContext) throws SQLException;
}
