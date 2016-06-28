/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF Unifest
 *
 * $Id: RulesData.java 61097 2013-10-17 10:17:18Z kozlov $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

import java.util.Date;

//import com.gridnine.bof.common.rules.RuleSet;

public interface RulesData {
    String getUid();

    Date getModified();

    void buildScript(StringBuilder buf) throws Exception;

    //RuleSet getRuleSet() throws Exception;
}
