// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.core;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.java.plugin.boot.Application;
import org.java.plugin.boot.ApplicationPlugin;
import org.java.plugin.boot.Boot;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.java.plugin.registry.PluginDescriptor;
import org.java.plugin.registry.Extension.Parameter;
import org.java.plugin.util.ExtendedProperties;


/**
 *
 * @version $Id: CorePlugin.java,v 1.1 2007/03/04 13:00:56 ddimon Exp $
 */
public final class CorePlugin extends ApplicationPlugin implements Application {
    /**
     * This plug-in ID.
     */
    public static final String PLUGIN_ID = "org.jpf.demo.toolbox.core";
    
    private File dataFolder;
    private JFrame frame;
    private JTabbedPane tabbedPane;
    
    /**
     * Returns folder where given plug-in can store it's data.
     * @param descr plug-in descriptor
     * @return plug-in data folder
     * @throws IOException if folder doesn't exist and can't be created
     */
    public File getDataFolder(final PluginDescriptor descr) throws IOException {
        File result = new File(dataFolder, descr.getId());
        if (!result.isDirectory() && !result.mkdirs()) {
            throw new IOException("can't create data folder " + result
                    + " for plug-in " + descr.getId());
        }
        return result;
    }

    /**
     * @see org.java.plugin.Plugin#doStart()
     */
    @Override
    protected void doStart() throws Exception {
        // no-op
    }

    /**
     * @see org.java.plugin.Plugin#doStop()
     */
    @Override
    protected void doStop() throws Exception {
        // no-op
    }

    /**
     * @see org.java.plugin.boot.ApplicationPlugin#initApplication(
     *      ExtendedProperties, String[])
     */
    @Override
    protected Application initApplication(final ExtendedProperties config,
            final String[] args) throws Exception {
        dataFolder = new File(config.getProperty("dataFolder",
                "." + File.separator + "data"));
        dataFolder = dataFolder.getCanonicalFile();
        log.debug("data folder - " + dataFolder);
        if (!dataFolder.isDirectory() && !dataFolder.mkdirs()) {
            throw new Exception("data folder " + dataFolder + " not found");
        }
        return this;
    }

    /**
     * @see org.java.plugin.boot.Application#startApplication()
     */
    public void startApplication() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    protected void createAndShowGUI() {
        frame = new JFrame("Tool Box");
        tabbedPane = new JTabbedPane(SwingConstants.TOP);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass()
                        .getResource("app.gif")).getImage());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                try {
                    saveState();
                    JOptionPane.getRootFrame().dispose();
                    Boot.stopApplication(CorePlugin.this);
                } catch (Exception ex) {
                    // ignore
                }
                System.exit(0);
            }
        });
        ExtensionPoint toolExtPoint =
            getManager().getRegistry().getExtensionPoint(
                    getDescriptor().getId(), "Tool");
        for (Extension ext : toolExtPoint.getConnectedExtensions()) {
            JPanel panel = new JPanel();
            panel.putClientProperty("extension", ext);
            Parameter descrParam = ext.getParameter("description");
            Parameter iconParam = ext.getParameter("icon");
            URL iconUrl = null;
            if (iconParam != null) {
                iconUrl = getManager().getPluginClassLoader(
                        ext.getDeclaringPluginDescriptor())
                            .getResource(iconParam.valueAsString());
            }
            tabbedPane.addTab(
                    ext.getParameter("name").valueAsString(),
                    (iconUrl != null) ? new ImageIcon(iconUrl) : null,
                    panel, (descrParam != null) ? descrParam.valueAsString()
                            : "");
        }
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent e) {
                JTabbedPane theTabbedPane = (JTabbedPane) e.getSource();
                JComponent toolComponent =
                    (JComponent) theTabbedPane.getComponent(
                        theTabbedPane.getSelectedIndex());
                activateTool(toolComponent);
            }
        });
        readState();
        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
    
    /*
     * This method activates (initializes Tool) if it wasn't activated yet.
     */
    protected void activateTool(final JComponent toolComponent) {
        Extension ext =
            (Extension) toolComponent.getClientProperty("extension");
        frame.setTitle("[Tool Box] - "
                + ext.getParameter("name").valueAsString());
        Tool tool = (Tool) toolComponent.getClientProperty("toolInstance");
        if (tool == null) {
            try {
                // Activate plug-in that declares extension.
                getManager().activatePlugin(
                        ext.getDeclaringPluginDescriptor().getId());
                // Get plug-in class loader.
                ClassLoader classLoader = getManager().getPluginClassLoader(
                        ext.getDeclaringPluginDescriptor());
                // Load Tool class.
                Class<?> toolCls = classLoader.loadClass(
                        ext.getParameter("class").valueAsString());
                // Create Tool instance.
                tool = (Tool) toolCls.newInstance();
                // Initialize class instance according to interface contract.
                tool.init(toolComponent);
            } catch (Throwable t) {
                toolComponent.setLayout(new BorderLayout());
                toolComponent.add(new JLabel(t.toString()), BorderLayout.NORTH);
                JScrollPane scrollPane = new JScrollPane();
                toolComponent.add(scrollPane, BorderLayout.CENTER);
                StringBuffer sb = new StringBuffer();
                String nl = System.getProperty("line.separator");
                Throwable err = t;
                while (err != null) {
                    if (err != t) {
                        sb.append(nl).append("Caused by " + err).append(nl).append(nl);
                    }
                    StackTraceElement[] stackTrace = err.getStackTrace();
                    for (int i = 0; i < stackTrace.length; i++) {
                        sb.append(stackTrace[i].toString()).append(nl);
                    }
                    err = err.getCause();
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setBackground(java.awt.SystemColor.control);
                textArea.setEditable(false);
                scrollPane.setViewportView(textArea);
                textArea.setCaretPosition(0);
                return;
            }
            toolComponent.putClientProperty("toolInstance", tool);
        }
    }
    
    private File getConfigFile() throws IOException {
        File result = new File(getDataFolder(getDescriptor()),
                "config.properties");
        if (!result.exists() && !result.createNewFile()) {
            throw new IOException("can't create configuration file " + result);
        }
        return result;
    }
    
    protected void saveState() {
        Properties props = new Properties();
        props.setProperty("window.X", "" + frame.getX());
        props.setProperty("window.Y", "" + frame.getY());
        props.setProperty("window.width", "" + frame.getWidth());
        props.setProperty("window.height", "" + frame.getHeight());
        JComponent toolComponent = (JComponent) tabbedPane.getComponent(
                tabbedPane.getSelectedIndex());
        Extension ext =
            (Extension) toolComponent.getClientProperty("extension");
        props.setProperty("window.activeTool", ext.getUniqueId());
        try {
            OutputStream strm = new FileOutputStream(getConfigFile(), false);
            try {
                props.store(strm,
                        "This is automatically generated configuration file.");
            } finally {
                strm.close();
            }
        } catch (Exception e) {
            log.error("can't save program state", e);
        }
    }
    
    private void readState() {
        Properties props = new Properties();
        try {
            InputStream strm = new FileInputStream(getConfigFile());
            try {
                props.load(strm);
            } finally {
                strm.close();
            }
        } catch (Exception e) {
            log.error("can't load program state", e);
        }
        frame.setBounds(
                Integer.parseInt(props.getProperty("window.X", "10"), 10),
                Integer.parseInt(props.getProperty("window.Y", "10"), 10),
                Integer.parseInt(props.getProperty("window.width", "600"), 10),
                Integer.parseInt(props.getProperty("window.height", "500"), 10));
        String activeTool = props.getProperty("window.activeTool");
        if (activeTool == null) {
            activateTool((JComponent) tabbedPane.getComponent(
                    tabbedPane.getSelectedIndex()));
        } else {
            boolean activated = false;
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                JComponent toolComponent =
                    (JComponent) tabbedPane.getComponent(i);
                Extension ext =
                    (Extension) toolComponent.getClientProperty("extension");
                if (activeTool.equals(ext.getUniqueId())) {
                    activateTool(toolComponent);
                    tabbedPane.setSelectedIndex(i);
                    activated = true;
                    break;
                }
            }
            if (!activated) {
                activateTool((JComponent) tabbedPane.getComponent(
                        tabbedPane.getSelectedIndex()));
            }
        }
    }
}
