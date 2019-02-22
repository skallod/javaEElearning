package ru.galuzin.db_service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.galuzin.db_service.connection.DataSource;

public class DataSourceTest implements DataSource {

    private final HikariConfig config;
    private final HikariDataSource ds;

    public DataSourceTest(){
        config = new HikariConfig();
//        config.setJdbcUrl( "jdbc:h2:./data/testdb" );
        config.setJdbcUrl( "jdbc:h2:mem:test");//MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db.sql'" );
        config.setUsername( "sa" );
        config.setPassword( "" );
        config.setAutoCommit( false );
        config.setMaximumPoolSize( 10 );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        //config.addDataSourceProperty( "prepStmtCacheSize" , "100" );
        //config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "100" );
        /*prepStmtCacheSize-This sets the number of prepared statements that the MySQL driver will cache per connection. The default is a conservative 25. We recommend setting this to between 250-500.
        prepStmtCacheSqlLimit-This is the maximum length of a prepared SQL statement that the driver will cache. The MySQL default is 256. In our experience, especially with ORM frameworks like Hibernate, this default is well below the threshold of generated statement lengths. Our recommended setting is 2048.*/
        ds = new HikariDataSource( config );
        System.out.println("config = " + config);
        initdb();
    }

    private void initdb() {
        try {
            URL resource = getClass().getResource("/db.sql");
            byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
            String str = new String(bytes, StandardCharsets.UTF_8);
            try(Connection con = ds.getConnection()) {
                try (PreparedStatement stmt = con.prepareStatement(str)){
                    int i = stmt.executeUpdate();
                    System.out.println("success init db");
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
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
