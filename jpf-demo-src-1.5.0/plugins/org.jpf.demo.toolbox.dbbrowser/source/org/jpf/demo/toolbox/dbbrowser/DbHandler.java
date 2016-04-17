// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @version $Id: DbHandler.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
public interface DbHandler {
    /**
     * Lists all available DB objects (tables, views etc.).
     * @param cnn DB connection
     * @return collection of {@link DbObject DB objects}
     * @throws SQLException
     */
    Collection<DbObject> getAllDbObjects(Connection cnn) throws SQLException;
    
    /**
     * Retrieves data from the given DB object.
     * @param cnn DB connection
     * @param dbObj DB object to fetch data from
     * @param limit data records count limit
     * @return data stored in given DB object
     * @throws SQLException if any data access error has occurred
     */
    ResultSet executeQuery(Connection cnn, DbObject dbObj, int limit)
            throws SQLException;
    
    /**
     * Executes SQL query.
     * @param cnn DB connection
     * @param queryStr SQL query
     * @param limit data records count limit
     * @return SQL query execution result
     * @throws SQLException if any data access error has occurred
     */
    ResultSet executeQuery(Connection cnn, String queryStr, int limit)
            throws SQLException;
}
