// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.pluginbrowser;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import org.jpf.demo.toolbox.core.Tool;

/**
 *
 * @version $Id: PBTool.java,v 1.1 2007/03/04 13:00:52 ddimon Exp $
 */
public class PBTool implements Tool {
    /**
     * @see org.jpf.demo.toolbox.core.Tool#init(javax.swing.JComponent)
     */
    public void init(final JComponent rootContainer) {
        rootContainer.setLayout(new BorderLayout());
        rootContainer.add(new MainPanel(), BorderLayout.CENTER);
    }
}
