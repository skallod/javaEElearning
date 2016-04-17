// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser.jtds;

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
 * @version $Id: JtdsDbHandler.java,v 1.1 2007/03/04 13:01:03 ddimon Exp $
 */
public final class JtdsDbHandler extends DefaultDbHandler {
    /**
     * @throws SQLException
     * @see org.jpf.demo.toolbox.dbbrowser.DefaultDbHandler#getAllDbObjects(
     *      java.sql.Connection)
     */
    @Override
    public Collection<DbObject> getAllDbObjects(final Connection cnn)
            throws SQLException {
        ResultSet rs = cnn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY).executeQuery(
                        "SELECT name, type  FROM sysobjects WHERE category=0");
        try {
            List<DbObject> result = new LinkedList<DbObject>();
            while (rs.next()) {
                String typeStr = rs.getString(2).trim();
                byte type;
                if ("U".equals(typeStr)) {
                    type = DbObject.TYPE_TABLE;
                } else if ("V".equals(typeStr)) {
                    type = DbObject.TYPE_VIEW;
                } else if ("P".equals(typeStr)) {
                    type = DbObject.TYPE_SP;
                } else if ("FN".equals(typeStr)) {
                    type = DbObject.TYPE_FUNC;
                } else {
                    continue;
                }
                result.add(new DbObject(rs.getString(1), type));
            }
            return result;
        } finally {
            rs.close();
        }
    }
}
