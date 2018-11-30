package ru.galuzin.generics3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@FunctionalInterface
public interface ItemHandler<T> {
    T handle(String result) throws SQLException;
}
