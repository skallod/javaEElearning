package ru.galuzin.c3p0_test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C3p0testMain {

    private static final Logger log = LoggerFactory.getLogger(C3p0testMain.class);

    public static void main(String[] args) throws PropertyVetoException, InterruptedException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
        dataSource.setUser("DEV_USER");
        dataSource.setPassword("1234");
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxIdleTime(60);//secs
        final ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(()->executeQuery(dataSource));
//        pool.submit(()->executeQuery(dataSource));
//        pool.submit(()->executeQuery(dataSource));
//        pool.submit(()->executeQuery(dataSource));
//        pool.submit(()->executeQuery(dataSource));
        //pool.shutdown();
        Thread.sleep(120_000);
        pool.submit(()->executeQuery(dataSource));
    }

    private static void executeQuery(ComboPooledDataSource dataSource) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from person");
            ResultSet rs = statement.executeQuery();
        ){
            log.info("executed ");
            while( rs.next() ){
                log.info("result "+rs);
            }
            Thread.sleep(40_000);
            log.info("executed finish");
        } catch (Exception e) {
            log.error("",e);
        }
    }
}
