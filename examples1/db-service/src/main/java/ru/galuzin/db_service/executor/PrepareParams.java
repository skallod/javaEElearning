package ru.galuzin.db_service.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PrepareParams {
    void accept(PreparedStatement statement) throws SQLException;
}
