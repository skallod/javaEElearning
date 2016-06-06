// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Default implementation of DB handler.
 * @version $Id: DefaultDbHandler.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
public class DefaultDbHandler implements DbHandler {
    /**
     * This implementation returns <code>null</code> as I don't know how to list
     * all DB objects using standard SQL query.
     * @see org.jpf.demo.toolbox.dbbrowser.DbHandler#getAllDbObjects(
     *      java.sql.Connection)
     */
    public Collection<DbObject> getAllDbObjects(Connection cnn)
            throws SQLException {
        throw new SQLException("not implemented");
    }

    /**
     * @see org.jpf.demo.toolbox.dbbrowser.DbHandler#executeQuery(
     *      java.sql.Connection, org.jpf.demo.toolbox.dbbrowser.DbObject, int)
     */
    public ResultSet executeQuery(final Connection cnn, final DbObject dbObj,
            final int maxResult) throws SQLException {
        switch (dbObj.getType()) {
            case DbObject.TYPE_TABLE:
            case DbObject.TYPE_VIEW: {
                return executeQuery(cnn, "SELECT "
                        + ((maxResult > 0) ? " TOP " + maxResult : "")
                        + " * FROM " + dbObj.getName(), maxResult);
            }
        }
        return cnn.prepareCall("EXEC " + dbObj.getName(),
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
                .executeQuery();
    }

    /**
     * @see org.jpf.demo.toolbox.dbbrowser.DbHandler#executeQuery(
     *      java.sql.Connection, java.lang.String, int)
     */
    public ResultSet executeQuery(final Connection cnn, final String queryStr,
            final int maxResult) throws SQLException {
        return cnn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY).executeQuery(queryStr);
    }
}
