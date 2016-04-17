// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jpf.demo.toolbox.dbbrowser.DbObject;
import org.jpf.demo.toolbox.dbbrowser.DefaultDbHandler;

/**
 *
 * @version $Id: MysqlDbHandler.java,v 1.1 2007/03/04 13:01:04 ddimon Exp $
 */
public final class MysqlDbHandler extends DefaultDbHandler {
    /**
     * @throws SQLException
     * @see org.jpf.demo.toolbox.dbbrowser.DefaultDbHandler#getAllDbObjects(
     *      java.sql.Connection)
     */
    @Override
    public Collection<DbObject> getAllDbObjects(final Connection cnn)
            throws SQLException {
        ResultSet rs = cnn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY).executeQuery("SHOW TABLES");
        try {
            List<DbObject> result = new LinkedList<DbObject>();
            while (rs.next()) {
                result.add(new DbObject(rs.getString(1), DbObject.TYPE_TABLE));
            }
            return result;
        } finally {
            rs.close();
        }
    }
    
    /**
     * @see org.jpf.demo.toolbox.dbbrowser.DefaultDbHandler#executeQuery(
     *      java.sql.Connection, org.jpf.demo.toolbox.dbbrowser.DbObject, int)
     */
    @Override
    public ResultSet executeQuery(final Connection cnn, final DbObject dbObj,
            final int maxResult) throws SQLException {
        if ((dbObj.getType() != DbObject.TYPE_TABLE)
                && (dbObj.getType() != DbObject.TYPE_VIEW)) {
            return super.executeQuery(cnn, dbObj, maxResult);
        }
        return executeQuery(cnn, "SELECT * FROM " + dbObj.getName()
                + ((maxResult > 0) ? " LIMIT " + maxResult : ""), maxResult);
    }
}
