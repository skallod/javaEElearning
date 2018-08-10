package ru.galuzin.db_service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class PreparedExecutor {

//    private final Connection connection;

//    public PreparedExecutor(Connection connection) {
//        this.connection = connection;
//    }

    static int execUpdate(String update, ExecuteParams prepare) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(update)) {
            prepare.accept(stmt);
            return stmt.executeUpdate();
        }
    }

    static  <T> List<T> execQuery(String update, ExecuteParams prepare, TResultHandler<T> resultHandler) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(update);
            ) {
            prepare.accept(stmt);
            ResultSet rs = stmt.executeQuery();
            ArrayList<T> result = new ArrayList<>();
            while(rs.next()){
                T el = resultHandler.handle(rs);
                result.add(el);
            }
            rs.close();
            return result;
        }
    }

    static Connection getConnection() throws SQLException {
        return DataSource.getInstance().getConnection();
    }
}
