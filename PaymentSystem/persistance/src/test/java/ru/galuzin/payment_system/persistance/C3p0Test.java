package ru.galuzin.payment_system.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;
import ru.galuzin.hb_pool_impl.C3p0pool;

public class C3p0Test {
    private static final Logger log = Logger.getLogger(C3p0Test.class);
    private C3p0pool c3p0pool = new C3p0pool();

    @Test
    public void testGetAllEntities() throws Exception {
        log.info("test start");
        Thread t = new Thread(() -> {
            //while (true) {
                try {
                    Connection conn = null;
                    Connection conn2 = null;
                    Statement primarySt = null;
                    ArrayList<Exception> exceptions = new ArrayList<>();
                    try {
                        conn = c3p0pool.getConnection();
                        //conn2 = c3p0pool.getConnection();
                        primarySt = conn.createStatement();
                        ResultSet primaryRes = primarySt.executeQuery("SELECT * from entitydata limit 1;");//"SELECT pg_current_xlog_location();");
                        primaryRes.next();
                        String[] primaryData = primaryRes.getString(1).split("/");
                        System.out.println("prim data " + Arrays.asList(primaryData));
                        //primaryRes.close();
                        primarySt.close();
                        //throw new SQLException("test2");
                    } catch (Exception e) {
                        //throw new RuntimeException(e);
                        //exceptions.add(e);
                        //e.printStackTrace();*/
                    } finally {
                        try {
                            if (conn != null && !conn.isClosed()) {
                                conn.close();
                                throw new SQLException("test");
                            }
                        }catch (SQLException e){
                            exceptions.add(e);
                        }
                        try {
                            System.gc();
                            Thread.sleep(1000);
                            if (conn2 != null && !conn2.isClosed()) {
                                conn2.close();
                            }
                        }catch (SQLException e){
                            exceptions.add(e);
                        }
                        if(!exceptions.isEmpty()){
                            throw exceptions.get(0);
                        }
                    }
                    //Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("tt ", e);
                    e.printStackTrace();
                }
            //}

        });
        t.setDaemon(true);
        t.start();
        while (true) {
            Thread.sleep(10000);
        }
    }

    @Test
    public void testGetAllEntities2() throws Exception {
        log.info("test start");
        Thread t = new Thread(() -> {
            try {
                ArrayList<Exception> exceptions = new ArrayList<>();
                try (MyConnection conn = new MyConnection(1);
                    MyConnection conn2 = new MyConnection(2);){
                    conn.test();
                }
            } catch (Exception e) {
                log.error("tt ", e);
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }

    class MyConnection implements AutoCloseable{
        int id;
        MyConnection(int id){
            this.id = id;
        }
        @Override
        public void close() throws Exception {
            System.out.println("CLOSE CONNECTION "+this.id);
            //throw new SQLException("test");
        }

        void test() throws Exception{
            //throw new Exception("test2");
        }
    }

}
