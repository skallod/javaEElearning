package ru.galuzin.db_service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

class DataSource {

    private static final DataSource instance = new DataSource();

    private final HikariConfig config;
    private final HikariDataSource ds;

////        config.setJdbcUrl( "jdbc:postgresql://localhost:5432/dbservice" );
////        config.setUsername( "postgres" );
////        config.setPassword( "12345678" );
////        config.addDataSourceProperty( "cachePrepStmts" , "true" );
////        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
////        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "1024" );

    static DataSource getInstance(){
        return instance;
    }

    private DataSource() {
        Properties props = new Properties();
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            props.load(input);
            config = new HikariConfig(props);
            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    void shutdown(){
        ds.close();
    }
}