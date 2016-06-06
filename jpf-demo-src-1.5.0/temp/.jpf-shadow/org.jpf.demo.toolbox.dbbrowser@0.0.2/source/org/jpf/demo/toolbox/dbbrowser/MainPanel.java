// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.dbbrowser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.java.plugin.PluginManager;
import org.java.plugin.boot.ErrorDialog;

/**
 * @version $Id: MainPanel.java,v 1.1 2007/03/04 13:00:49 ddimon Exp $
 */
final class MainPanel extends JPanel {
    private static final long serialVersionUID = 5158318725725890704L;

    private JPanel topPanel = null;
	private JPanel bottomPanel = null;
	JComboBox connectionsComboBox = null;
	private JButton addNewCnnButton = null;
	private JButton deleteCnnButton = null;
	JSplitPane jSplitPane = null;
	JList dataObjectList = null;
	private JLabel cnnLabel = null;
	private JButton editCnnButton = null;
	private JPanel mainPanel = null;
	private JPanel queryPanel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JLabel queryLabel = null;
	private JButton executeQueryButton = null;
	JTextField queryTextField = null;
	private JButton listDbObjectsButton = null;
	private JScrollPane jScrollPane1 = null;  //  @jve:decl-index=0:visual-constraint="619,179"
	/**
	 * This is the default constructor
	 */
	MainPanel() {
		super();
		initialize();
        initConnectionList();
        try {
            Configuration config =
                ((DBBPlugin) PluginManager.lookup(this).getPlugin(
                        DBBPlugin.PLUGIN_ID)).getConfiguration();
            int dbObjectsListPaneSize = config.getDbObjectsListPaneSize();
            if (dbObjectsListPaneSize > 0) {
                jSplitPane.setDividerLocation(dbObjectsListPaneSize);
            }
            queryTextField.setText(config.getLastQuery());
        } catch (Exception ex) {
            // ignore
        }
	}

	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(560, 304);
		this.add(getTopPanel(), java.awt.BorderLayout.NORTH);
		this.add(getBottomPanel(), java.awt.BorderLayout.CENTER);
	}

    private void initConnectionList() {
        try {
            for (ConnectionInfo cnnInfo : ConnectionInfo.getAll()) {
                connectionsComboBox.addItem(cnnInfo);
            }
        } catch (Exception e) {
            ErrorDialog.showError(this, "Error", e);
        }
    }

    private JPanel getTopPanel() {
		if (topPanel == null) {
			cnnLabel = new JLabel();
			FlowLayout flowLayout4 = new FlowLayout();
			topPanel = new JPanel();
			topPanel.setLayout(flowLayout4);  // Generated
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
			cnnLabel.setText("Connection:");
			topPanel.add(cnnLabel, null);
			topPanel.add(getConnectionsComboBox(), null);  // Generated
			topPanel.add(getListDbObjectsButton(), null);
			topPanel.add(getAddNewCnnButton(), null);
			topPanel.add(getEditCnnButton(), null);
			topPanel.add(getDeleteCnnButton(), null);
		}
		return topPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());  // Generated
			bottomPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return bottomPanel;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getConnectionsComboBox() {
		if (connectionsComboBox == null) {
		    connectionsComboBox = new JComboBox();
		}
		return connectionsComboBox;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAddNewCnnButton() {
		if (addNewCnnButton == null) {
			addNewCnnButton = new JButton();
			addNewCnnButton.setText("Add new");
			addNewCnnButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    DbInfo dbInfo = (DbInfo) JOptionPane.showInputDialog(
                            MainPanel.this,
                            null, "Select Database Type",
                            JOptionPane.QUESTION_MESSAGE, null,
                            DbInfo.getAll().toArray(), null);
                    if (dbInfo == null) {
                        return;
                    }
                    ConnectionInfo cnnInfo =
                        ConnectionInfoDialog.showDialog(MainPanel.this, dbInfo);
                    if (cnnInfo == null) {
                        return;
                    }
                    connectionsComboBox.addItem(cnnInfo);
                    connectionsComboBox.setSelectedItem(cnnInfo);
				}
			});
		}
		return addNewCnnButton;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getDeleteCnnButton() {
		if (deleteCnnButton == null) {
			deleteCnnButton = new JButton();
			deleteCnnButton.setText("Delete");
			deleteCnnButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
                    ConnectionInfo cnnInfo =
                        (ConnectionInfo) connectionsComboBox.getSelectedItem();
                    if (cnnInfo == null) {
                        return;
                    }
                    if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(
                            MainPanel.this, "Sure to delete \"" + cnnInfo
                            + "\" connection?", "Confirm connection delete",
                            JOptionPane.YES_NO_OPTION)) {
                        return;
                    }
					try {
                        ConnectionInfo.remove(cnnInfo);
                        ConnectionInfo.saveAll();
                        connectionsComboBox.removeItem(cnnInfo);
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return deleteCnnButton;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(100);
			jSplitPane.setRightComponent(getMainPanel());
			jSplitPane.setLeftComponent(getJScrollPane1());
			jSplitPane.addPropertyChangeListener("lastDividerLocation", new java.beans.PropertyChangeListener() { 
				public void propertyChange(java.beans.PropertyChangeEvent e) {
                    try {
                        Configuration config =
                            ((DBBPlugin) PluginManager.lookup(this).getPlugin(
                                    DBBPlugin.PLUGIN_ID)).getConfiguration();
                        config.setDbObjectsListPaneSize(
                                jSplitPane.getDividerLocation());
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getDataObjectList() {
		if (dataObjectList == null) {
			dataObjectList = new JList();
			dataObjectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			dataObjectList.addMouseListener(new java.awt.event.MouseAdapter() { 
				@Override
                public void mouseClicked(final MouseEvent evt) {
                    if (evt.getClickCount() != 2) {
                        return;
                    }
                    ConnectionInfo cnnInfo =
                        (ConnectionInfo) connectionsComboBox.getSelectedItem();
                    if (cnnInfo == null) {
                        return;
                    }
                    DbObject dbObject =
                        (DbObject) dataObjectList.getSelectedValue();
                    if (dbObject == null) {
                        return;
                    }
                    try {
                        Connection cnn = cnnInfo.getConnection();
                        try {
                            int maxResult = 100;
                            ResultSet rs =
                                cnnInfo.getDbInfo().getHandler().executeQuery(
                                        cnn, dbObject, maxResult);
                            populateResultSet(rs, maxResult);
                            rs.close();
                        } finally {
                            cnn.close();
                        }
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return dataObjectList;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getEditCnnButton() {
		if (editCnnButton == null) {
			editCnnButton = new JButton();
			editCnnButton.setText("Edit");
			editCnnButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    ConnectionInfo cnnInfo =
                        (ConnectionInfo) connectionsComboBox.getSelectedItem();
                    if (cnnInfo == null) {
                        return;
                    }
                    ConnectionInfo newCnnInfo =
                        ConnectionInfoDialog.showDialog(MainPanel.this, cnnInfo);
                    if (newCnnInfo == null) {
                        return;
                    }
                    connectionsComboBox.removeItem(cnnInfo);
                    connectionsComboBox.addItem(newCnnInfo);
                    connectionsComboBox.setSelectedItem(newCnnInfo);
				}
			});
		}
		return editCnnButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getQueryPanel(), java.awt.BorderLayout.NORTH);
			mainPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return mainPanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getQueryPanel() {
		if (queryPanel == null) {
			queryLabel = new JLabel();
			BorderLayout borderLayout2 = new BorderLayout();
			queryPanel = new JPanel();
			queryPanel.setLayout(borderLayout2);
			queryLabel.setText("Query:");
			borderLayout2.setHgap(2);
			borderLayout2.setVgap(2);
			queryPanel.setEnabled(true);
			queryPanel.add(queryLabel, java.awt.BorderLayout.WEST);
			queryPanel.add(getExecuteQueryButton(), java.awt.BorderLayout.EAST);
			queryPanel.add(getQueryTextField(), java.awt.BorderLayout.CENTER);
		}
		return queryPanel;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getExecuteQueryButton() {
		if (executeQueryButton == null) {
			executeQueryButton = new JButton();
			executeQueryButton.setText("Execute");
			executeQueryButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    ConnectionInfo cnnInfo =
                        (ConnectionInfo) connectionsComboBox.getSelectedItem();
                    if (cnnInfo == null) {
                        return;
                    }
                    try {
                        Connection cnn = cnnInfo.getConnection();
                        try {
                            int maxResult = 100;
                            ResultSet rs =
                                cnnInfo.getDbInfo().getHandler().executeQuery(
                                        cnn, queryTextField.getText(), maxResult);
                            populateResultSet(rs, maxResult);
                            rs.close();
                        } finally {
                            cnn.close();
                        }
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return executeQueryButton;
	}
    
    void populateResultSet(final ResultSet rs, final int maxResult)
            throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        final String[]  colNames = new String[metaData.getColumnCount()];
        for (int i = 0; i < colNames.length; i++) {
            colNames[i] = metaData.getColumnName(i + 1);
        }
        LinkedList<String[]> rowList = new LinkedList<String[]>();
        while (rs.next()) {
            String[] row = new String[colNames.length];
            for (int i = 0; i < colNames.length; i++) {
                Object val = rs.getObject(i + 1);
                row[i] = (val != null) ? val.toString() : "";
            }
            rowList.add(row);
            if (rowList.size() >= maxResult) {
                break;
            }
        }
        final String[][] rows =
            rowList.toArray(new String[rowList.size()][]);
        rowList.clear();
        jTable.setModel(new AbstractTableModel() {
            private static final long serialVersionUID = 8371769144305864284L;

            public int getColumnCount() {
                return colNames.length;
            }
            
            @Override
            public String getColumnName(final int col) {
                if ((col < 0) || (col >= colNames.length)) {
                    return "";
                }
                return colNames[col];
            }
            
            public int getRowCount() {
                return rows.length;
            }
            
            public Object getValueAt(final int row, final int col) {
                if ((row < 0) || (row >= rows.length) || (col < 0)
                        || (col >= colNames.length)) {
                    return null;
                }
                return rows[row][col];
            }
        });
    }

    /**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getQueryTextField() {
		if (queryTextField == null) {
			queryTextField = new JTextField();
			queryTextField.addFocusListener(new java.awt.event.FocusAdapter() { 
				@Override
                public void focusLost(final FocusEvent evt) {
                    try {
                        Configuration config =
                            ((DBBPlugin) PluginManager.lookup(this).getPlugin(
                                    DBBPlugin.PLUGIN_ID)).getConfiguration();
                        config.setLastQuery(queryTextField.getText());
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return queryTextField;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getListDbObjectsButton() {
		if (listDbObjectsButton == null) {
			listDbObjectsButton = new JButton();
			listDbObjectsButton.setText("List DB Objects");
			listDbObjectsButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    ConnectionInfo cnnInfo =
                        (ConnectionInfo) connectionsComboBox.getSelectedItem();
                    if (cnnInfo == null) {
                        return;
                    }
                    try {
                        Connection cnn = cnnInfo.getConnection();
                        try {
                            populateDbObjects(cnnInfo.getDbInfo().getHandler()
                                    .getAllDbObjects(cnn));
                        } finally {
                            cnn.close();
                        }
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return listDbObjectsButton;
	}

    void populateDbObjects(final Collection<DbObject> allDbObjects) {
        final DbObject[] dbObjects =
            allDbObjects.toArray(new DbObject[allDbObjects.size()]);
        dataObjectList.setModel(new AbstractListModel() {
            private static final long serialVersionUID = 679262110000860629L;

            public int getSize() {
                return dbObjects.length;
            }
            
            public Object getElementAt(final int idx) {
                return dbObjects[idx];
            }
        });
    }
    
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getDataObjectList());
		}
		return jScrollPane1;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
