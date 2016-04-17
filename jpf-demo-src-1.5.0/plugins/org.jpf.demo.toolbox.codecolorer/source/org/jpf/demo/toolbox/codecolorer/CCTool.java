// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.codecolorer;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import org.jpf.demo.toolbox.core.Tool;


/**
 *
 * @version $Id: CCTool.java,v 1.1 2007/03/04 13:00:51 ddimon Exp $
 */
public final class CCTool implements Tool {
    /**
     * @see org.jpf.demo.toolbox.core.Tool#init(javax.swing.JComponent)
     */
    public void init(final JComponent rootContainer) {
        rootContainer.setLayout(new BorderLayout());
        rootContainer.add(new CodeColorer(), BorderLayout.CENTER);
    }
}
