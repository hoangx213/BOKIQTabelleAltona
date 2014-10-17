package de.hx.bokIQTablleAltona.xml.getraenke;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.os.Environment;

import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;

public class GetraenkeBestellungenXmlWriter {

    public GetraenkeBestellungenXmlWriter() {
    }

    ;

    public void writeGetraenkeBestellungenXml(
            ArrayList<GetraenkeBestellungModel> gbList, String bestellungID, double portionSumme, double nettoUmsatzsumme,
            double einkaufssumme, String bestellungsdatum, int daysFrom1970)
            throws ParserConfigurationException, SAXException, IOException,
            TransformerFactoryConfigurationError, TransformerException {

//		deleteExistedBestellungTag(daysFrom1970);

        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona", "getraenke_bestellungen.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);

        Node fbNode = doc.getFirstChild();
        Node thisBestellung = doc.createElement("bestellung");
        fbNode.appendChild(thisBestellung);

        Node bestellungIDNode = doc.createElement("bestellungID");
        bestellungIDNode.setTextContent(bestellungID);
        thisBestellung.appendChild(bestellungIDNode);

        Node portionSummeNode = doc.createElement("portionSumme");
        portionSummeNode.setTextContent(String.valueOf(portionSumme));
        thisBestellung.appendChild(portionSummeNode);

        Node nettoUmsatzsummeNode = doc.createElement("nettoUmsatzsumme");
        nettoUmsatzsummeNode.setTextContent(String.valueOf(nettoUmsatzsumme));
        thisBestellung.appendChild(nettoUmsatzsummeNode);

        Node einkaufssummeNode = doc.createElement("einkaufssumme");
        einkaufssummeNode.setTextContent(String.valueOf(einkaufssumme));
        thisBestellung.appendChild(einkaufssummeNode);

        Node datumNode = doc.createElement("datum");
        datumNode.setTextContent(String.valueOf(bestellungsdatum));
        thisBestellung.appendChild(datumNode);

        Node daysFrom1970Node = doc.createElement("daysFrom1970");
        daysFrom1970Node.setTextContent(String.valueOf(daysFrom1970));
        thisBestellung.appendChild(daysFrom1970Node);

        for (GetraenkeBestellungModel i : gbList) {
            Node itemNode = doc.createElement("item");
            Node artikelNameNode = doc.createElement("artikelName");
            artikelNameNode
                    .setTextContent(i.getGetraenkeModel().getArtikelName());
            itemNode.appendChild(artikelNameNode);
            Node kategorieNode = doc.createElement("kategorie");
            kategorieNode
                    .setTextContent(i.getGetraenkeModel().getKategorie());
            itemNode.appendChild(kategorieNode);
            Node orderNode = doc.createElement("order");
            orderNode
                    .setTextContent(String.valueOf(i.getGetraenkeModel().getOrder()));
            itemNode.appendChild(orderNode);
            Node proBestellung = doc.createElement("proBestellung");
            proBestellung.setTextContent(String.valueOf(i.getProBestellung()));
            itemNode.appendChild(proBestellung);
            Node bestellungenNode = doc.createElement("bestellungen");
            bestellungenNode
                    .setTextContent(String.valueOf(i.getBestellungen()));
            itemNode.appendChild(bestellungenNode);
            Node totalNode = doc.createElement("total");
            totalNode.setTextContent(String.valueOf(i.getTotal()));
            itemNode.appendChild(totalNode);
            Node portionNode = doc.createElement("portion");
            portionNode.setTextContent(String.valueOf(i.getPortion()));
            itemNode.appendChild(portionNode);
            Node einkaufspreis = doc.createElement("einkaufspreis");
            einkaufspreis.setTextContent(String.valueOf(i.getEinkaufspreis()));
            itemNode.appendChild(einkaufspreis);
            Node nettoEinkauf = doc.createElement("nettoEinkauf");
            nettoEinkauf.setTextContent(String.valueOf(i.getNettoEinkauf()));
            itemNode.appendChild(nettoEinkauf);
            Node bruttoEinkauf = doc.createElement("bruttoEinkauf");
            bruttoEinkauf.setTextContent(String.valueOf(i.getBruttoEinkauf()));
            itemNode.appendChild(bruttoEinkauf);
            Node verkaufspreis = doc.createElement("verkaufspreis");
            verkaufspreis.setTextContent(String.valueOf(i.getEinkaufspreis()));
            itemNode.appendChild(verkaufspreis);
            Node nettoUmsatz = doc.createElement("nettoUmsatz");
            nettoUmsatz.setTextContent(String.valueOf(i.getNettoUmsatz()));
            itemNode.appendChild(nettoUmsatz);
            Node bruttoUmsatz = doc.createElement("bruttoUmsatz");
            bruttoUmsatz.setTextContent(String.valueOf(i.getBruttoUmsatz()));
            itemNode.appendChild(bruttoUmsatz);
            Node wareneinsatz = doc.createElement("wareneinsatz");
            wareneinsatz.setTextContent(String.valueOf(i.getWareneinsatz()));
            itemNode.appendChild(wareneinsatz);
            thisBestellung.appendChild(itemNode);
        }

        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(xmlFile);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
    }
}
