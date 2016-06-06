// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.pluginbrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.java.plugin.PluginManager;
import org.jpf.demo.toolbox.core.CorePlugin;

/**
 * @version $Id: Configuration.java,v 1.1 2007/03/04 13:00:52 ddimon Exp $
 */
final class Configuration {
    static Configuration load() {
        Properties props = new Properties();
        try {
            PluginManager pm = PluginManager.lookup(Configuration.class);
            File dataFolder;
            dataFolder = ((CorePlugin) pm.getPlugin(CorePlugin.PLUGIN_ID))
                    .getDataFolder(pm.getRegistry().getPluginDescriptor(
                            PBPlugin.PLUGIN_ID));
            File file = new File(dataFolder, "config.properties");
            if (file.isFile()) {
                InputStream strm = new FileInputStream(file);
                try {
                    props.load(strm);
                } finally {
                    strm.close();
                }
            }
        } catch (Exception e) {
            LogFactory.getLog(Configuration.class).warn(
                    "failed loading plug-in settings", e);
        }
        return new Configuration(props);
    }
    
    static void save(Configuration config) {
        try {
            PluginManager pm = PluginManager.lookup(Configuration.class);
            File dataFolder = ((CorePlugin) pm.getPlugin(CorePlugin.PLUGIN_ID))
                    .getDataFolder(pm.getRegistry().getPluginDescriptor(
                            PBPlugin.PLUGIN_ID));
            OutputStream strm = new FileOutputStream(
                    new File(dataFolder, "config.properties"), false);
            try {
                config.toProps().store(strm,
                        "This is automatically generated configuration file.");
            } finally {
                strm.close();
            }
        } catch (Exception e) {
            LogFactory.getLog(Configuration.class).warn(
                    "failed saving plug-in settings", e);
        }
    }
    
    private File lastFolder;
    private int treePaneSize = 0;

    private Configuration(Properties props) {
        String prop = props.getProperty("lastFolder");
        if (prop != null) {
            lastFolder = new File(prop);
            if (!lastFolder.isDirectory()) {
                lastFolder = null;
            }
        }
        prop = props.getProperty("treePaneSize");
        if (prop != null) {
            try {
                treePaneSize = Integer.parseInt(prop, 10);
            } catch (NumberFormatException nfe) {
                treePaneSize = 0;
            }
        }
    }
    
    private Properties toProps() {
        Properties result = new Properties();
        if (lastFolder != null) {
            result.setProperty("lastFolder", lastFolder.getAbsolutePath());
        }
        if (treePaneSize > 0) {
            result.setProperty("treePaneSize", "" + treePaneSize);
        }
        return result;
    }
    
    File getLastFolder() {
        return lastFolder;
    }
    
    void setLastFolder(final File value) {
        this.lastFolder = value;
    }
    
    int getTreePaneSize() {
        return treePaneSize;
    }
    
    void setTreePaneSize(final int value) {
        this.treePaneSize = value;
    }
}
