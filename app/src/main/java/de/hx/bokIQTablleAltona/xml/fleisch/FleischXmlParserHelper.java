package de.hx.bokIQTablleAltona.xml.fleisch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import de.hx.bokIQTablleAltona.models.fleisch.FleischBestellungModel;
import de.hx.bokIQTablleAltona.models.fleisch.FleischModel;
import de.hx.bokIQTablleAltona.models.fleisch.OneDayFleischBestellungenModel;

import android.os.Environment;

//Dung de lay OneDayBestellungen tu Xml dua vao BestellungID hoac 2 moc thoi gian va
//de xoa OneDayBestellungen dua vao BestellungID
public class FleischXmlParserHelper {

    //Lay ngay Bestellungen dua theo BestellungID
    public OneDayFleischBestellungenModel getOneDayFBWithBestellungID(
            String bestellungID) throws XmlPullParserException,
            ParserConfigurationException, SAXException, IOException {
        Document ret = getXMLDocument();

        Element zufindendeDayFBestellung = null;
        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayFBestellung = (Element) nodes.item(i);
            Element thisBestellungIDElement = (Element) thisOneDayFBestellung
                    .getElementsByTagName("bestellungID").item(0);
            String thisBestellungID = thisBestellungIDElement.getTextContent();
            if (thisBestellungID.equals(bestellungID)) {
                zufindendeDayFBestellung = thisOneDayFBestellung;
                break;
            }
        }
        return getOneDayFBFromXMLDOMElement(zufindendeDayFBestellung);
    }

    //Lay nhung Bestellungen tu nhung ngay nam giua 2 moc thoi gian
    public ArrayList<OneDayFleischBestellungenModel> getDaysFleischBestellungen(
            int vonDaysFrom1970, int bisDaysFrom1970) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<OneDayFleischBestellungenModel> result = new ArrayList<OneDayFleischBestellungenModel>();

        Document ret = getXMLDocument();

        ArrayList<Element> zufindendeDaysFBestellungenList = new ArrayList<Element>();
        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayFBestellung = (Element) nodes.item(i);
            Element thisDaysFrom1970Element = (Element) thisOneDayFBestellung
                    .getElementsByTagName("daysFrom1970").item(0);
            int thisDaysFrom1970 = Integer.valueOf(thisDaysFrom1970Element.getTextContent());
            if (thisDaysFrom1970 >= vonDaysFrom1970 && thisDaysFrom1970 <= bisDaysFrom1970) {
                zufindendeDaysFBestellungenList.add(thisOneDayFBestellung);
            }
        }
        for (Element thisOneDayFBestellung : zufindendeDaysFBestellungenList) {
            result.add(getOneDayFBFromXMLDOMElement(thisOneDayFBestellung));
        }
        return result;
    }

    //Tao OneDayFleischBestellunenModel tu mot XMLDOMElement
    OneDayFleischBestellungenModel getOneDayFBFromXMLDOMElement(Element element) {
        String datum;
        int daysFrom1970;
        double portionSumme, nettoUmsatzsumme, einkaufssumme;
        ArrayList<FleischBestellungModel> fbList = new ArrayList<FleischBestellungModel>();
        datum = element.getElementsByTagName("datum").item(0).getTextContent();
        daysFrom1970 = Integer.valueOf(element
                .getElementsByTagName("daysFrom1970").item(0).getTextContent());
        portionSumme = Double.valueOf(element
                .getElementsByTagName("portionSumme").item(0)
                .getTextContent());
        nettoUmsatzsumme = Double.valueOf(element
                .getElementsByTagName("nettoUmsatzsumme").item(0)
                .getTextContent());
        einkaufssumme = Double
                .valueOf(element.getElementsByTagName("einkaufssumme").item(0)
                        .getTextContent());
        NodeList fleischBestellungNodeList = element
                .getElementsByTagName("item");
        for (int i = 0; i < fleischBestellungNodeList.getLength(); i++) {
            FleischBestellungModel thisFleischBestellung;
            Element thisFleischBestellungElem = (Element) fleischBestellungNodeList
                    .item(i);
            FleischModel fleischModel;
            String proBestellung;
            int bestellungen;
            double total, portion, einkaufspreis, nettoEinkauf, bruttoEinkauf, verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz;
            fleischModel = new FleischModel(thisFleischBestellungElem
                    .getElementsByTagName("artikelName").item(0)
                    .getTextContent(), thisFleischBestellungElem
                    .getElementsByTagName("kategorie").item(0)
                    .getTextContent(), Integer.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("order").item(0)
                    .getTextContent()));
            proBestellung = thisFleischBestellungElem
                    .getElementsByTagName("proBestellung").item(0)
                    .getTextContent();
            bestellungen = Integer.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("bestellungen").item(0)
                    .getTextContent());
            total = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("total").item(0).getTextContent());
            portion = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("portion").item(0).getTextContent());
            einkaufspreis = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("einkaufspreis").item(0)
                    .getTextContent());
            nettoEinkauf = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("nettoEinkauf").item(0)
                    .getTextContent());
            bruttoEinkauf = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("bruttoEinkauf").item(0)
                    .getTextContent());
            verkaufspreis = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("verkaufspreis").item(0)
                    .getTextContent());
            nettoUmsatz = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("nettoUmsatz").item(0)
                    .getTextContent());
            bruttoUmsatz = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("bruttoUmsatz").item(0)
                    .getTextContent());
            wareneinsatz = Double.valueOf(thisFleischBestellungElem
                    .getElementsByTagName("wareneinsatz").item(0)
                    .getTextContent());
            thisFleischBestellung = new FleischBestellungModel(fleischModel,
                    proBestellung, bestellungen, total, portion, einkaufspreis,
                    nettoEinkauf, bruttoEinkauf, verkaufspreis, nettoUmsatz,
                    bruttoUmsatz, wareneinsatz);
            fbList.add(thisFleischBestellung);
        }
        return new OneDayFleischBestellungenModel(datum, daysFrom1970, fbList, portionSumme,
                nettoUmsatzsumme, einkaufssumme);
    }

    Document getXMLDocument() throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/fleisch_bestellungen.xml");
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

    //Xoa ngay Bestellungen dua theo BestellungID
    public void removeOneDayFBWithBestellungID(String bestellungID) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document ret = getXMLDocument();

        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayFBestellung = (Element) nodes.item(i);
            Element thisBestellungIDElement = (Element) thisOneDayFBestellung
                    .getElementsByTagName("bestellungID").item(0);
            String thisBestellungID = thisBestellungIDElement.getTextContent();
            if (thisBestellungID.equals(bestellungID)) {
                thisOneDayFBestellung.getParentNode().removeChild(thisOneDayFBestellung);
                break;
            }
        }

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/fleisch_bestellungen.xml");
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(ret);
        transformer.transform(source, result);
    }

}
