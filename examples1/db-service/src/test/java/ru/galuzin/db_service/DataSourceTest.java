package ru.galuzin.db_service;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceTest implements DataSource {

    private final HikariConfig config;
    private final HikariDataSource ds;

    public DataSourceTest(){
        config = new HikariConfig();
        config.setJdbcUrl( "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db.sql'" );
//        config.setJdbcUrl( "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db.sql'" );
        config.setUsername( "sa" );
        config.setPassword( "" );
        config.setAutoCommit( false );
        config.setMaximumPoolSize( 1 );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void shutdown() {
        ds.close();
    }
}
