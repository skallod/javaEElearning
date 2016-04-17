// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.pluginbrowser;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.java.plugin.ObjectFactory;
import org.java.plugin.PluginManager;
import org.java.plugin.boot.ErrorDialog;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.java.plugin.registry.Identity;
import org.java.plugin.registry.IntegrityCheckReport;
import org.java.plugin.registry.PluginDescriptor;
import org.java.plugin.registry.PluginFragment;
import org.java.plugin.registry.PluginRegistry;
import org.java.plugin.registry.PluginRegistry.RegistryChangeData;
import org.java.plugin.registry.PluginRegistry.RegistryChangeListener;
import org.java.plugin.tools.PluginArchiver;
import org.java.plugin.util.IoUtil;
import org.onemind.jxp.JxpProcessingContext;
import org.onemind.jxp.JxpProcessor;

/**
 * @version $Id: MainPanel.java,v 1.1 2007/03/04 13:00:52 ddimon Exp $
 */
final class MainPanel extends JPanel {
    private static final long serialVersionUID = 8531058675733981722L;

    private static URL getManifestUrl(final File file) {
        try {
            if (file.isDirectory()) {
                File result = new File(file, "plugin.xml"); //$NON-NLS-1$
                if (result.isFile()) {
                    return IoUtil.file2url(result);
                }
                result = new File(file, "plugin-fragment.xml"); //$NON-NLS-1$
                if (result.isFile()) {
                    return IoUtil.file2url(result);
                }
                return null;
            }
            if (!file.isFile()) {
                return null;
            }
            URL url = new URL("jar:" //$NON-NLS-1$
                    + IoUtil.file2url(file).toExternalForm()
                    + "!/plugin.xml"); //$NON-NLS-1$
            if (IoUtil.isResourceExists(url)) {
                return url;
            }
            url = new URL("jar:" //$NON-NLS-1$
                    + IoUtil.file2url(file).toExternalForm()
                    + "!/plugin-fragment.xml"); //$NON-NLS-1$
            if (IoUtil.isResourceExists(url)) {
                return url;
            }
            url = new URL("jar:" //$NON-NLS-1$
                    + IoUtil.file2url(file).toExternalForm()
                    + "!/META-INF/plugin.xml"); //$NON-NLS-1$
            if (IoUtil.isResourceExists(url)) {
                return url;
            }
            url = new URL("jar:" //$NON-NLS-1$
                    + IoUtil.file2url(file).toExternalForm()
                    + "!/META-INF/plugin-fragment.xml"); //$NON-NLS-1$
            if (IoUtil.isResourceExists(url)) {
                return url;
            }
        } catch (MalformedURLException mue) {
            // ignore
        }
        return null;
    }

    JSplitPane jSplitPane = null;
	private JPanel leftPanel = null;
	private JPanel jPanel = null;
	private JButton addManifestButton = null;
	private JScrollPane jScrollPane = null;
	JTree jTree = null;
	private JButton removeManifestButton = null;
    final PluginRegistry registry =
        ObjectFactory.newInstance().createRegistry();
    private final JxpProcessor processor;

	private JScrollPane jScrollPane1 = null;
	private JTextPane htmlTextPane = null;
	private JButton checkIntegrityButton = null;
    private JButton showGraphButton = null;

    /**
	 * This is the default constructor
	 */
	MainPanel() {
		super();
		initialize();
        String templatesPath = this.getClass().getName().substring(0,
                this.getClass().getName().lastIndexOf('.')).replace('.', '/')
                + "/templates/";
        processor =
            new JxpProcessor(new ClassPathPageSource(templatesPath, "UTF-8"));
        try {
            Configuration config =
                ((PBPlugin) PluginManager.lookup(this).getPlugin(
                        PBPlugin.PLUGIN_ID)).getConfiguration();
            int treePaneSize = config.getTreePaneSize();
            if (treePaneSize > 0) {
                jSplitPane.setDividerLocation(treePaneSize);
            }
        } catch (Exception ex) {
            // ignore
        }
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(656, 310);
		this.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
	}
    
    void addManifests(final File[] files) throws Exception {
        if (files.length == 0) {
            return;
        }
        RegistryChangeListenerImpl listener = new RegistryChangeListenerImpl();
        registry.registerListener(listener);
        try {
            Set<URL> manifests = new HashSet<URL>();
            Set<URL> archives = new HashSet<URL>();
            for (int i = 0; i < files.length; i++) {
                collectManifests(files[i], manifests, archives);
            }
            registry.register(manifests.toArray(new URL[manifests.size()]));
            for (URL url : archives) {
                PluginArchiver.readDescriptor(url, registry);
            }
        } finally {
            registry.unregisterListener(listener);
        }
        updateTree();
        showRegistryChanges(listener);
    }
    
    void showRegistryChanges(final RegistryChangeListenerImpl listener) {
        Map<String, Set<String>> ctx = new HashMap<String, Set<String>>();
        ctx.put("addedPlugins", listener.addedPlugins());
        ctx.put("removedPlugins", listener.removedPlugins());
        ctx.put("modifiedPlugins", listener.modifiedPlugins());
        ctx.put("addedExtensions", listener.addedExtensions());
        ctx.put("removedExtensions", listener.removedExtensions());
        ctx.put("modifiedExtensions", listener.modifiedExtensions());
        renderTemplate("registryChangeReport.jxp", ctx);
    }
    
    void updateTree() {
        jTree.clearSelection();
        DefaultTreeModel model =
            new DefaultTreeModel(new DefaultMutableTreeNode("Root"));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        DefaultMutableTreeNode pluginsNode =
            new DefaultMutableTreeNode("Plug-ins");
        root.add(pluginsNode);
        for (PluginDescriptor descr : registry.getPluginDescriptors()) {
            TreeNodeImpl node = new TreeNodeImpl(descr);
            populateDescriptor(node, descr);
            pluginsNode.add(node);
        }
        DefaultMutableTreeNode fragmentsNode =
            new DefaultMutableTreeNode("Plug-in fragments");
        root.add(fragmentsNode);
        for (PluginFragment fragment : registry.getPluginFragments()) {
            fragmentsNode.add(new TreeNodeImpl(fragment));
        }
        jTree.setModel(model);
        jTree.scrollPathToVisible(new TreePath(
                ((DefaultMutableTreeNode)root.getFirstChild()).getPath()));
    }
    
    private void populateDescriptor(final TreeNodeImpl parentNode,
            final PluginDescriptor descr) {
        DefaultMutableTreeNode extPointsNode =
            new DefaultMutableTreeNode("Extension points");
        for (ExtensionPoint extPoint : descr.getExtensionPoints()) {
            extPointsNode.add(new TreeNodeImpl(extPoint));
        }
        parentNode.add(extPointsNode);
        DefaultMutableTreeNode extensionsNode =
            new DefaultMutableTreeNode("Extensions");
        for (Extension ext : descr.getExtensions()) {
            extensionsNode.add(new TreeNodeImpl(ext));
        }
        parentNode.add(extensionsNode);
    }

    void renderTemplate(final String name, final Map<String, ?> ctx) {
        StringWriter writer = new StringWriter();
        try {
            processor.process(name, new JxpProcessingContext(writer, ctx));
        } catch (Throwable t) {
            ErrorDialog.showError(this, "Error", t);
        }
        htmlTextPane.setText(writer.getBuffer().toString());
        htmlTextPane.setCaretPosition(0);
    }

    private void collectManifests(final File file, final Set<URL> manifests,
            final Set<URL> archives) throws Exception {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".jpa")) {
            archives.add(file.toURL());
            return;
        }
        URL url = getManifestUrl(file);
        if (url != null) {
            manifests.add(url);
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                collectManifests(files[i], manifests, archives);
            }
        }
    }

    /**
	 * This method initializes jSplitPane	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getLeftPanel());
			jSplitPane.setRightComponent(getJScrollPane1());
            jSplitPane.setDividerLocation(290);
			jSplitPane.addPropertyChangeListener("lastDividerLocation",
                    new PropertyChangeListener() { 
				public void propertyChange(final PropertyChangeEvent evt) {
                    try {
                        Configuration config =
                            ((PBPlugin) PluginManager.lookup(this).getPlugin(
                                    PBPlugin.PLUGIN_ID)).getConfiguration();
                        config.setTreePaneSize(jSplitPane.getDividerLocation());
                    } catch (Exception ex) {
                        ErrorDialog.showError(MainPanel.this, "Error", ex);
                    }
				}
			});
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new JPanel();
			leftPanel.setLayout(new BorderLayout());
			leftPanel.add(getJPanel(), java.awt.BorderLayout.NORTH);
			leftPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return leftPanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getAddManifestButton(), null);
			jPanel.add(getRemoveManifestButton(), null);
			jPanel.add(getCheckIntegrityButton(), null);
			jPanel.add(getShowGraphButton(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAddManifestButton() {
		if (addManifestButton == null) {
			addManifestButton = new JButton();
            addManifestButton.setIcon(new ImageIcon(
                    getClass().getClassLoader().getResource("plus.gif")));
			addManifestButton.setToolTipText("Register plug-in(s)");
			addManifestButton.addActionListener(new ActionListener() { 
				public void actionPerformed(final ActionEvent evt) {
                    try {
                        Configuration config =
                            ((PBPlugin) PluginManager.lookup(this).getPlugin(
                                    PBPlugin.PLUGIN_ID)).getConfiguration();
                        File lastFolder = config.getLastFolder();
                        JFileChooser fc = (lastFolder != null)
                            ? new JFileChooser(lastFolder) : new JFileChooser();
                        fc.setFileSelectionMode(
                                JFileChooser.FILES_AND_DIRECTORIES);
                        fc.setMultiSelectionEnabled(true);
                        fc.showOpenDialog(MainPanel.this);
                        config.setLastFolder(fc.getCurrentDirectory());
                        addManifests(fc.getSelectedFiles());
                    } catch (Throwable t) {
                        ErrorDialog.showError(MainPanel.this, "Error", t);
                    }
				}
			});
		}
		return addManifestButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree(new DefaultTreeModel(
                    new DefaultMutableTreeNode("Root")));
			jTree.setRootVisible(false);
            jTree.setEditable(false);
            jTree.setShowsRootHandles(true);
            jTree.addTreeSelectionListener(new TreeSelectionListener() { 
            	public void valueChanged(final TreeSelectionEvent evt) {
            		Object node = jTree.getLastSelectedPathComponent();
                    if (!(node instanceof TreeNodeImpl)) {
                        return;
                    }
                    Identity idt = ((TreeNodeImpl) node).getIdentity();
                    Map<String, Identity> ctx = new HashMap<String, Identity>();
                    String template;
                    if (idt instanceof PluginDescriptor) {
                        ctx.put("descriptor", idt);
                        template = "plugin.jxp";
                    } else if (idt instanceof PluginFragment) {
                        ctx.put("fragment", idt);
                        template = "fragment.jxp";
                    } else if (idt instanceof ExtensionPoint) {
                        ctx.put("extPoint", idt);
                        template = "extPoint.jxp";
                    } else if (idt instanceof Extension) {
                        ctx.put("ext", idt);
                        template = "ext.jxp";
                    } else {
                        JOptionPane.showMessageDialog(MainPanel.this,
                                "Unknown identity: " + idt);
                        return;
                    }
                    renderTemplate(template, ctx);
            	}
            });
		}
		return jTree;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getRemoveManifestButton() {
		if (removeManifestButton == null) {
			removeManifestButton = new JButton();
            removeManifestButton.setIcon(new ImageIcon(
                    getClass().getClassLoader().getResource("minus.gif")));
            removeManifestButton.setToolTipText("Unregister plug-in");
			removeManifestButton.addActionListener(new ActionListener() { 
				public void actionPerformed(final ActionEvent evt) {
                    Object node = jTree.getLastSelectedPathComponent();
                    if (!(node instanceof TreeNodeImpl)) {
                        return;
                    }
                    Identity idt = ((TreeNodeImpl) node).getIdentity();
                    if (!(idt instanceof PluginDescriptor)
                            && !(idt instanceof PluginFragment)) {
                        return;
                    }
                    RegistryChangeListenerImpl listener =
                        new RegistryChangeListenerImpl();
                    registry.registerListener(listener);
                    try {
                        registry.unregister(new String[] {idt.getId()});
                    } finally {
                        registry.unregisterListener(listener);
                    }
                    updateTree();
                    showRegistryChanges(listener);
				}
			});
		}
		return removeManifestButton;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getHtmlTextPane());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	private JTextPane getHtmlTextPane() {
		if (htmlTextPane == null) {
			htmlTextPane = new JTextPane();
			htmlTextPane.setContentType("text/html");
			htmlTextPane.setEditable(false);
			htmlTextPane.addHyperlinkListener(new HyperlinkListener() { 
				public void hyperlinkUpdate(final HyperlinkEvent evt) {
                    if (!evt.getEventType().equals(
                            HyperlinkEvent.EventType.ACTIVATED)) {
                        return;
                    }
                    String descr = evt.getDescription();
                    int p = descr.indexOf(':');
                    if (p == -1) {
                        JOptionPane.showMessageDialog(MainPanel.this,
                                "Unsupported link: " + descr);
                        return;
                    }
					activateTreeNode(descr.substring(0, p),
                            descr.substring(p + 1));
				}
			});
		}
		return htmlTextPane;
	}
    
    void activateTreeNode(final String type, final String id) {
        TreeNodeImpl node = null;
        if ("plugin".equalsIgnoreCase(type)) {
            node = findPluginNode(id);
        } else if ("fragment".equalsIgnoreCase(type)) {
            node = findFragmentNode(id);
        } else if ("extp".equalsIgnoreCase(type)) {
            node = findExtPointNode(
                    findPluginNode(registry.extractPluginId(id)),
                    registry.extractId(id));
        } else if ("ext".equalsIgnoreCase(type)) {
            node = findExtNode(findPluginNode(registry.extractPluginId(id)),
                    registry.extractId(id));
        } else {
            JOptionPane.showMessageDialog(this, "Unknown link type [" + type
                    + "] given for manifest element with ID " + id);
            return;
        }
        if (node == null) {
            JOptionPane.showMessageDialog(this,
                    "Unable to find node for link of type [" + type
                    + "] and ID " + id);
            return;
        }
        jTree.setSelectionPath(new TreePath(node.getPath()));
    }
    
    private TreeNodeImpl findPluginNode(final String id) {
        DefaultMutableTreeNode root =
            (DefaultMutableTreeNode) jTree.getModel().getRoot();
        DefaultMutableTreeNode pluginsNode = null;
        for (Enumeration<?> en = root.children(); en.hasMoreElements();) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) en.nextElement();
            if (node.getUserObject().equals("Plug-ins")) {
                pluginsNode = node;
                break;
            }
        }
        if (pluginsNode == null) {
            return null;
        }
        for (Enumeration<?> en = pluginsNode.children();
                en.hasMoreElements();) {
            TreeNodeImpl node = (TreeNodeImpl) en.nextElement();
            if (node.getIdentity().getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
    
    private TreeNodeImpl findFragmentNode(final String id) {
        DefaultMutableTreeNode root =
            (DefaultMutableTreeNode) jTree.getModel().getRoot();
        DefaultMutableTreeNode fragmentsNode = null;
        for (Enumeration<?> en = root.children(); en.hasMoreElements();) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) en.nextElement();
            if (node.getUserObject().equals("Plug-in fragments")) {
                fragmentsNode = node;
                break;
            }
        }
        if (fragmentsNode == null) {
            return null;
        }
        for (Enumeration<?> en = fragmentsNode.children();
                en.hasMoreElements();) {
            TreeNodeImpl node = (TreeNodeImpl) en.nextElement();
            if (node.getIdentity().getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
    
    private TreeNodeImpl findExtPointNode(final TreeNodeImpl pluginNode,
            final String id) {
        if (pluginNode == null) {
            return null;
        }
        DefaultMutableTreeNode extPointsNode = null;
        for (Enumeration<?> en = pluginNode.children();
                en.hasMoreElements();) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) en.nextElement();
            if (node.getUserObject().equals("Extension points")) {
                extPointsNode = node;
                break;
            }
        }
        if (extPointsNode == null) {
            return null;
        }
        for (Enumeration<?> en = extPointsNode.children();
                en.hasMoreElements();) {
            TreeNodeImpl node = (TreeNodeImpl) en.nextElement();
            if (node.getIdentity().getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
    
    private TreeNodeImpl findExtNode(final TreeNodeImpl pluginNode,
            final String id) {
        if (pluginNode == null) {
            return null;
        }
        DefaultMutableTreeNode extsNode = null;
        for (Enumeration<?> en = pluginNode.children(); en.hasMoreElements();) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) en.nextElement();
            if (node.getUserObject().equals("Extensions")) {
                extsNode = node;
                break;
            }
        }
        if (extsNode == null) {
            return null;
        }
        for (Enumeration<?> en = extsNode.children(); en.hasMoreElements();) {
            TreeNodeImpl node = (TreeNodeImpl) en.nextElement();
            if (node.getIdentity().getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
    
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCheckIntegrityButton() {
		if (checkIntegrityButton == null) {
			checkIntegrityButton = new JButton();
            checkIntegrityButton.setIcon(new ImageIcon(
                    getClass().getClassLoader().getResource("check.gif")));
            checkIntegrityButton.setToolTipText("Check plug-ins integrity");
            checkIntegrityButton.addActionListener(new ActionListener() { 
            	public void actionPerformed(final ActionEvent e) {    
            		if (registry == null) {
            		    return;
                    }
                    jTree.clearSelection();
                    Map<String, IntegrityCheckReport> ctx =
                        new HashMap<String, IntegrityCheckReport>();
                    ctx.put("report", registry.checkIntegrity(null));
                    renderTemplate("integrityCheckReport.jxp", ctx);
            	}
            });
		}
		return checkIntegrityButton;
	}
    
    private static class TreeNodeImpl extends DefaultMutableTreeNode {
        private static final long serialVersionUID = -9170454575995595543L;

        private Identity idt;

        TreeNodeImpl(final Identity identity) {
            idt = identity;
        }
        
        /**
         * @see javax.swing.tree.DefaultMutableTreeNode#toString()
         */
        @Override
        public String toString() {
            return idt.getId();
        }
        
        Identity getIdentity() {
            return idt;
        }
    }
    
    private static class RegistryChangeListenerImpl
            implements RegistryChangeListener {
        private Set<String> addedPlugins = new HashSet<String>();
        private Set<String> removedPlugins = new HashSet<String>();
        private Set<String> modifiedPlugins = new HashSet<String>();
        private Set<String> addedExtensions = new HashSet<String>();
        private Set<String> removedExtensions = new HashSet<String>();
        private Set<String> modifiedExtensions = new HashSet<String>();
        
        RegistryChangeListenerImpl() {
            // no-op
        }

        /**
         * @see RegistryChangeListener#registryChanged(
         *      org.java.plugin.registry.PluginRegistry.RegistryChangeData)
         */
        public void registryChanged(final RegistryChangeData data) {
            addedPlugins.addAll(data.addedPlugins());
            removedPlugins.addAll(data.removedPlugins());
            modifiedPlugins.addAll(data.modifiedPlugins());
            addedExtensions.addAll(data.addedExtensions());
            removedExtensions.addAll(data.removedExtensions());
            modifiedExtensions.addAll(data.modifiedExtensions());
        }
        
        Set<String> addedExtensions() {
            return addedExtensions;
        }
        
        Set<String> addedPlugins() {
            return addedPlugins;
        }
        
        Set<String> modifiedExtensions() {
            return modifiedExtensions;
        }
        
        Set<String> modifiedPlugins() {
            return modifiedPlugins;
        }
        
        Set<String> removedExtensions() {
            return removedExtensions;
        }
        
        Set<String> removedPlugins() {
            return removedPlugins;
        }
    }

    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getShowGraphButton() {
        if (showGraphButton == null) {
            showGraphButton = new JButton();
            showGraphButton.setIcon(new ImageIcon(
                    getClass().getClassLoader().getResource("diagram.gif")));
            showGraphButton.setToolTipText("View plug-ins diagram");
            showGraphButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent evt) {
                    if (registry.getPluginDescriptors().isEmpty()) {
                        JOptionPane.showMessageDialog(MainPanel.this,
                                "No plug-ins registered. Register at least one plug-in first.",
                                "Data Missing", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    GraphWindow window = new GraphWindow(
                            (Frame) SwingUtilities.windowForComponent(
                                    MainPanel.this));
                    try {
                        try {
                            window.setRegistry(registry);
                        } catch (Exception e) {
                            ErrorDialog.showError(MainPanel.this, "Error", e);
                            return;
                        }
                        window.setLocationRelativeTo(MainPanel.this);
                        window.setVisible(true);
                    } finally {
                        window.dispose();
                    }
                }
            });
        }
        return showGraphButton;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
