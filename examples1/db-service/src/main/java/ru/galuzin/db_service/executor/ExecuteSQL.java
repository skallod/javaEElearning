package ru.galuzin.db_service.executor;

import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ExecuteSQL {
    void exec(TransactionContext transactionContext) throws SQLException;
}