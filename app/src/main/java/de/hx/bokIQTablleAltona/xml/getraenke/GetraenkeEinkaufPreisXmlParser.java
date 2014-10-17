package de.hx.bokIQTablleAltona.xml.getraenke;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.Environment;

public class GetraenkeEinkaufPreisXmlParser {

    public Map<String, Double> getMonatEinkaufspreisMap(int selectedMonat,
                                                        int selectedJahr) throws ParserConfigurationException,
            SAXException, IOException {

        Map<String, Double> result = null;
        Document doc = getXMLDocument();
        Element zufindendeMonatEinkaufspreis = null;
        NodeList nodes = doc.getElementsByTagName("monatEinkaufspreis");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisMonatEinkaufspreisElement = (Element) nodes.item(i);
            Element thisMonatEinkaufspreisMonatElement = (Element) thisMonatEinkaufspreisElement
                    .getElementsByTagName("monat").item(0);
            int thisMonat = Integer.valueOf(thisMonatEinkaufspreisMonatElement
                    .getTextContent());
            Element thisMonatEinkaufspreisJahrElement = (Element) thisMonatEinkaufspreisElement
                    .getElementsByTagName("jahr").item(0);
            int thisJahr = Integer.valueOf(thisMonatEinkaufspreisJahrElement
                    .getTextContent());
            if (thisMonat == selectedMonat && thisJahr == selectedJahr) {
                zufindendeMonatEinkaufspreis = thisMonatEinkaufspreisElement;
                break;
            }
        }
        if (zufindendeMonatEinkaufspreis != null) {
            result = new HashMap<String, Double>();
            NodeList items = zufindendeMonatEinkaufspreis
                    .getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element thisItem = (Element) items.item(i);
                Element thisArtikelNameElement = (Element) thisItem
                        .getElementsByTagName("artikelName").item(0);
                String thisArtikelName = thisArtikelNameElement
                        .getTextContent();
                NodeList thisEinkaufspreisElements = thisItem
                        .getElementsByTagName("einkaufspreis");
                double thisMittelwertEinkaufspreis = 0;
                for (int j = 0; j < thisEinkaufspreisElements.getLength(); j++) {
                    Element thisEinkaufspreisElement = (Element) thisEinkaufspreisElements
                            .item(j);
                    double thisEinkaufspreis = Double
                            .valueOf(thisEinkaufspreisElement.getTextContent());
                    thisMittelwertEinkaufspreis += thisEinkaufspreis;
                }
                thisMittelwertEinkaufspreis = thisMittelwertEinkaufspreis
                        / (thisEinkaufspreisElements.getLength());
                result.put(thisArtikelName, thisMittelwertEinkaufspreis);
            }
        }
        return result;
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
        Document ret = null;
        dFactory = DocumentBuilderFactory.newInstance();
        builder = dFactory.newDocumentBuilder();
        ret = builder.parse(new InputSource(fis));
        return ret;
    }

}
