/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RuleAction.java 2881 2010-08-02 11:40:48Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

public interface RuleAction {
    String getId();

    String getDisplayName();

    String getRendererId();

    boolean hasValue();

    String value2str(Object value);

    Object str2value(String str);

    void buildInfo(StringBuilder buf, Object value);

    void buildScript(StringBuilder buf, RuleTarget target, Object value)
            throws Exception;

    String getRuleCategory(Object value) throws Exception;
}
