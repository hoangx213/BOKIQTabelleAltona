package de.hx.bokIQTablleAltona.xml.getraenke;

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

import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeModel;
import de.hx.bokIQTablleAltona.models.getraenke.OneDayGetraenkeBestellungenModel;

import android.os.Environment;

//Dung de lay OneDayBestellungen tu Xml dua vao BestellungID hoac 2 moc thoi gian va
//de xoa OneDayBestellungen dua vao BestellungID
public class GetraenkeXmlParserHelper {

    //Lay ngay Bestellungen dua theo BestellungID
    public OneDayGetraenkeBestellungenModel getOneDayGBWithBestellungID(
            String bestellungID) throws XmlPullParserException,
            ParserConfigurationException, SAXException, IOException {
        Document ret = getXMLDocument();

        Element zufindendeDayGBestellung = null;
        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayGBestellung = (Element) nodes.item(i);
            Element thisBestellungIDElement = (Element) thisOneDayGBestellung
                    .getElementsByTagName("bestellungID").item(0);
            String thisBestellungID = thisBestellungIDElement.getTextContent();
            if (thisBestellungID.equals(bestellungID)) {
                zufindendeDayGBestellung = thisOneDayGBestellung;
                break;
            }
        }
        return getOneDayGBFromXMLDOMElement(zufindendeDayGBestellung);
    }

    //Lay nhung Bestellungen tu nhung ngay nam giua 2 moc thoi gian
    public ArrayList<OneDayGetraenkeBestellungenModel> getDaysGetraenkeBestellungen(
            int vonDaysFrom1970, int bisDaysFrom1970) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<OneDayGetraenkeBestellungenModel> result = new ArrayList<OneDayGetraenkeBestellungenModel>();

        Document ret = getXMLDocument();

        ArrayList<Element> zufindendeDaysGBestellungenList = new ArrayList<Element>();
        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayGBestellung = (Element) nodes.item(i);
            Element thisDaysFrom1970Element = (Element) thisOneDayGBestellung
                    .getElementsByTagName("daysFrom1970").item(0);
            int thisDaysFrom1970 = Integer.valueOf(thisDaysFrom1970Element.getTextContent());
            if (thisDaysFrom1970 >= vonDaysFrom1970 && thisDaysFrom1970 <= bisDaysFrom1970) {
                zufindendeDaysGBestellungenList.add(thisOneDayGBestellung);
            }
        }
        for (Element thisOneDayGBestellung : zufindendeDaysGBestellungenList) {
            result.add(getOneDayGBFromXMLDOMElement(thisOneDayGBestellung));
        }
        return result;
    }

    //Tao OneDayGetraenkeBestellunenModel tu mot XMLDOMElement
    OneDayGetraenkeBestellungenModel getOneDayGBFromXMLDOMElement(Element element) {
        String datum;
        int daysFrom1970;
        double portionSumme, nettoUmsatzsumme, einkaufssumme;
        ArrayList<GetraenkeBestellungModel> fbList = new ArrayList<GetraenkeBestellungModel>();
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
        NodeList getraenkeBestellungNodeList = element
                .getElementsByTagName("item");
        for (int i = 0; i < getraenkeBestellungNodeList.getLength(); i++) {
            GetraenkeBestellungModel thisGetraenkeBestellung;
            Element thisGetraenkeBestellungElem = (Element) getraenkeBestellungNodeList
                    .item(i);
            GetraenkeModel getraenkeModel;
            String proBestellung;
            int bestellungen;
            double total, portion, einkaufspreis, nettoEinkauf, bruttoEinkauf, verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz;
            getraenkeModel = new GetraenkeModel(thisGetraenkeBestellungElem
                    .getElementsByTagName("artikelName").item(0)
                    .getTextContent(), thisGetraenkeBestellungElem
                    .getElementsByTagName("kategorie").item(0)
                    .getTextContent(), Integer.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("order").item(0)
                    .getTextContent()));
            proBestellung = thisGetraenkeBestellungElem
                    .getElementsByTagName("proBestellung").item(0)
                    .getTextContent();
            bestellungen = Integer.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("bestellungen").item(0)
                    .getTextContent());
            total = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("total").item(0).getTextContent());
            portion = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("portion").item(0).getTextContent());
            einkaufspreis = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("einkaufspreis").item(0)
                    .getTextContent());
            nettoEinkauf = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("nettoEinkauf").item(0)
                    .getTextContent());
            bruttoEinkauf = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("bruttoEinkauf").item(0)
                    .getTextContent());
            verkaufspreis = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("verkaufspreis").item(0)
                    .getTextContent());
            nettoUmsatz = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("nettoUmsatz").item(0)
                    .getTextContent());
            bruttoUmsatz = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("bruttoUmsatz").item(0)
                    .getTextContent());
            wareneinsatz = Double.valueOf(thisGetraenkeBestellungElem
                    .getElementsByTagName("wareneinsatz").item(0)
                    .getTextContent());
            thisGetraenkeBestellung = new GetraenkeBestellungModel(getraenkeModel,
                    proBestellung, bestellungen, total, portion, einkaufspreis,
                    nettoEinkauf, bruttoEinkauf, verkaufspreis, nettoUmsatz,
                    bruttoUmsatz, wareneinsatz);
            fbList.add(thisGetraenkeBestellung);
        }
        return new OneDayGetraenkeBestellungenModel(datum, daysFrom1970, fbList,
                portionSumme, nettoUmsatzsumme, einkaufssumme);
    }

    Document getXMLDocument() throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_bestellungen.xml");
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
    public void removeOneDayGBWithBestellungID(String bestellungID) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document ret = getXMLDocument();

        NodeList nodes = ret.getElementsByTagName("bestellung");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element thisOneDayGBestellung = (Element) nodes.item(i);
            Element thisBestellungIDElement = (Element) thisOneDayGBestellung
                    .getElementsByTagName("bestellungID").item(0);
            String thisBestellungID = thisBestellungIDElement.getTextContent();
            if (thisBestellungID.equals(bestellungID)) {
                thisOneDayGBestellung.getParentNode().removeChild(thisOneDayGBestellung);
                break;
            }
        }

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_bestellungen.xml");
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(ret);
        transformer.transform(source, result);
    }

}
