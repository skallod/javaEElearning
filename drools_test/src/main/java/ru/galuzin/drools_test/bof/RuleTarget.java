/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RuleTarget.java 2881 2010-08-02 11:40:48Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

public interface RuleTarget {
    String getId();

    String getDisplayName();

    String getPropertySetClassName();

    String getActionSetClassName();
}
