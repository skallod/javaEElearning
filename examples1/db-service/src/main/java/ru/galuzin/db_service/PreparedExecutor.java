package ru.galuzin.db_service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PreparedExecutor {

    private static final Logger log = LoggerFactory.getLogger(PreparedExecutor.class);

    static int execUpdate(String update, ExecuteParams prepare) throws SQLException {
        return execUpdate(update, prepare, null);
    }

    static int execUpdate(String update, ExecuteParams prepare, Connection masterConn) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            if (masterConn == null) {
                connection = getConnection();
            } else {
                connection = masterConn;
            }
            stmt = connection.prepareStatement(update);
            prepare.accept(stmt);
            int i = stmt.executeUpdate();
            if (masterConn == null) {
                connection.commit();
            }
            return i;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    log.error("stmt close ",e);
                }
            }
            if (masterConn == null && connection != null) {
                connection.close();
            }
        }
    }

    static <T> List<T> execQuery(String update, ExecuteParams prepare, TResultHandler<T> resultHandler) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(update);
        ) {
            prepare.accept(stmt);
            ResultSet rs = stmt.executeQuery();
            ArrayList<T> result = new ArrayList<>();
            while (rs.next()) {
                T el = resultHandler.handle(rs);
                result.add(el);
            }
            rs.close();
            connection.commit();
            return result;
        }
    }

    static Connection getConnection() throws SQLException {
        return DataSource.getInstance().getConnection();
    }
}
