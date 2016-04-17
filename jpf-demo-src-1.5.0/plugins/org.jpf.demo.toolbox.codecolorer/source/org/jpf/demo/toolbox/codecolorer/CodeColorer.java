// Placed in public domain by Dmitry Olshansky, 2006
package org.jpf.demo.toolbox.codecolorer;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import de.java2html.Java2HtmlOptionsPanel;
import de.java2html.converter.JavaSourceConverter;
import de.java2html.gui.GBorderedPanel;
import de.java2html.gui.Gap;
import de.java2html.gui.InfoDialog;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.Java2HtmlConversionOptions;

/**
 * The code is taken from de.java2html.Java2HtmlApplication class with small
 * modifications.
 * @version $Id: CodeColorer.java,v 1.1 2007/03/04 13:00:51 ddimon Exp $
 */
class CodeColorer extends Panel implements ActionListener, ItemListener {
    private static final long serialVersionUID = 6036643890421802368L;

    private List list;

    private Button bAdd;

    private Button bRemove;

    private Button bConvert;

    private Button bClear;

    //private Button bExit;
    private String currentDirectory;

    private Java2HtmlOptionsPanel optionsPanel;

    /**
     * Creates tool's main window as panel.
     */
    public CodeColorer() {
        //super(Version.J2H_TITLE);
        super();
        list = new List(6, true);
        list.addItemListener(this);

        bAdd = new Button("Add");
        bAdd.addActionListener(this);

        bRemove = new Button("Remove");
        bRemove.addActionListener(this);
        bRemove.setEnabled(false);

        bClear = new Button("Clear");
        bClear.addActionListener(this);
        bClear.setEnabled(false);

        bConvert = new Button("Convert");
        bConvert.addActionListener(this);
        bConvert.setEnabled(false);

        Panel pb = new Panel(new GridLayout(0, 1, 2, 2));

        pb.add(bAdd);
        pb.add(bRemove);
        pb.add(bClear);
        pb.add(new Gap());

        GBorderedPanel pc = new GBorderedPanel("Files to convert");
        pc.setLayout(new BorderLayout(4, 4));
        pc.add(list, BorderLayout.CENTER);
        pc.add(pb, BorderLayout.EAST);

        optionsPanel = new Java2HtmlOptionsPanel();
        GBorderedPanel pOptions = new GBorderedPanel("Options");
        pOptions.setLayout(new BorderLayout());
        pOptions.add(optionsPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout(1, 1));
        add(pOptions, BorderLayout.EAST);
        add(pc, BorderLayout.CENTER);
        add(bConvert, BorderLayout.SOUTH);
    }

    private void add() {
        FileDialog fd = new FileDialog(Frame.getFrames()[0],
                "Open Java Source", FileDialog.LOAD);
        fd.setFile("*.java");

        if (currentDirectory != null)
            fd.setDirectory(currentDirectory);

        fd.setVisible(true);

        String filename = fd.getFile();
        String directory = fd.getDirectory();

        if (filename == null)
            return;

        //Remember current directory
        currentDirectory = directory;
        list.add(directory + filename);
        bClear.setEnabled(true);
        bConvert.setEnabled(true);
    }

    private void remove() {
        String[] o = list.getSelectedItems();
        for (int i = 0; i < o.length; ++i)
            list.remove(o[i]);

        bRemove.setEnabled(false);
        bClear.setEnabled(list.getItemCount() > 0);
        bConvert.setEnabled(list.getItemCount() > 0);
    }

    private void clear() {
        list.removeAll();

        bRemove.setEnabled(false);
        bClear.setEnabled(false);
        bConvert.setEnabled(false);
    }

    private void convert() {
        Java2HtmlConversionOptions options = optionsPanel
                .getConversionOptions();
        options.setShowJava2HtmlLink(true);

        //Collect statistical information
        StringBuffer report = new StringBuffer();

        //Collect conversion-results
        StringWriter writer = new StringWriter();

        //Create the converter
        JavaSourceConverter converter = optionsPanel.getConverter();
        converter.setConversionOptions(options);

        writer.write(converter.getDocumentHeader());

        String[] fileNames = list.getItems();
        for (int count = 0; count < fileNames.length; ++count) {
            report.append("File " + (count + 1) + ": " + fileNames[count]
                    + "\n");

            if (count > 0) {
                writer.write(converter.getBlockSeparator());
            }

            try {
                JavaSourceParser parser = new JavaSourceParser(options);
                JavaSource source = parser.parse(new File(fileNames[count]));
                converter.convert(source, writer);

                writer.write('\n');

                report.append(source.getStatisticsString() + "\n");
            } catch (IOException e) {
                System.err.println("Error converting " + fileNames[count]
                        + ": " + e);
            }
        }

        writer.write(converter.getDocumentFooter());

        //Copy result to system clipboard
        StringSelection sel = new StringSelection(writer.getBuffer().toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(sel, sel);

        report
                .append("\n - The converted source code has been copied the system clipboard - ");

        String plural = "";
        if (fileNames.length > 1)
            plural = "s";

        //Show success-dialog
        new InfoDialog(Frame.getFrames()[0], "File" + plural + " converted",
                fileNames.length + " File" + plural
                        + " successfully converted to the system clipboard.",
                report.toString()).setVisible(true);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == bAdd) {
            add();
        } else if (source == bRemove) {
            remove();
        } else if (source == bClear) {
            clear();
        } else {
            convert();
        }
    }

    /**
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent evt) {
        bRemove.setEnabled(list.getSelectedIndexes().length > 0);
    }
}