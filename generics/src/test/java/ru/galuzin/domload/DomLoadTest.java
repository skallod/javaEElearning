package ru.galuzin.domload;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by galuzin on 23.01.2017.
 */
public class DomLoadTest extends TestCase{
    private static final String xpathFilter = "//response";

    @Test
    public void test1(){
        try {
            //InputStream input = DomLoadTest.class.getResource("submitXmlOnSession1.xml").openStream();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("submitXmlOnSession1.xml").getFile());
            //File file = ResourceUtils.getFile("classpath:file/test.txt")
            FileInputStream input = new FileInputStream(file);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
//            NodeList element = doc.getChildNodes();
//            for(int i = 0; i<element.getLength();i++){
//                Node node = element.item(i);
//                System.out.println("node = " + node);
//            }
//            Assert.assertNotNull(element);
//            System.out.println("element = " + element);
            Element element1 = doc.getDocumentElement();
            System.out.println("element1 = " + element1);
            String err =
                    getValue(element1, "DocProdDisplayStoredQuote/ErrText/Text");
            System.out.println("err = " + err);
            List<List<Element>> items = new LinkedList<List<Element>>();
            List<Element> item = new LinkedList<Element>();
            for (Element el : getElements(element1,
                    "DocProdDisplayStoredQuote/*")) {
                if ("FareNumInfo".equals(el.getTagName())) {
                    if (!item.isEmpty()) {
                        items.add(new LinkedList<Element>(item));
                    }
                    item.clear();
                }
                item.add(el);
            }

            Element[] programaticSSRs =
                    getElementsStartsWith(element1, "PNRBFRetrieve/ProgramaticSSR");
            for (int i = 0; i < programaticSSRs.length; i++) {
                String psgrNumStr = getValue(programaticSSRs[i],
                        "AppliesToAry/AppliesTo/AbsNameNum");
                String segNumStr = getValue(programaticSSRs[i], "SegNum");
                try {
                    if (!isBlank(psgrNumStr)
                            && getValue(programaticSSRs[i], "SSRCode")
                            .startsWith("TKN")) {
                        int additionalElIdx = Integer.parseInt(psgrNumStr);
//                        if ((additionalElIdx == absPassengerIndex)
//                                && ((segNums.isEmpty())
//                                || (segNums.contains(segNumStr)))) {
                            String tkNum =
                                    getValue(programaticSSRs[i + 1], "Text");
                            System.out.println("tkNum = " + tkNum);
//                        }
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static String getValue(final Element elm, final String path) {
        int p = path.indexOf('@');
        if (p == 0) {
            Attr attr = elm.getAttributeNode(path.substring(1));
            return (attr == null) ? null : attr.getNodeValue();
        }
        String elmPath = (p == -1) ? path : path.substring(0, p);
        String attrName = (p == -1) ? null : path.substring(p + 1);
        Element subElm = getElement(elm, elmPath);
        if (subElm == null) {
            return null;
        }
        if (attrName != null) {
            Attr attr = subElm.getAttributeNode(attrName);
            return (attr == null) ? null : attr.getNodeValue();
        }
        return getValue(subElm);
    }

    public static Element getElement(final Element elm, final String path) {
        if (elm == null) {
            throw new IllegalArgumentException("element is NULL"); //$NON-NLS-1$
        }
        if ((path == null) || "".equals(path) //$NON-NLS-1$
                || path.endsWith("/") || (path.indexOf('@') != -1)) { //$NON-NLS-1$
            throw new IllegalArgumentException("invalid path " + path); //$NON-NLS-1$
        }
        if (".".equals(path)) { //$NON-NLS-1$
            return elm;
        }
        if (path.startsWith("/")) { //$NON-NLS-1$
            Element root = elm.getOwnerDocument().getDocumentElement();
            String subPath = path.substring(1);
            int p = subPath.indexOf('/');
            if (p == -1) {
                if (root.getTagName().equals(subPath)) {
                    return root;
                }
                return null;
            }
            if (root.getTagName().equals(subPath.substring(0, p))) {
                return getElement(root, subPath.substring(p + 1));
            }
            return null;
        }
        if (path.startsWith("./")) { //$NON-NLS-1$
            return getElement(elm, path.substring(2));
        }
        StringTokenizer st = new StringTokenizer(path, "/", false); //$NON-NLS-1$
        Element currElm = elm;
        while (true) {
            Element nextElm = findChild(currElm, st.nextToken());
            if (nextElm == null) {
                return null;
            }
            if (!st.hasMoreTokens()) {
                return nextElm;
            }
            currElm = nextElm;
        }
    }
    private static Element findChild(final Element elm, final String tagName) {
        NodeList children = elm.getChildNodes();
        int c = children.getLength();
        for (int i = 0; i < c; i++) {
            Node child = safeGetNode(children, i);
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (tagName.equals(child.getNodeName())) {
                return (Element) child;
            }
        }
        return null;
    }

    public static Node safeGetNode(final NodeList nodeList, final int i) {
        if ((i < 0) || (i >= nodeList.getLength())) {
            return null;
        }
        //NB: official java bug workaround (http://bugs.sun.com/view_bug.do?bug_id=6333993)
        Node n = nodeList.item(i);
        if (n == null) {
            n = nodeList.item(i);
        }
        return n;
    }
    public static String getValue(final Element elm) {
        return getValue(elm, false);
    }

    public static String getValue(final Element elm, final boolean recursive) {
        if (elm == null) {
            throw new IllegalArgumentException("element is NULL"); //$NON-NLS-1$
        }
        NodeList children = elm.getChildNodes();
        int c = children.getLength();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < c; i++) {
            Node child = safeGetNode(children, i);
            switch (child.getNodeType()) {
                case Node.CDATA_SECTION_NODE:
                    result.append(child.getNodeValue());
                    break;
                case Node.TEXT_NODE:
                    result.append(child.getNodeValue().trim());
                    break;
                case Node.ELEMENT_NODE:
                    if (recursive) {
                        result.append(getValue((Element) child, recursive));
                    }
                    break;
            }
        }
        return result.toString();
    }

    public static Element[] getElements(final Element elm, final String path) {
        if (elm == null) {
            throw new IllegalArgumentException("element is NULL"); //$NON-NLS-1$
        }
        if ((path == null) || "".equals(path) //$NON-NLS-1$
                || ".".equals(path) //$NON-NLS-1$
                || path.endsWith("/") || (path.indexOf('@') != -1)) { //$NON-NLS-1$
            throw new IllegalArgumentException("invalid path " + path); //$NON-NLS-1$
        }
        if (path.startsWith("/")) { //$NON-NLS-1$
            Element root = elm.getOwnerDocument().getDocumentElement();
            String subPath = path.substring(1);
            int p = subPath.indexOf('/');
            if (p == -1) {
                if (root.getTagName().equals(subPath)) {
                    return new Element[] { root };
                }
                return new Element[0];
            }
            if (root.getTagName().equals(subPath.substring(0, p))) {
                return getElements(root, subPath.substring(p + 1));
            }
            return new Element[0];
        }
        if (path.startsWith("./")) { //$NON-NLS-1$
            return getElements(elm, path.substring(2));
        }
        List<Element> result = new LinkedList<Element>();
        int p = path.indexOf('/');
        if (p == -1) {
            NodeList children = elm.getChildNodes();
            int c = children.getLength();
            for (int i = 0; i < c; i++) {
                Node child = safeGetNode(children, i);
                if (child.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if ("*".equals(path) || path.equals(child.getNodeName())) { //$NON-NLS-1$
                    result.add((Element) child);
                }
            }
        } else {
            Element[] elms = getElements(elm, path.substring(0, p));
            for (Element elm2 : elms) {
                Element[] subElms = getElements(elm2, path.substring(p + 1));
                for (Element subElm : subElms) {
                    result.add(subElm);
                }
            }
        }
        return result.toArray(new Element[result.size()]);
    }
    public static boolean isBlank(final String str) {
        return (str == null) || (str.trim().length() == 0);
    }
    public static Element[] getElementsStartsWith(final Element elm,
                                                  final String path) {
        if (elm == null) {
            throw new IllegalArgumentException("element is NULL"); //$NON-NLS-1$
        }
        if ((path == null) || "".equals(path) //$NON-NLS-1$
                || ".".equals(path) //$NON-NLS-1$
                || path.endsWith("/") || (path.indexOf('@') != -1)) { //$NON-NLS-1$
            throw new IllegalArgumentException("invalid path " + path); //$NON-NLS-1$
        }
        if (path.startsWith("/")) { //$NON-NLS-1$
            Element root = elm.getOwnerDocument().getDocumentElement();
            String subPath = path.substring(1);
            int p = subPath.indexOf('/');
            if (p == -1) {
                if (root.getTagName().equals(subPath)) {
                    return new Element[] { root };
                }
                return new Element[0];
            }
            if (root.getTagName().equals(subPath.substring(0, p))) {
                return getElementsStartsWith(root, subPath.substring(p + 1));
            }
            return new Element[0];
        }
        if (path.startsWith("./")) { //$NON-NLS-1$
            return getElementsStartsWith(elm, path.substring(2));
        }
        List<Element> result = new LinkedList<Element>();
        int p = path.indexOf('/');
        if (p == -1) {
            NodeList children = elm.getChildNodes();
            int c = children.getLength();
            for (int i = 0; i < c; i++) {
                Node child = safeGetNode(children, i);
                if (child.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if ("*".equals(path) || child.getNodeName().startsWith(path)) { //$NON-NLS-1$
                    result.add((Element) child);
                }
            }
        } else {
            Element[] elms = getElementsStartsWith(elm, path.substring(0, p));
            for (Element elm2 : elms) {
                Element[] subElms =
                        getElementsStartsWith(elm2, path.substring(p + 1));
                for (Element subElm : subElms) {
                    result.add(subElm);
                }
            }
        }
        return result.toArray(new Element[result.size()]);
    }
}
