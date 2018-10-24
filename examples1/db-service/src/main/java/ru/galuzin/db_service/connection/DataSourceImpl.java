package ru.galuzin.db_service.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceImpl implements DataSource{
    private static final Logger log = LoggerFactory.getLogger(DataSourceImpl.class);

    private final HikariConfig config;

    private final HikariDataSource ds;

    public DataSourceImpl() {
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

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void shutdown(){
        ds.close();
    }
}