package ru.galuzin.db_service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DataSource {
    private static final Logger log = LoggerFactory.getLogger(DataSource.class);

    private static final DataSource instance = new DataSource();

    private final HikariConfig config;
    private final HikariDataSource ds;

    static DataSource getInstance(){
        return instance;
    }

    private DataSource() {
        Properties props = new Properties();
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            props.load(input);
            config = new HikariConfig(props);
            log.info("hikari props "+ props);
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