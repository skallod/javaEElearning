/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof
 *
 * $Id: RuleEngine.java 32214 2013-02-18 12:34:46Z olshansky $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.drools.compiler.PackageBuilder;

public class RuleEngine {
    static final Log LOG = LogFactory.getLog(RuleEngine.class);

    static {
        System.setProperty("drools.dateformat", RulesEnvironment.DATE_FORMAT);
    }

    private final LRUMap entries;

    private final File folder;

    private Timer timer;

    public RuleEngine(final File persistenceFolder, final int size) {
        entries = new LRUMap(size);
        folder = persistenceFolder;
        LOG.info(String.format(
                "created, cashSize = %s, persistance folder is %s",
                Integer.toString(size), folder != null ? folder : "undefined"));
        if (LOG.isDebugEnabled()) {
            long interval = 5 * 1000 * 60;
            timer = new Timer("rule-engine-stat", true);
            timer.schedule(new TimerTask() {
                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    StringBuilder result =
                            new StringBuilder("execution rule set statistics:\r\n");
                    synchronized (entries) {
                        Map<String, Long> allFiles =
                                new HashMap<String, Long>();
                        if (folder != null) {
                            try {
                                for (File file : folder.listFiles()) {
                                    allFiles.put(file.getName(),
                                            Long.valueOf(file.length()));
                                }
                            } catch (Throwable e) {
                                LOG.error("unable to get files info", e);
                            }
                        }
                        @SuppressWarnings("unchecked")
                        Collection<ExecutionSetEntry> allEntries =
                                entries.values();
                        long filesSize = 0;
                        for (ExecutionSetEntry entry : allEntries) {
                            if (allFiles.containsKey(entry.getRulesUid())) {
                                filesSize +=
                                        allFiles.get(entry.getRulesUid())
                                                .longValue();
                            }
                        }
                        result
                                .append(String
                                        .format(
                                                "at memory cache: %s rule sets (approximate size = %s MB)\r\n",
                                                Integer.valueOf(allEntries.size()),
                                                Long.valueOf(filesSize / (1024 * 1024))));
                        filesSize = 0;
                        for (Long fileSize : allFiles.values()) {
                            filesSize += fileSize.longValue();
                        }
                        result
                                .append(String
                                        .format(
                                                "at disk cache: %s rule sets (approximate size = %s MB)\r\n",
                                                Integer.toString(allFiles.size()),
                                                Long.toString(filesSize / (1024 * 1024))));
                    }
                    LOG.debug(result.toString());
                }
            }, interval, interval);
        }
    }

    public void dispose() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        synchronized (entries) {
            entries.clear();
        }
        LOG.info("disposed");
    }

    public void execute(final RulesData rules,
                        final List<? extends RuleProxy<?, ?>> data) throws Exception {
        long timing = System.currentTimeMillis();
        ExecutionSetEntry entry;
        synchronized (entries) {
            entry = (ExecutionSetEntry) entries.get(rules.getUid());
            if (entry == null) {
                entry = ExecutionSetEntry.load(folder, rules.getUid());
                if (entry == null) {
                    entry = new ExecutionSetEntry(rules.getUid());
                }
                entries.put(entry.getRulesUid(), entry);
            }
        }
        entry.getSession(rules, folder).execute(data);
        MiscUtil.logTiming(timing, "executed rules " + rules);
    }

    private final static class ExecutionSetEntry implements Serializable {
        private static final long serialVersionUID = 4083712991893127754L;

        static ExecutionSetEntry load(final File folder, final String uid) {
            if (folder == null) {
                return null;
            }
            File file = new File(folder, uid);
            if (!file.isFile()) {
                return null;
            }
            ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(
                        RuleEngine.class.getClassLoader());
                ObjectInput strm =
                        new ObjectInputStream(new FileInputStream(file));
                try {
                    ExecutionSetEntry result =
                            (ExecutionSetEntry) strm.readObject();
                    LOG.debug("execution set data loaded from " + file);
                    return result;
                } finally {
                    strm.close();
                }
            } catch (Exception e) {
                LOG.warn("failed loading execution set data from file " + file,
                        e);
                return null;
            } finally {
                Thread.currentThread().setContextClassLoader(oldCl);
            }
        }

        private final String rulesUid;

        private Date timestamp;

        private StatelessSession session;

        ExecutionSetEntry(final String uid) {
            rulesUid = uid;
        }

        String getRulesUid() {
            return rulesUid;
        }

        synchronized StatelessSession getSession(final RulesData rules,
                                                 final File folder) throws Exception {
            if (!MiscUtil.equals(rules.getModified(), timestamp)) {
                session = null;
            }
            if (session != null) {
                return session;
            }
            ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(
                        RuleEngine.class.getClassLoader());
                PackageBuilder builder = new PackageBuilder();
                StringBuilder script = new StringBuilder();
                rules.buildScript(script);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("compiling rules script:\r\n" + script);
                }
                builder.addPackageFromDrl(new StringReader(script.toString()));
                RuleBase ruleBase = RuleBaseFactory.newRuleBase();
                ruleBase.addPackage(builder.getPackage());
                session = ruleBase.newStatelessSession();
                LOG.debug("(re-)built rules for " + rules.getUid());
                timestamp = rules.getModified();
            } finally {
                Thread.currentThread().setContextClassLoader(oldCl);
            }
            save(folder);
            return session;
        }

        private void save(final File folder) {
            if (folder == null) {
                return;
            }
            File file = new File(folder, rulesUid);
            try {
                ObjectOutput strm =
                        new ObjectOutputStream(new FileOutputStream(file));
                try {
                    strm.writeObject(this);
                    LOG.debug("execution set data wrote to " + file);
                } finally {
                    strm.close();
                }
            } catch (Exception e) {
                LOG.warn("failed writing execution set data to " + file, e);
            }
        }
    }
}
