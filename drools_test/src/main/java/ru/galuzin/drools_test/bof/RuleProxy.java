/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RuleProxy.java 33080 2013-02-25 16:03:45Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RuleProxy<P, A> implements Serializable {
    private static final long serialVersionUID = 6358220196476893893L;

    private Date proxyDate;

    private String proxyType;

    private String roundingMode = "UP";

    private String roundingValue = "V1";

    private final Set<String> appliedRules = new LinkedHashSet<String>();

    private final Set<String> appliedRuleCategories =
        new LinkedHashSet<String>();

    private P propertySet;

    private A actionSet;

    //private final RuleLogTracer tracer = new RuleLogTracer();

    public Date getProxyDate() {
        return proxyDate;
    }

    public void setProxyDate(final Date value) {
        proxyDate = value;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(final String value) {
        proxyType = value;
    }

    public String getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(final String value) {
        roundingMode = value;
    }

    public String getRoundingValue() {
        return roundingValue;
    }

    public void setRoundingValue(final String value) {
        roundingValue = value;
    }

    public Set<String> getAppliedRules() {
        return appliedRules;
    }

    public Set<String> getAppliedRuleCategories() {
        return appliedRuleCategories;
    }

    public P getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(final P value) {
        propertySet = value;
    }

    public A getActionSet() {
        return actionSet;
    }

    public void setActionSet(final A value) {
        actionSet = value;
    }

//    public RuleLogTracer getTracer() {
//        return tracer;
//    }
}
