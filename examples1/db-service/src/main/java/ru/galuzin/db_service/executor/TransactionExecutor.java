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
