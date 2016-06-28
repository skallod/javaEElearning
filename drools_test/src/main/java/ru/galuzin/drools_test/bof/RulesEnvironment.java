/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id: RulesEnvironment.java 2881 2010-08-02 11:40:48Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RulesEnvironment {
    public static interface Hook {
        void environmentInitialized(RulesEnvironment env);
    }

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"; //$NON-NLS-1$

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
    }

    private final Map<String, RuleTarget> targetMap =
        new HashMap<String, RuleTarget>();

    private final Map<String, RuleProperty> propertyMap =
        new HashMap<String, RuleProperty>();

    private final Map<String, RuleCondition> conditionMap =
        new HashMap<String, RuleCondition>();

    private final Map<String, RuleAction> actionMap =
        new HashMap<String, RuleAction>();

    private final Map<String, Object> rendererMap =
        new HashMap<String, Object>();

    private final Map<String, Set<String>> target2property =
        new HashMap<String, Set<String>>();

    private final Map<String, Set<String>> target2action =
        new HashMap<String, Set<String>>();

    private final Map<String, Set<String>> target2multiAction =
        new HashMap<String, Set<String>>();

    private final Map<String, Set<String>> property2condition =
        new HashMap<String, Set<String>>();

    public RulesEnvironment() {
        // no-op
    }

    public void reset() {
        targetMap.clear();
        propertyMap.clear();
        conditionMap.clear();
        actionMap.clear();
        rendererMap.clear();
        target2property.clear();
        target2action.clear();
        target2multiAction.clear();
        property2condition.clear();
    }

    public void registerTargets(final RuleTarget... targets) {
        for (RuleTarget target : targets) {
            if (targetMap.containsKey(target.getId())) {
                throw new IllegalArgumentException(String.format(
                    "target with ID %s already registered", target.getId())); //$NON-NLS-1$
            }
            targetMap.put(target.getId(), target);
        }
    }

    public RuleTarget findTarget(final String id) {
        return (id == null) ? null : targetMap.get(id);
    }

    public Collection<RuleTarget> getTargets() {
        return Collections.unmodifiableCollection(targetMap.values());
    }

    public void registerProperties(final RuleProperty... properties) {
        for (RuleProperty property : properties) {
            if (propertyMap.containsKey(property.getId())) {
                throw new IllegalArgumentException(String.format(
                    "property with ID %s already registered", //$NON-NLS-1$
                    property.getId()));
            }
            propertyMap.put(property.getId(), property);
        }
    }

    public RuleProperty findProperty(final String id) {
        return (id == null) ? null : propertyMap.get(id);
    }

    public void registerConditions(final RuleCondition... conditions) {
        for (RuleCondition condition : conditions) {
            if (conditionMap.containsKey(condition.getId())) {
                throw new IllegalArgumentException(String.format(
                    "condition with ID %s already registered", //$NON-NLS-1$
                    condition.getId()));
            }
            conditionMap.put(condition.getId(), condition);
        }
    }

    public RuleCondition findCondition(final String id) {
        return (id == null) ? null : conditionMap.get(id);
    }

    public void registerActions(final RuleAction... actions) {
        for (RuleAction action : actions) {
            if (actionMap.containsKey(action.getId())) {
                throw new IllegalArgumentException(String.format(
                    "action with ID %s already registered", action.getId())); //$NON-NLS-1$
            }
            actionMap.put(action.getId(), action);
        }
    }

    public RuleAction findAction(final String id) {
        return (id == null) ? null : actionMap.get(id);
    }

    public void registerRenderer(final String flavor, final String id,
                                 final Object renderer) {
        String key = flavor + ':' + id;
        if (rendererMap.containsKey(key)) {
            throw new IllegalArgumentException(String.format(
                "renderer with ID %s already registered", key)); //$NON-NLS-1$
        }
        rendererMap.put(key, renderer);
    }

    public Object findRenderer(final String flavor, final String id) {
        return ((flavor == null) || (id == null)) ? null : rendererMap
            .get(flavor + ':' + id);
    }

    public void registerTarget2propertyAssociation(final String targetId,
            final String propertyId) {
        Set<String> set = target2property.get(targetId);
        if (set == null) {
            set = new HashSet<String>();
            target2property.put(targetId, set);
        }
        set.add(propertyId);
    }

    public Collection<RuleProperty> getProperties(final RuleTarget target) {
        Set<String> set = target2property.get(target.getId());
        if (set == null) {
            return Collections.emptyList();
        }
        Collection<RuleProperty> result =
            new ArrayList<RuleProperty>(set.size());
        for (String id : set) {
            RuleProperty property = propertyMap.get(id);
            if (property != null) {
                result.add(property);
            }
        }
        return result;
    }

    public void registerTarget2actionAssociation(final String targetId,
            final String actionId) {
        Set<String> set = target2action.get(targetId);
        if (set == null) {
            set = new HashSet<String>();
            target2action.put(targetId, set);
        }
        set.add(actionId);
    }

    public Collection<RuleAction> getActions(final RuleTarget target) {
        Set<String> set = target2action.get(target.getId());
        if (set == null) {
            return Collections.emptyList();
        }
        Collection<RuleAction> result = new ArrayList<RuleAction>(set.size());
        for (String id : set) {
            RuleAction action = actionMap.get(id);
            if (action != null) {
                result.add(action);
            }
        }
        return result;
    }

    public void registerTarget2multiActionAssociation(final String targetId,
            final String actionId) {
        Set<String> set = target2multiAction.get(targetId);
        if (set == null) {
            set = new HashSet<String>();
            target2multiAction.put(targetId, set);
        }
        set.add(actionId);
    }

    public void registerProperty2conditionAssociation(final String propertyId,
            final String conditionId) {
        Set<String> set = property2condition.get(propertyId);
        if (set == null) {
            set = new HashSet<String>();
            property2condition.put(propertyId, set);
        }
        set.add(conditionId);
    }

    public Collection<RuleCondition> getConditions(final RuleProperty property) {
        Set<String> set = property2condition.get(property.getId());
        if (set == null) {
            return Collections.emptyList();
        }
        Collection<RuleCondition> result =
            new ArrayList<RuleCondition>(set.size());
        for (String id : set) {
            RuleCondition condition = conditionMap.get(id);
            if (condition != null) {
                result.add(condition);
            }
        }
        return result;
    }
}
