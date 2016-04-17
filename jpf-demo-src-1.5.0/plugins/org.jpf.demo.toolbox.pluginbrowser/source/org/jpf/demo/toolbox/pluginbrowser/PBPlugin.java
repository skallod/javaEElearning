// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.pluginbrowser;

import org.java.plugin.Plugin;

/**
 * @version $Id: PBPlugin.java,v 1.1 2007/03/04 13:00:52 ddimon Exp $
 */
public final class PBPlugin extends Plugin {
    /**
     * This plug-in ID.
     */
    public static final String PLUGIN_ID = "org.jpf.demo.toolbox.pluginbrowser";
    
    private Configuration configuration;
    
    /**
     * @see org.java.plugin.Plugin#doStart()
     */
    @Override
    protected void doStart() throws Exception {
        configuration = Configuration.load();
    }

    /**
     * @see org.java.plugin.Plugin#doStop()
     */
    @Override
    protected void doStop() throws Exception {
        Configuration.save(configuration);
    }
    
    Configuration getConfiguration() {
        return configuration;
    }
}
