package ru.galuzin.db_service.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreparedExecutor{

    private static final Logger log = LoggerFactory.getLogger(PreparedExecutor.class);

    public PreparedExecutor() {
    }

    public int execUpdate(@NonNull String update, ExecuteParams prepare, TransactionContext transactionContext) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = transactionContext.getConnection().prepareStatement(update);
            prepare.accept(stmt);
            int i = stmt.executeUpdate();
            return i;
        } finally {
            DbUtils.close(stmt);
        }
    }

    public <T> List<T> execQuery(String update, ExecuteParams prepare
            , TResultHandler<T> resultHandler, TransactionContext transactionContext) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = transactionContext.getConnection().prepareStatement(update);
            prepare.accept(stmt);
            rs = stmt.executeQuery();
            ArrayList<T> result = new ArrayList<>();
            while (rs.next()) {
                T el = resultHandler.handle(rs);
                result.add(el);
            }
            return result;
        }finally {
            DbUtils.close(rs);
            DbUtils.close(stmt);
        }
    }

}
