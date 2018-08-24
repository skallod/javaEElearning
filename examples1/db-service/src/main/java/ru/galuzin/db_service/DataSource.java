package ru.galuzin.db_service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {

    Connection getConnection() throws SQLException;

    void shutdown();
}
