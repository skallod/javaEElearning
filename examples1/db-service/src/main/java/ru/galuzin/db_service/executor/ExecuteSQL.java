package ru.galuzin.db_service.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExecuteSQL {
    void exec(TransactionContext transactionContext) throws SQLException;
}
