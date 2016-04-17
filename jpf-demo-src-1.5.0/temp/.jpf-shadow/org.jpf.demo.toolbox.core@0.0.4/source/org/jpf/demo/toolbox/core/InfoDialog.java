// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.core;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * Utility dialogue to display HTML text.
 * @version $Id: InfoDialog.java,v 1.1 2007/03/04 13:00:56 ddimon Exp $
 */
public final class InfoDialog extends JDialog {
    private static final long serialVersionUID = -4482028396605528343L;

    /**
     * Shows info dialogue.
     * @param title dialog window title
     * @param text HTML text to display
     */
    public static void show(String title, String text) {
        show(null, title, text);
    }
    
    /**
     * Shows info dialogue.
     * @param parentComponent parent component
     * @param title dialog window title
     * @param text HTML text to display
     */
    public static void show(Component parentComponent, String title, String text) {
        Frame frame = (parentComponent != null) ? JOptionPane
                .getFrameForComponent(parentComponent) : JOptionPane
                .getRootFrame();
        InfoDialog dialog = new InfoDialog(frame, title, text);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
        dialog.dispose();
    }

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton closeButton = null;
	private JScrollPane jScrollPane = null;
	private JTextPane jTextPane = null;
	/**
	 * This is the default constructor
	 */
    private InfoDialog(Frame frame, String title, String text) {
        super(frame);
		initialize();
        setTitle(title);
        getRootPane().setDefaultButton(closeButton);
        jTextPane.setText(text);
        jTextPane.setCaretPosition(0);
	}

	private void initialize() {
		this.setModal(true);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(467, 319);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getCloseButton(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("Close");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
		}
		return closeButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextPane());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setContentType("text/html");
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"
