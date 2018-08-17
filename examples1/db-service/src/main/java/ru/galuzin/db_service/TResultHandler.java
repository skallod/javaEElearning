package ru.galuzin.db_service;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
interface TResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
