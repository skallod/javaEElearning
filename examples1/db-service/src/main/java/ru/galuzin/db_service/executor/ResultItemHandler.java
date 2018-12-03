package ru.galuzin.db_service.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultItemHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
