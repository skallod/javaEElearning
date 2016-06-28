/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RuleCondition.java 2881 2010-08-02 11:40:48Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

public interface RuleCondition {
    String getId();

    String getDisplayName();

    boolean hasRightSide();
}
