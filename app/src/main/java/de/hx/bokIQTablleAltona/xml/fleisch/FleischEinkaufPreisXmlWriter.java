package de.hx.bokIQTablleAltona.xml.fleisch;

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

public class FleischEinkaufPreisXmlWriter {

    public FleischEinkaufPreisXmlWriter() {
    }

    ;

    // Dung de viet du lieu Einkaufspreis theo dung thang vao XML
    public void writeFleischEinkaufpreisXml(
            Map<String, Double> fleischEinkaufPreisMap, int month, int year)
            throws ParserConfigurationException, SAXException, IOException,
            TransformerFactoryConfigurationError, TransformerException {

        Map<String, List<Double>> allItems = removeExistingEinkaufspreis(fleischEinkaufPreisMap, month, year);
        Document doc = getXMLDocument();
        if (allItems != null) {
//			Map<String, List<Double>> allItems = new HashMap<String, List<Double>>();
//			NodeList items = zufindendeMonatEinkauf
//					.getElementsByTagName("item");
//			for (int i = 0; i < items.getLength(); i++) {
//				ArrayList<Double> thisItemsEinkaufspreis = new ArrayList<Double>();
//				Element thisItem = (Element) items.item(i);
//				Element thisArtikelNameElement = (Element) thisItem
//						.getElementsByTagName("artikelName").item(0);
//				String thisArtikelName = thisArtikelNameElement
//						.getTextContent();
//				Double neueEinkaufspreis = fleischEinkaufPreisMap
//						.remove(thisArtikelName);
//				if (neueEinkaufspreis != null) {
//					thisItemsEinkaufspreis.add(neueEinkaufspreis);
//				}
//				NodeList thisItemEinkaufspreisElements = thisItem
//						.getElementsByTagName("einkaufspreis");
//				for (int j = 0; j < thisItemEinkaufspreisElements.getLength(); j++) {
//					Element thisItemEinkaufspreisElement = (Element) thisItemEinkaufspreisElements
//							.item(j);
//					Double thisEinkaufspreis = Double
//							.valueOf(thisItemEinkaufspreisElement
//									.getTextContent());
//					thisItemsEinkaufspreis.add(thisEinkaufspreis);
//				}
//				allItems.put(thisArtikelName, thisItemsEinkaufspreis);
//			}
//			for (String key : fleischEinkaufPreisMap.keySet()) {
//				ArrayList<Double> thisEinkaufspreisList = new ArrayList<Double>();
//				thisEinkaufspreisList.add(fleischEinkaufPreisMap.get(key));
//				allItems.put(key, thisEinkaufspreisList);
//			}
//			zufindendeMonatEinkauf.getParentNode().removeChild(
//					zufindendeMonatEinkauf);
            Node fbNode = doc.getFirstChild();
            Node neueMonatEinkauf = doc.createElement("monatEinkaufspreis");
            fbNode.appendChild(neueMonatEinkauf);

            // Node monatEinkaufID = doc.createElement("monatEinkaufspreisID");
            // monatEinkaufID.setTextContent(UUID.randomUUID().toString());
            // neueMonatEinkauf.appendChild(monatEinkaufID);

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

            // Node monatEinkaufID = doc.createElement("monatEinkaufspreisID");
            // monatEinkaufID.setTextContent(UUID.randomUUID().toString());
            // thisMonatEinkauf.appendChild(monatEinkaufID);

            Node monatNode = doc.createElement("monat");
            monatNode.setTextContent(String.valueOf(month));
            thisMonatEinkauf.appendChild(monatNode);

            Node jahrNode = doc.createElement("jahr");
            jahrNode.setTextContent(String.valueOf(year));
            thisMonatEinkauf.appendChild(jahrNode);

            for (String key : fleischEinkaufPreisMap.keySet()) {
                Node itemNode = doc.createElement("item");
                Node artikelNameNode = doc.createElement("artikelName");
                artikelNameNode.setTextContent(key);
                itemNode.appendChild(artikelNameNode);
                Node einkaufspreisNode = doc.createElement("einkaufspreis");
                einkaufspreisNode.setTextContent(String
                        .valueOf(fleischEinkaufPreisMap.get(key)));
                itemNode.appendChild(einkaufspreisNode);
                thisMonatEinkauf.appendChild(itemNode);
            }
        }
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/fleisch_einkauf_sammlung.xml");
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
                + "/BOK/Altona/fleisch_einkauf_sammlung.xml");
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
            Map<String, Double> fleischEinkaufPreisMap, int month, int year)
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
                    Double neueEinkaufspreis = fleischEinkaufPreisMap
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
                for (String key : fleischEinkaufPreisMap.keySet()) {
                    ArrayList<Double> thisEinkaufspreisList = new ArrayList<Double>();
                    thisEinkaufspreisList.add(fleischEinkaufPreisMap.get(key));
                    allItems.put(key, thisEinkaufspreisList);
                }
                thisMonatEinkaufElement.getParentNode().removeChild(
                        thisMonatEinkaufElement);
                File xmlFile = new File(Environment.getExternalStorageDirectory()
                        + "/BOK/Altona/fleisch_einkauf_sammlung.xml");
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
