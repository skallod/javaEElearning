package ru.galuzin.db_service.executor;

import ru.galuzin.db_service.connection.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

    private final DataSource dataSource;

    public TransactionExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void exec(ExecuteSQL query) throws SQLException {
        TransactionContext tContext = new TransactionContext();
        try{
            tContext.setConnection(getConnection());
            query.exec(tContext);
            tContext.getConnection().commit();
        }catch (SQLException e){
            DbUtils.rollback(tContext.getConnection());
            throw e;
        }finally {
            DbUtils.close(tContext.getConnection());
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
