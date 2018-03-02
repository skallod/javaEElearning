//package ru.galuzin.hb_pool_impl;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//
//public class DbRegistry {
//    private final Map<String, DataSource> dataSources =
//            new HashMap<String, DataSource>();
//
//
//    public void regConn(ConnectionInfo ci)throws Exception{
//        synchronized (dataSources) {
//            dataSources.put("prime", createDataSource(ci));
//        }
//    }
//
//    private DataSource createDataSource(final ConnectionInfo cnnInfo)
//            throws Exception {
//        ComboPooledDataSource result = new ComboPooledDataSource();
//        result.setDriverClass("org.postgresql.Driver");//DriverName
//        //loads the jdbc driver
//        result.setJdbcUrl(cnnInfo.getUrl());
//        result.setUser(cnnInfo.getUser());
//        result.setPassword(cnnInfo.getPassword());
//        // the settings below are optional -- c3p0 can work with defaults
//        result.setMinPoolSize(1);
//        result.setAcquireIncrement(5);
//        result.setMaxPoolSize(cnnInfo.getPoolSize());
//        result.setMaxIdleTime(1800);
//        //result.setMaxStatements(50);
//        // other connection properties
//        result.getProperties().putAll(cnnInfo.getParameters());
//        return result;
//    }
//
//
//    public ConnectionInfo getConnectionInfo(){
//        return null;
//    }
//
//
//    public Connection getConnection() throws Exception{
//        DataSource ds;
//        synchronized (dataSources) {
//            ds = dataSources.get("prime");
//        }
//        return ds.getConnection();
//    }
//}
