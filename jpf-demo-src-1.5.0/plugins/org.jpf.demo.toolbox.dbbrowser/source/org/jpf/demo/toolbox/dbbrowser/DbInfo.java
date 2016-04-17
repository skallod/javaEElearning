// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.sql.Driver;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.java.plugin.PluginManager;
import org.java.plugin.registry.Documentation;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.Extension.Parameter;

/**
 * @version $Id: DbInfo.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
final class DbInfo {
    private static Set<DbInfo> allDbInfos;

    static Set<DbInfo> getAll() {
        if (allDbInfos == null) {
            allDbInfos = new HashSet<DbInfo>();
            PluginManager mngr = PluginManager.lookup(DbInfo.class);
            Collection<Extension> extensions =
                mngr.getRegistry().getExtensionPoint(
                    DBBPlugin.PLUGIN_ID, "Database").getConnectedExtensions();
            for (Extension ext : extensions) {
                allDbInfos.add(new DbInfo(ext));
            }
            allDbInfos = Collections.unmodifiableSet(allDbInfos);
        }
        return allDbInfos;
    }
    
    static DbInfo getById(String id) throws Exception {
        for (DbInfo dbInfo : getAll()) {
            if (id.equals(dbInfo.getId())) {
                return dbInfo;
            }
        }
        throw new Exception("unknown DB info ID - " + id);
    }

    private Extension ext;
    private Driver driver;
    private DbHandler handler;

    private DbInfo(final Extension anExt) {
        ext = anExt;
    }
    
    String getId() {
        return ext.getUniqueId();
    }

    Driver getDriver() throws Exception {
        if (driver == null) {
            String className = ext.getParameter("driverClass").valueAsString();
            PluginManager mngr = PluginManager.lookup(this);
            mngr.activatePlugin(ext.getDeclaringPluginDescriptor().getId());
            ClassLoader classLoader = mngr.getPluginClassLoader(
                    ext.getDeclaringPluginDescriptor());
            driver = (Driver) (classLoader.loadClass(className)).newInstance();
        }
        return driver;
    }
    
    String getVendor() {
        return ext.getParameter("vendor").valueAsString();
    }
    
    Documentation<Extension> getDocumentation() {
        return ext.getDocumentation();
    }
    
    DbHandler getHandler() throws Exception {
        if (handler != null) {
            return handler;
        }
        Parameter param = ext.getParameter("handlerClass");
        if (param != null) {
            handler = (DbHandler) PluginManager.lookup(this)
                .getPluginClassLoader(ext.getDeclaringPluginDescriptor())
                .loadClass(param.valueAsString()).newInstance();
        } else {
            handler = new DefaultDbHandler();
        }
        return handler;
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
        if (!(obj instanceof DbInfo)) {
            return false;
        }
        return getId().equals(((DbInfo) obj).getId());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getVendor();
    }
}
