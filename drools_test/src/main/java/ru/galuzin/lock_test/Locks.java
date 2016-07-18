//package ru.galuzin.lock_test;
///*****************************************************************
// * Gridnine AB http://www.gridnine.com
// * Project: BOF Unifest
// *
// * $Id: Locks.java 10674 2012-04-02 11:46:29Z olshansky $
// *****************************************************************/
//        import java.sql.PreparedStatement;
//        import java.sql.ResultSet;
//        import java.util.Timer;
//        import java.util.TimerTask;
//        import java.util.concurrent.TimeoutException;
//
//        import org.apache.commons.logging.Log;
//        import org.apache.commons.logging.LogFactory;
//
//
//@SuppressWarnings("nls")
//public final class Locks {
//    private static final long TIMER_PERIOD = 1000 * 60;// one minute
//
//    public static void setup(final ConnectionInfo cnnInfo) throws Exception {
//        ConnectionHelper helper = new ConnectionHelper(cnnInfo);
//        try {
//            helper
//                    .execute(
//                            "CREATE TABLE LOCKDATA (TOKEN VARCHAR(255) NOT NULL PRIMARY KEY, EXPIRED BIGINT NOT NULL)",
//                            "CREATE INDEX LOCKDATA_EXPIRED ON LOCKDATA(EXPIRED)");
//            helper.commitAndClose();
//        } finally {
//            helper.rollbackAndClose();
//        }
//    }
//
//    final transient Log log = LogFactory.getLog(getClass());
//
//    private final ConnectionInfo cnnInfo;
//
//    private final Timer timer;
//
//    public Locks(final ConnectionInfo aCnnInfo) {
//        cnnInfo = aCnnInfo;
//        timer = new Timer("expired-locks-release", true);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                removeExpired();
//            }
//        }, TIMER_PERIOD, TIMER_PERIOD);
//    }
//
//    protected void removeExpired() {
//        try {
//            ConnectionHelper helper = new ConnectionHelper(cnnInfo);
//            try {
//                helper.prepareAndExecute(
//                        "DELETE FROM LOCKDATA WHERE EXPIRED<?",
//                        new ConnectionHelper.StatementExecutor<Void>() {
//                            @Override
//                            public Void execute(final PreparedStatement st)
//                                    throws Exception {
//                                st.setLong(1, System.currentTimeMillis());
//                                log.debug(String.format("deleted %s expired locks",
//                                        Integer.valueOf(st.executeUpdate())));
//                                return null;
//                            }
//                        });
//                helper.commitAndClose();
//            } finally {
//                helper.rollbackAndClose();
//            }
//        } catch (Exception e) {
//            log.error("failed deleting expired locks", e);
//        }
//    }
//
//    public void dispose() {
//        if (timer != null) {
//            timer.cancel();
//        }
//    }
//
//    public void acquire(final String token, final long waitTimeoutMillis,
//                        final long timeToHoldMillis) throws Exception {
//        long timing = System.currentTimeMillis();
//        while (!tryToAcquire(token, timeToHoldMillis)) {
//            if ((System.currentTimeMillis() - timing) >= waitTimeoutMillis) {
//                throw new TimeoutException();
//            }
//            Thread.sleep(1000);
//        }
//    }
//
//    public void release(final String token) {
//        try {
//            ConnectionHelper helper = new ConnectionHelper(cnnInfo);
//            try {
//                helper.prepareAndExecute("DELETE FROM LOCKDATA WHERE TOKEN=?",
//                        new ConnectionHelper.StatementExecutor<Void>() {
//                            @Override
//                            public Void execute(final PreparedStatement st)
//                                    throws Exception {
//                                st.setString(1, token);
//                                st.executeUpdate();
//                                return null;
//                            }
//                        });
//                helper.commitAndClose();
//            } finally {
//                helper.rollbackAndClose();
//            }
//        } catch (Exception e) {
//            log.error("failed releasing lock " + token, e);
//        }
//    }
//
//    private boolean tryToAcquire(final String token, final long timeToHoldMillis) {
//        try {
//            ConnectionHelper helper = new ConnectionHelper(cnnInfo);
//            try {
//                Boolean proceed =
//                        helper.prepareAndExecute(
//                                "SELECT COUNT(1) FROM LOCKDATA WHERE TOKEN=?",
//                                new ConnectionHelper.StatementExecutor<Boolean>() {
//                                    @Override
//                                    public Boolean execute(final PreparedStatement st)
//                                            throws Exception {
//                                        st.setString(1, token);
//                                        ResultSet rs = st.executeQuery();
//                                        rs.next();
//                                        int count = rs.getInt(1);
//                                        rs.close();
//                                        return Boolean.valueOf(count == 0);
//                                    }
//                                });
//                if (!proceed.booleanValue()) {
//                    return false;
//                }
//                helper.prepareAndExecute(
//                        "INSERT INTO LOCKDATA (TOKEN, EXPIRED) VALUES(?, ?)",
//                        new ConnectionHelper.StatementExecutor<Void>() {
//                            @Override
//                            public Void execute(final PreparedStatement st)
//                                    throws Exception {
//                                st.setString(1, token);
//                                st.setLong(2, System.currentTimeMillis()
//                                        + timeToHoldMillis);
//                                st.executeUpdate();
//                                return null;
//                            }
//                        });
//                helper.commitAndClose();
//                return true;
//            } finally {
//                helper.rollbackAndClose();
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
//
