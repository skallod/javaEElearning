package ru.galuzin.hb_pool_impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0pool {

    ComboPooledDataSource ds;

    public C3p0pool() {
        try {
//        Properties p = new Properties(System.getProperties());
            System.getProperties().setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.log4j.Log4jMLog");
//        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // Off or any other level
//        System.setProperties(p);
            ds = new ComboPooledDataSource();
            ds.setDriverClass("org.postgresql.Driver");
            ds.setJdbcUrl("jdbc:postgresql://localhost:55433/bof?loginTimeout=10");
            ds.setUser("postgres");
            ds.setPassword("12345678");
            ds.setMaxIdleTime(1800);
            ds.setMinPoolSize(1);
            ds.setMaxPoolSize(1);
            ds.setAcquireIncrement(5);
            ds.setAcquireRetryAttempts(5);
            //ds.getProperties().setProperty("unreturnedConnectionTimeout","10");
            //ds.getProperties().setProperty("debugUnreturnedConnectionStackTraces","true");
            ds.setUnreturnedConnectionTimeout(10);
            //ds.setDebugUnreturnedConnectionStackTraces(true);
            //ds.setTestConnectionOnCheckin(true);
            //Properties properties = new Properties();
            //ds.getProperties().put("com.mchange.v2.log.MLog","log4j");
            System.out.println("ds = " + ds);
            //ds.setProperties(properties);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        }catch (Exception e){
            System.out.println("get connection from pool exception "+ e.getMessage());
            if(connection!=null && !connection.isClosed()){
                System.out.println("closed");
                connection.close();
            }
            throw e;
        }
        return ds.getConnection();
    }



    public void close() {
        ds.close();
    }
}
