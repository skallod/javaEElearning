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
        try(PreparedStatement stmt = transactionContext.getConnection().prepareStatement(update)) {
            prepare.accept(stmt);
            int i = stmt.executeUpdate();
            return i;
        }
    }

    public <T> List<T> execQuery(String update, ExecuteParams prepare
            , TResultHandler<T> resultHandler, TransactionContext transactionContext) throws SQLException {
        try(PreparedStatement stmt = transactionContext.getConnection().prepareStatement(update)){
            prepare.accept(stmt);
            ArrayList<T> result = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T el = resultHandler.handle(rs);
                    result.add(el);
                }
            }
            return result;
        }
    }

}
