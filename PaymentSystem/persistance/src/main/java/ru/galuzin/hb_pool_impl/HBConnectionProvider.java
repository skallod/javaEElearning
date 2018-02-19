package ru.galuzin.hb_pool_impl;

/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof
 *
 * $Id$
 *****************************************************************/

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

/**
 * Connection provider that using DbRegistry to provide connections.
 */
public class HBConnectionProvider implements ConnectionProvider {
    static final Logger log = Logger.getLogger("c3test");

    static final String CONNECTION_UID = "bof.connectionUid";

    //private DbRegistry dbRegistry = new DbRegistry();

    C3p0pool c3p0pool = new C3p0pool();

    @Override
    public void configure(final Properties props) throws HibernateException {
    }


    @Override
    public Connection getConnection() throws SQLException {
        log.info("get c3 conn");
        try {
            Connection result = c3p0pool.getConnection();
            return result;
        } catch (Exception e) {
            //log.error("failed creating connection", e);
            e.printStackTrace();
            throw new SQLException("failed creating connection", e);
        }
    }

    @Override
    public void closeConnection(final Connection conn) throws SQLException {
        log.info("close c3 conn");
        if (conn.isClosed()) {
            return;
        }
        conn.close();
    }

    @Override
    public void close() throws HibernateException {
        System.out.println("close");
        //log.info("closed for " + cnnInfo.getUid());
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
}

