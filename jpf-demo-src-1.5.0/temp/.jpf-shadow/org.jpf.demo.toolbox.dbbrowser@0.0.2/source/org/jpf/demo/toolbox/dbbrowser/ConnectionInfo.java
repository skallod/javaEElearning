// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.LogFactory;
import org.java.plugin.PluginManager;
import org.jpf.demo.toolbox.core.CorePlugin;

/**
 * @version $Id: ConnectionInfo.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
final class ConnectionInfo {
    private static boolean loaded = false;
    private static Set<ConnectionInfo> allCnnInfos =
        new HashSet<ConnectionInfo>();
    
    static Set<ConnectionInfo> getAll() throws Exception {
        if (!loaded) {
            loadAll();
        }
        return Collections.unmodifiableSet(allCnnInfos);
    }
    
    private static void loadAll() throws Exception {
        PluginManager pm = PluginManager.lookup(ConnectionInfo.class);
        File dataFolder = ((CorePlugin) pm.getPlugin(CorePlugin.PLUGIN_ID))
                .getDataFolder(pm.getRegistry().getPluginDescriptor(
                        DBBPlugin.PLUGIN_ID));
        File file = new File(dataFolder, "cnn.properties");
        if (!file.isFile()) {
            loaded = true;
            return;
        }
        Properties props = new Properties();
        InputStream strm = new FileInputStream(file);
        try {
            props.load(strm);
        } finally {
            strm.close();
        }
        for (int i = 0; ;i++) {
            try {
                ConnectionInfo cnnInfo = loadCnnInfo(props, i);
                if (cnnInfo == null) {
                    break;
                }
                allCnnInfos.add(cnnInfo);
            } catch (Exception e) {
                LogFactory.getLog(ConnectionInfo.class).warn(
                        "failed loading connection info", e);
            }
        }
        loaded = true;
    }
    
    private static ConnectionInfo loadCnnInfo(Properties props, int idx)
            throws Exception {
        String prefix = ConnectionInfo.class.getName() + "." + idx + ".";
        String dbId = props.getProperty(prefix + "db");
        if (dbId == null) {
            return null;
        }
        DbInfo dbInfo = DbInfo.getById(dbId);
        ConnectionInfo result = new ConnectionInfo(dbInfo);
        result.setName(props.getProperty(prefix + "name"));
        result.setUrl(props.getProperty(prefix + "url"));
        result.setUser(props.getProperty(prefix + "user"));
        result.setPassword(props.getProperty(prefix + "psw"));
        return result;
    }

    static void saveAll() throws Exception {
        if (!loaded) {
            return;
        }
        Properties props = new Properties();
        int i = 0;
        for (ConnectionInfo cnnInfo : allCnnInfos) {
            saveCnnInfo(props, cnnInfo, i);
        }
        PluginManager pm = PluginManager.lookup(ConnectionInfo.class);
        File dataFolder = ((CorePlugin) pm.getPlugin(CorePlugin.PLUGIN_ID))
                .getDataFolder(pm.getRegistry().getPluginDescriptor(
                        DBBPlugin.PLUGIN_ID));
        OutputStream strm = new FileOutputStream(
                new File(dataFolder, "cnn.properties"), false);
        try {
            props.store(strm,
                    "This is automatically generated configuration file.");
        } finally {
            strm.close();
        }
    }
    
    private static void saveCnnInfo(Properties props, ConnectionInfo cnnInfo,
            int idx) {
        String prefix = ConnectionInfo.class.getName() + "." + idx + ".";
        props.setProperty(prefix + "db", cnnInfo.getDbInfo().getId());
        props.setProperty(prefix + "name", cnnInfo.getName());
        props.setProperty(prefix + "url", cnnInfo.getUrl());
        props.setProperty(prefix + "user", cnnInfo.getUser());
        props.setProperty(prefix + "psw", cnnInfo.getPassword());
    }

    static ConnectionInfo getById(String id) throws Exception {
        if (!loaded) {
            loadAll();
        }
        for (ConnectionInfo cnnInfo : allCnnInfos) {
            if (id.equals(cnnInfo.getId())) {
                return cnnInfo;
            }
        }
        throw new Exception("unknown connection info ID - " + id);
    }
    
    static void add(ConnectionInfo cnnInfo) throws Exception {
        if (cnnInfo.name == null) {
            throw new Exception("required connection name not specified");
        }
        if (!loaded) {
            loadAll();
        }
        if (allCnnInfos.contains(cnnInfo)) {
            throw new Exception("connection " + cnnInfo + " already exists");
        }
        allCnnInfos.add(cnnInfo);
    }
    
    static void remove(ConnectionInfo cnnInfo) throws Exception {
        if (!loaded) {
            loadAll();
        }
        allCnnInfos.remove(cnnInfo);
    }
    
    private DbInfo dbInfo;
    private String name;
    private String url;
    private String user;
    private String password;
    
    ConnectionInfo(final DbInfo aDbInfo) {
        dbInfo = aDbInfo;
    }
    
    DbInfo getDbInfo() {
        return dbInfo;
    }
    
    String getId() {
        return name + "|" + dbInfo.getId();
    }
    
    String getName() {
        return name;
    }
    
    void setName(final String value) {
        this.name = value;
    }
    
    String getPassword() {
        return password;
    }
    
    void setPassword(final String value) {
        this.password = value;
    }
    
    String getUrl() {
        return url;
    }
    
    void setUrl(final String value) {
        this.url = value;
    }
    
    String getUser() {
        return user;
    }
    
    void setUser(final String value) {
        this.user = value;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConnectionInfo)) {
            return false;
        }
        return getId().equals(((ConnectionInfo) obj).getId());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ((name == null) ? "no name" : name) + " - " + dbInfo;
    }
    
    /**
     * Connects to the DB this info describes.
     * @return newly created DB connection
     * @throws Exception if any error has occurred
     */
    public Connection getConnection() throws Exception {
        return getConnection(url, user, password);
    }

    Connection getConnection(final String aUrl, final String aUser,
            final String aPassword) throws Exception {
        Properties props = new Properties();
        if (aUser != null) {
            props.put("user", aUser);
        }
        if (aPassword != null) {
            props.put("password", aPassword);
        }
        Connection result = dbInfo.getDriver().connect(aUrl, props);
        if (result == null) {
            throw new SQLException("failed to connect to " + aUrl);
        }
        return result;
    }
}
