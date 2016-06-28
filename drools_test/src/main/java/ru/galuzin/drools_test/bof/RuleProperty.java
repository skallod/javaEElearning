/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RuleProperty.java 55631 2013-08-29 08:52:00Z kozlov $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

public interface RuleProperty {
    String getId();

    String getDisplayName();

    String getRendererId();

    String value2str(Object value);

    Object str2value(String str);

    void buildInfo(StringBuilder buf, RuleCondition cond, Object value);

    void buildScript(StringBuilder buf, RuleCondition cond, Object value);

    String getHelpInfo();
}
