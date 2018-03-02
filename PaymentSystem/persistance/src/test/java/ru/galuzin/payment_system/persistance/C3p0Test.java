package ru.galuzin.payment_system.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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


            while (true) {
                try {
                    Connection conn = c3p0pool.getConnection();
                    try {
                        Statement primarySt = conn.createStatement();
                        ResultSet primaryRes = primarySt.executeQuery("SELECT * from entitydata limit 1;");//"SELECT pg_current_xlog_location();");
                        primaryRes.next();
                        String[] primaryData = primaryRes.getString(1).split("/");
                        System.out.println("prim data " + Arrays.asList(primaryData));
                        primaryRes.close();
                        primarySt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("tt ", e);
                    e.printStackTrace();
                }
            }

        });
        t.setDaemon(true);
        t.start();
        while (true) {
            Thread.sleep(1000);
        }
    }

}
