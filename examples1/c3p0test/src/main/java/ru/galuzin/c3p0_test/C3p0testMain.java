package ru.galuzin.c3p0_test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.*;

public class C3p0testMain {

    private static final Logger log = LoggerFactory.getLogger(C3p0testMain.class);

    private static final String PROC_CALL = "BEGIN\n"+
            "test1;\n"+
            "END;\n";

    public static void main(String[] args) throws PropertyVetoException, InterruptedException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
        dataSource.setUser("DEV_USER");
        dataSource.setPassword("4321");
        dataSource.setMinPoolSize(1);
        dataSource.setMaxPoolSize(1);
//        dataSource.setProperties();
        dataSource.setMaxIdleTime(10);//secs
        dataSource.setMaxIdleTimeExcessConnections(10);
        dataSource.setInitialPoolSize(1);
        dataSource.setIdleConnectionTestPeriod(3);
        dataSource.setTestConnectionOnCheckin(false);
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setPreferredTestQuery("select * from person");
        dataSource.setDescription("get-persons");
        dataSource.setCheckoutTimeout(70_000);
        dataSource.setUnreturnedConnectionTimeout(30);
        dataSource.setDebugUnreturnedConnectionStackTraces(true);
        dataSource.setAutoCommitOnClose(false);
//        dataSource.setConnectionPoolDataSource();
//        dataSource.setConnectionCustomizerClassName();
        final Properties prop = new Properties();
        //prop.setProperty("oracle.net.CONNECT_TIMEOUT", "30000");
        //prop.setProperty("oracle.jdbc.ReadTimeout", "30000");
        prop.setProperty("user", "DEV_USER");
        prop.setProperty("password", "4321");
        dataSource.setProperties(prop);
        sleep(1_000);
        final CyclicBarrier b = new CyclicBarrier(1);
        final ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(()->executeQuery(dataSource,b));
        Thread.sleep(1_000);
//        pool.submit(()->executeQuery2(dataSource));
//        pool.submit(()->executeQuery(dataSource,b));
//        pool.submit(()->executeQuery(dataSource,b));
//        pool.submit(()->executeQuery(dataSource,b));
        //pool.shutdown();
        //Thread.sleep(120_000);
    }

    private static void executeQuery2(ComboPooledDataSource dataSource){
        log.info("query 2 start ");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from data_type");
            ResultSet rs = ps.executeQuery();
                ){
                log.info("query2 connection "+connection.hashCode() + " ; "+connection.getClass());
                while( rs.next() ){
                    log.info("query 2 result n "+rs.getString("NAME"));
                }
        } catch (SQLException e) {
            log.error("query 2 error" ,e );
        }
    }

    private static void executeQuery(ComboPooledDataSource dataSource, CyclicBarrier b) {
        final Random random = new Random();
//        while (true){
            try(Connection connection = dataSource.getConnection();
//                final CallableStatement call = connection.prepareCall(PROC_CALL);
//                final CallableStatement call = connection.prepareCall("call TEST1()");
                final CallableStatement call = connection.prepareCall("call dbms_lock.sleep(60)");
//                PreparedStatement statement = connection.prepareStatement(
//                        "execute dbms_lock.sleep(60);");
//                final PreparedStatement ps = connection.prepareStatement("select * from data_type");
//                final PreparedStatement ps = connection
//                        .prepareStatement("insert into jes values(?,?)");
//                ResultSet rs = ps.executeQuery();
            ){
                log.info("gal execute con =" +connection.hashCode());
                //ps.setInt(1,9);
                //ps.setInt(2,calculateHash(Integer.MIN_VALUE));
                //ps.execute();
                //call.registerOutParameter(1, Types.VARCHAR);
                call.execute();
//                while( rs.next() ){
//                    log.info("result n "+rs.getString("NAME"));
//                }
//                random.nextInt(20);
                log.info("gal execute finish ");
                //sleep(10_000);
            } catch (Exception e) {
                log.error("",e);
                log.info("gal execute finish with error");
            }
            sleep(30_000);
            log.info("gal sleep finish");
//            try {
//                b.await();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        log.info("gal after barrier");
//        }
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            log.error("",e);
        }
    }

}
//    CREATE TABLE DATA_TYPE(
//        ID NUMBER NOT NULL,
//        NAME VARCHAR2(32) NOT NULL,
//    MIME_TYPE VARCHAR2(32) NOT NULL,
//    PRIMARY KEY(ID)
//);


