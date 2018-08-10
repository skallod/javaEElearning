package ru.galuzin.db_service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecuteParams {
    void accept(PreparedStatement statement) throws SQLException;
}
