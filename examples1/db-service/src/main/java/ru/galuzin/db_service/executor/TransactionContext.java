package ru.galuzin.db_service.executor;

import java.sql.Connection;

public class TransactionContext {

    private Connection connection;

    TransactionContext() {
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection() {
        return connection;
    }
}
