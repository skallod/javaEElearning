package ru.galuzin.db_service.executor;

import ru.galuzin.db_service.connection.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionExecutor {

    private final DataSource dataSource;

    public TransactionExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> exec(ExecuteSqlWithResult<T> query) throws SQLException {
        TransactionContext txContext = new TransactionContext();
        try(Connection conn = getConnection()) {
            txContext.setConnection(conn);
            try{
                List<T> resultList = query.exec(txContext);
                txContext.getConnection().commit();
                return resultList;
            }catch (SQLException e){
                txContext.getConnection().rollback();
                throw e;
            }
        }
    }

    public void exec(ExecuteSQL query) throws SQLException {
        TransactionContext txContext = new TransactionContext();
        try(Connection conn = getConnection()) {
            txContext.setConnection(conn);
            try{
                query.exec(txContext);
                txContext.getConnection().commit();
            }catch (SQLException e){
                txContext.getConnection().rollback();
                throw e;
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
