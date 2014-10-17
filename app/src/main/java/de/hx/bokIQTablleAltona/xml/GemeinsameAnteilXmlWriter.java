package de.hx.bokIQTablleAltona.xml;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by hoang on 27.08.14.
 */
public class GemeinsameAnteilXmlWriter {


    public void writeFleischAnteil(Map<String, String> fleischAnteilMap) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document doc = getXMLDocumentFleisch();
        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            Element artikelNameElement = (Element) item.getElementsByTagName("artikelName").item(0);
            String artikelName = artikelNameElement.getTextContent();
            String anteil = fleischAnteilMap.containsKey(artikelName) ? fleischAnteilMap.get(artikelName) : "0";
            Element anteilElement = (Element) item.getElementsByTagName("anteil").item(0);
            if(anteilElement!=null) {
                anteilElement.getParentNode().removeChild(anteilElement);
            }
            Node anteilNode = doc.createElement("anteil");
            anteilNode.setTextContent(anteil);
            item.appendChild(anteilNode);
        }
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/fleisch.xml");
        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
    }

    public void writeGetraenkeAnteil(Map<String, String> getraenkeAnteilMap) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document doc = getXMLDocumentGetraenke();
        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            Element artikelNameElement = (Element) item.getElementsByTagName("artikelName").item(0);
            String artikelName = artikelNameElement.getTextContent();
            String anteil = getraenkeAnteilMap.containsKey(artikelName) ? getraenkeAnteilMap.get(artikelName) : "0";
            Element anteilElement = (Element) item.getElementsByTagName("anteil").item(0);
            if(anteilElement!=null) {
                anteilElement.getParentNode().removeChild(anteilElement);
            }
            Node anteilNode = doc.createElement("anteil");
            anteilNode.setTextContent(anteil);
            item.appendChild(anteilNode);
        }
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke.xml");
        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
    }

    Document getXMLDocumentFleisch() throws ParserConfigurationException,
            SAXException, IOException {
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/fleisch.xml");
        if (!xmlFile.exists())
            return null;
        FileInputStream fis = new FileInputStream(xmlFile);
        DocumentBuilderFactory dFactory = null;
        DocumentBuilder builder = null;
        Document doc = null;
        dFactory = DocumentBuilderFactory.newInstance();
        builder = dFactory.newDocumentBuilder();
        doc = builder.parse(new InputSource(fis));
        return doc;
    }

    Document getXMLDocumentGetraenke() throws ParserConfigurationException,
            SAXException, IOException {
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke.xml");
        if (!xmlFile.exists())
            return null;
        FileInputStream fis = new FileInputStream(xmlFile);
        DocumentBuilderFactory dFactory = null;
        DocumentBuilder builder = null;
        Document doc = null;
        dFactory = DocumentBuilderFactory.newInstance();
        builder = dFactory.newDocumentBuilder();
        doc = builder.parse(new InputSource(fis));
        return doc;
    }
}
