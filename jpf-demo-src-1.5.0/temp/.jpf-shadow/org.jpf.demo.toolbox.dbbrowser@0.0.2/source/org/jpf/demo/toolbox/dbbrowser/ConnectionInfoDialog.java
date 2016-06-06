// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.java.plugin.boot.ErrorDialog;
import org.java.plugin.registry.Documentation;
import org.java.plugin.registry.Extension;
import org.jpf.demo.toolbox.core.InfoDialog;
/**
 *
 * @version $Id: ConnectionInfoDialog.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
final class ConnectionInfoDialog extends JDialog {
    private static final long serialVersionUID = -8513553948695704955L;

    static ConnectionInfo showDialog(Component parentComponent, DbInfo dbInfo) {
        return showDialog(parentComponent, new ConnectionInfo(dbInfo));
    }

    static ConnectionInfo showDialog(Component parentComponent,
            ConnectionInfo cnnInfo) {
        ConnectionInfoDialog dialog = new ConnectionInfoDialog(
                JOptionPane.getFrameForComponent(parentComponent), cnnInfo);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
        dialog.dispose();
        return dialog.getResult();
    }

    private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton saveButton = null;
	private JButton testButton = null;
	private JButton cancelButton = null;
    ConnectionInfo cnnInfo;
    boolean isResultOk = false;
    
	private JPanel mainPanel = null;
	private JLabel jLabel = null;
	JTextField cnnNameTextField = null;
	private JLabel jLabel1 = null;
	private JLabel cnnUrlLabel = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel cnnVendorLabel = null;
	JTextField cnnUrlTextField = null;
	JTextField cnnUserTextField = null;
	JPasswordField cnnPswPasswordField = null;
    
    ConnectionInfoDialog(Frame owner, ConnectionInfo connectionInfo) {
        super(owner);
        this.cnnInfo = connectionInfo;
        initialize();
        initData();
    }
    
    private void initData() {
        cnnVendorLabel.setText(cnnInfo.getDbInfo().getVendor());
        cnnNameTextField.setText(cnnInfo.getName());
        cnnUrlTextField.setText(cnnInfo.getUrl());
        cnnUserTextField.setText(cnnInfo.getUser());
        cnnPswPasswordField.setText(cnnInfo.getPassword());
        final Documentation<Extension> doc =
            cnnInfo.getDbInfo().getDocumentation();
        if (doc != null) {
            cnnVendorLabel.setToolTipText("Click to view DB driver notes");
            cnnVendorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cnnVendorLabel.addMouseListener(new java.awt.event.MouseAdapter() { 
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {    
                    InfoDialog.show(ConnectionInfoDialog.this, doc.getCaption(),
                            "<html><body>" + doc.getText() + "</body></html>");
                }
            });
        }
    }

    private ConnectionInfo getResult() {
        return isResultOk ? cnnInfo : null;
    }

	private void initialize() {
		this.setResizable(false);
		this.setTitle("Connection properties");
		this.setName("Edit connection parameters");
		this.setModal(true);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(330, 204);
		this.setContentPane(getJContentPane());
        getRootPane().setDefaultButton(saveButton);
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
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
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
			jPanel.add(getTestButton(), null);
			jPanel.add(getSaveButton(), null);
			jPanel.add(getCancelButton(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
			saveButton.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			saveButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (cnnNameTextField.getText().length() == 0) {
                        JOptionPane.showMessageDialog(ConnectionInfoDialog.this,
                                "Invalid connection name", "Invalid value",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
					if ((cnnInfo.getName() != null)
                            && !cnnInfo.getName().equals(
                                    cnnNameTextField.getText())) {
                        try {
                            ConnectionInfo.remove(cnnInfo);
                        } catch (Exception ex) {
                            ErrorDialog.showError(ConnectionInfoDialog.this,
                                    "Error", ex);
                            return;
                        }
                        cnnInfo = new ConnectionInfo(cnnInfo.getDbInfo());
                        cnnInfo.setName(cnnNameTextField.getText());
                    } else if (cnnInfo.getName() == null) {
                        cnnInfo.setName(cnnNameTextField.getText());
                        try {
                            ConnectionInfo.add(cnnInfo);
                        } catch (Exception ex) {
                            ErrorDialog.showError(ConnectionInfoDialog.this,
                                    "Error", ex);
                            return;
                        }
                    }
                    cnnInfo.setUrl(cnnUrlTextField.getText());
                    cnnInfo.setUser(cnnUserTextField.getText());
                    cnnInfo.setPassword(
                            new String(cnnPswPasswordField.getPassword()));
                    isResultOk = true;
                    try {
                        ConnectionInfo.saveAll();
                    } catch (Exception ex) {
                        ErrorDialog.showError(ConnectionInfoDialog.this,
                                "Error", ex);
                    }
                    dispose();
				}
			});
		}
		return saveButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getTestButton() {
		if (testButton == null) {
			testButton = new JButton();
			testButton.setText("Test");
			testButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        Connection cnn = cnnInfo.getConnection(
                                cnnUrlTextField.getText(),
                                cnnUserTextField.getText(),
                                new String(cnnPswPasswordField.getPassword()));
                        cnn.close();
                        JOptionPane.showMessageDialog(ConnectionInfoDialog.this,
                                "Connection to a DB successfully established");
                    } catch (Exception ex) {
                        ErrorDialog.showError(ConnectionInfoDialog.this,
                                "Error", ex);
                    }
				}
			});
		}
		return testButton;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setMnemonic(java.awt.event.KeyEvent.VK_CANCEL);
			cancelButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return cancelButton;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			jLabel = new JLabel();
			cnnVendorLabel = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			cnnUrlLabel = new JLabel();
			jLabel1 = new JLabel();
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			jLabel.setText("Vendor:");
			jLabel.setBounds(6, 7, 114, 20);
			jLabel1.setBounds(6, 34, 114, 20);
			jLabel1.setText("Connection name:");
			cnnUrlLabel.setBounds(6, 61, 114, 20);
			cnnUrlLabel.setText("Connection URL:");
			jLabel3.setBounds(6, 88, 114, 20);
			jLabel3.setText("DB user name:");
			jLabel4.setBounds(6, 115, 114, 20);
			jLabel4.setText("DB password:");
			cnnVendorLabel.setBounds(128, 7, 187, 20);
			cnnVendorLabel.setText("");
			mainPanel.add(jLabel, null);
			mainPanel.add(jLabel1, null);
			mainPanel.add(cnnUrlLabel, null);
			mainPanel.add(jLabel3, null);
			mainPanel.add(jLabel4, null);
			mainPanel.add(cnnVendorLabel, null);
			mainPanel.add(getCnnUrlTextField(), null);
			mainPanel.add(getCnnUserTextField(), null);
			mainPanel.add(getCnnPswPasswordField(), null);
			mainPanel.add(getCnnNameTextField(), null);
		}
		return mainPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getCnnNameTextField() {
		if (cnnNameTextField == null) {
			cnnNameTextField = new JTextField();
			cnnNameTextField.setBounds(128, 34, 187, 20);
		}
		return cnnNameTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getCnnUrlTextField() {
		if (cnnUrlTextField == null) {
			cnnUrlTextField = new JTextField();
			cnnUrlTextField.setBounds(128, 61, 187, 20);
		}
		return cnnUrlTextField;
	}
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getCnnUserTextField() {
		if (cnnUserTextField == null) {
			cnnUserTextField = new JTextField();
			cnnUserTextField.setBounds(128, 88, 187, 20);
		}
		return cnnUserTextField;
	}
	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */    
	private JPasswordField getCnnPswPasswordField() {
		if (cnnPswPasswordField == null) {
			cnnPswPasswordField = new JPasswordField();
			cnnPswPasswordField.setBounds(128, 115, 187, 20);
		}
		return cnnPswPasswordField;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
