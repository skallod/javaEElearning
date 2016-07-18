//package ru.galuzin.lock_test;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import com.gridnine.bof.common.Environment;
//
//class ConnectionHelper {
//    static interface StatementExecutor<T> {
//        T execute(PreparedStatement st) throws Exception;
//    }
//
//    private Connection cnn;
//
//    ConnectionHelper(final ConnectionInfo cnnInfo) throws Exception {
//        cnn =
//                Environment.getPublished(DbRegistry.class)
//                        .createConnection(cnnInfo);
//        cnn.setAutoCommit(false);
//    }
//
//    void commitAndClose() throws SQLException {
//        if ((cnn == null) || cnn.isClosed()) {
//            return;
//        }
//        cnn.commit();
//        cnn.close();
//        cnn = null;
//    }
//
//    void rollbackAndClose() throws SQLException {
//        if ((cnn == null) || cnn.isClosed()) {
//            return;
//        }
//        cnn.rollback();
//        cnn.close();
//        cnn = null;
//    }
//
//    void execute(final String... statements) throws SQLException {
//        Statement st = cnn.createStatement();
//        try {
//            for (String statement : statements) {
//                st.executeUpdate(statement);
//            }
//        } finally {
//            st.close();
//        }
//    }
//
//    <T> T prepareAndExecute(final String statement,
//                            final StatementExecutor<T> executor) throws Exception {
//        PreparedStatement st = cnn.prepareStatement(statement);
//        try {
//            return executor.execute(st);
//        } finally {
//            st.close();
//        }
//    }
//}