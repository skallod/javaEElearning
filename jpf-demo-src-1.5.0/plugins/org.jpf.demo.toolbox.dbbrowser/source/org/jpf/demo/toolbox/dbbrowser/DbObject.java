// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.io.Serializable;

/**
 *
 * @version $Id: DbObject.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
public final class DbObject implements Serializable {
    private static final long serialVersionUID = 8975560977649857704L;

    /**
     * DB object of type TABLE.
     */
    public static final byte TYPE_TABLE = 1;
    
    /**
     * DB object of type VIEW.
     */
    public static final byte TYPE_VIEW = 2;
    
    /**
     * DB object of type STORED PROCEDURE.
     */
    public static final byte TYPE_SP = 3;
    
    /**
     * DB object of type FUNCTION.
     */
    public static final byte TYPE_FUNC = 4;
    
    private final String name;
    private final byte type;
    
    /**
     * Creates new instance of a DB object.
     * @param aName DB object name
     * @param aType DB object type
     */
    public DbObject(final String aName, final byte aType) {
        name = aName;
        type = aType;
    }
    
    /**
     * @return DB object name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return DB object type
     */
    public byte getType() {
        return type;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        switch (type) {
            case TYPE_FUNC: return "FUNCTION: " + name;
            case TYPE_SP: return "STORED PROCEDURE: " + name;
            case TYPE_VIEW: return "VIEW: " + name;
        }
        return "TABLE: " + name;
    }
}
