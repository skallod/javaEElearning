package ru.galuzin.db_service.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbUtils {
    static void close(Connection connection)throws SQLException{
        if(connection!=null){
            connection.close();
        }
    }
    static void rollback(Connection connection)throws SQLException{
        if(connection!=null){
            connection.rollback();
        }
    }
    static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
}
