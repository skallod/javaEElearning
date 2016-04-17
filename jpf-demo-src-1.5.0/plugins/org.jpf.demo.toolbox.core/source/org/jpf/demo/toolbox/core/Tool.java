// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.core;

import javax.swing.JComponent;

/**
 * "Home" interface for particular tool. It is used by application core
 * to start up tool when user selects appropriate "tab" on the main
 * application window.
 * @version $Id: Tool.java,v 1.1 2007/03/04 13:00:56 ddimon Exp $
 */
public interface Tool {
    /**
     * This method is called once during application life cycle to allow
     * tool to initialize and show itself.
     * @param rootContainer parent container for tool visual components
     */
    void init(JComponent rootContainer);
}
