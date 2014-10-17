package de.hx.bokIQTablleAltona.xml.getraenke;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.Environment;

public class GetraenkeEinkaufPreisXmlWriter {

    public GetraenkeEinkaufPreisXmlWriter() {
    }

    ;

    // Dung de viet du lieu Einkaufspreis theo dung thang vao XML
    public void writeGetraenkeEinkaufpreisXml(
            Map<String, Double> getraenkeEinkaufPreisMap, int month, int year)
            throws ParserConfigurationException, SAXException, IOException,
            TransformerFactoryConfigurationError, TransformerException {

        Map<String, List<Double>> allItems = removeExistingEinkaufspreis(getraenkeEinkaufPreisMap, month, year);
        Document doc = getXMLDocument();
        if (allItems != null) {
            Node fbNode = doc.getFirstChild();
            Node neueMonatEinkauf = doc.createElement("monatEinkaufspreis");
            fbNode.appendChild(neueMonatEinkauf);

            Node monatNode = doc.createElement("monat");
            monatNode.setTextContent(String.valueOf(month));
            neueMonatEinkauf.appendChild(monatNode);

            Node jahrNode = doc.createElement("jahr");
            jahrNode.setTextContent(String.valueOf(year));
            neueMonatEinkauf.appendChild(jahrNode);

            for (String key : allItems.keySet()) {
                Node itemNode = doc.createElement("item");
                Node artikelNameNode = doc.createElement("artikelName");
                artikelNameNode.setTextContent(key);
                itemNode.appendChild(artikelNameNode);
                ArrayList<Double> einkaufspreisList = new ArrayList<Double>(
                        allItems.get(key));
                for (Double einkaufspreis : einkaufspreisList) {
                    Node einkaufspreisNode = doc.createElement("einkaufspreis");
                    einkaufspreisNode.setTextContent(einkaufspreis.toString());
                    itemNode.appendChild(einkaufspreisNode);
                }
                neueMonatEinkauf.appendChild(itemNode);
            }
        } else {
            Node fbNode = doc.getFirstChild();
            Node thisMonatEinkauf = doc.createElement("monatEinkaufspreis");
            fbNode.appendChild(thisMonatEinkauf);

            Node monatNode = doc.createElement("monat");
            monatNode.setTextContent(String.valueOf(month));
            thisMonatEinkauf.appendChild(monatNode);

            Node jahrNode = doc.createElement("jahr");
            jahrNode.setTextContent(String.valueOf(year));
            thisMonatEinkauf.appendChild(jahrNode);

            for (String key : getraenkeEinkaufPreisMap.keySet()) {
                Node itemNode = doc.createElement("item");
                Node artikelNameNode = doc.createElement("artikelName");
                artikelNameNode.setTextContent(key);
                itemNode.appendChild(artikelNameNode);
                Node einkaufspreisNode = doc.createElement("einkaufspreis");
                einkaufspreisNode.setTextContent(String
                        .valueOf(getraenkeEinkaufPreisMap.get(key)));
                itemNode.appendChild(einkaufspreisNode);
                thisMonatEinkauf.appendChild(itemNode);
            }
        }
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_einkauf_sammlung.xml");
        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
    }

    Element getExistedMonatEinkaufElement(int month, int year)
            throws ParserConfigurationException, SAXException, IOException {
        Document doc = getXMLDocument();
        NodeList nodes = doc.getElementsByTagName("monatEinkaufspreis");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisMonatEinkaufElement = (Element) nodes.item(i);
            Element thisYearMonatEinkaufElement = (Element) thisMonatEinkaufElement
                    .getElementsByTagName("jahr").item(0);
            int thisJahrMonatEinkauf = Integer
                    .valueOf(thisYearMonatEinkaufElement.getTextContent());
            Element thisMonatMonatEinkaufElement = (Element) thisMonatEinkaufElement
                    .getElementsByTagName("monat").item(0);
            int thisMonatMonatEinkauf = Integer
                    .valueOf(thisMonatMonatEinkaufElement.getTextContent());
            if (thisMonatMonatEinkauf == month && thisJahrMonatEinkauf == year) {
                return thisMonatEinkaufElement;
            }
        }
        return null;
    }

    Document getXMLDocument() throws ParserConfigurationException,
            SAXException, IOException {
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_einkauf_sammlung.xml");
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

    Map<String, List<Double>> removeExistingEinkaufspreis(
            Map<String, Double> getraenkeEinkaufPreisMap, int month, int year)
            throws ParserConfigurationException, SAXException, IOException,
            TransformerFactoryConfigurationError, TransformerException {

        Document doc = getXMLDocument();
        Map<String, List<Double>> allItems = null;
        NodeList nodes = doc.getElementsByTagName("monatEinkaufspreis");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisMonatEinkaufElement = (Element) nodes.item(i);
            Element thisYearMonatEinkaufElement = (Element) thisMonatEinkaufElement
                    .getElementsByTagName("jahr").item(0);
            int thisJahrMonatEinkauf = Integer
                    .valueOf(thisYearMonatEinkaufElement.getTextContent());
            Element thisMonatMonatEinkaufElement = (Element) thisMonatEinkaufElement
                    .getElementsByTagName("monat").item(0);
            int thisMonatMonatEinkauf = Integer
                    .valueOf(thisMonatMonatEinkaufElement.getTextContent());
            if (thisMonatMonatEinkauf == month && thisJahrMonatEinkauf == year) {
                allItems = new HashMap<String, List<Double>>();
                NodeList items = thisMonatEinkaufElement
                        .getElementsByTagName("item");
                for (int k = 0; k < items.getLength(); k++) {
                    ArrayList<Double> thisItemsEinkaufspreis = new ArrayList<Double>();
                    Element thisItem = (Element) items.item(k);
                    Element thisArtikelNameElement = (Element) thisItem
                            .getElementsByTagName("artikelName").item(0);
                    String thisArtikelName = thisArtikelNameElement
                            .getTextContent();
                    Double neueEinkaufspreis = getraenkeEinkaufPreisMap
                            .remove(thisArtikelName);
                    if (neueEinkaufspreis != null) {
                        thisItemsEinkaufspreis.add(neueEinkaufspreis);
                    }
                    NodeList thisItemEinkaufspreisElements = thisItem
                            .getElementsByTagName("einkaufspreis");
                    for (int j = 0; j < thisItemEinkaufspreisElements
                            .getLength(); j++) {
                        Element thisItemEinkaufspreisElement = (Element) thisItemEinkaufspreisElements
                                .item(j);
                        Double thisEinkaufspreis = Double
                                .valueOf(thisItemEinkaufspreisElement
                                        .getTextContent());
                        thisItemsEinkaufspreis.add(thisEinkaufspreis);
                    }
                    allItems.put(thisArtikelName, thisItemsEinkaufspreis);
                }
                for (String key : getraenkeEinkaufPreisMap.keySet()) {
                    ArrayList<Double> thisEinkaufspreisList = new ArrayList<Double>();
                    thisEinkaufspreisList.add(getraenkeEinkaufPreisMap.get(key));
                    allItems.put(key, thisEinkaufspreisList);
                }
                thisMonatEinkaufElement.getParentNode().removeChild(
                        thisMonatEinkaufElement);
                File xmlFile = new File(Environment.getExternalStorageDirectory()
                        + "/BOK/Altona/getraenke_einkauf_sammlung.xml");
                Transformer transformer = TransformerFactory.newInstance()
                        .newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                StreamResult result = new StreamResult(xmlFile);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                break;
            }
        }
        return allItems;
    }
}
