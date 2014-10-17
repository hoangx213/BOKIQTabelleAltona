package de.hx.bokIQTablleAltona.xml.fleisch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import de.hx.bokIQTablleAltona.models.fleisch.FleischModel;

import android.content.Context;
import android.os.Environment;

public class FleischModelXmlParser {

    Context context;

    public FleischModelXmlParser(Context context) {
        this.context = context;
    }

    public ArrayList<FleischModel> fleischParsen()
            throws XmlPullParserException, IOException {

        ArrayList<FleischModel> result = new ArrayList<FleischModel>();
//		XmlPullParser xpp = context.getResources().getXml(R.xml.fleisch);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory() + "/BOK/Altona/fleisch.xml");
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("fleisch")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    while (!nodeName.contentEquals("fleisch")) {
                        if (nodeName.contentEquals("item") && eventType == XmlPullParser.START_TAG) {
                            FleischModel fleischModel;
                            String artikelName = "", einheit = "";
                            double einheitProBestellung = 0, schwund = 0, verkaufsfaktor = 0, bruttoPreis = 0,
                                    nettoPreis = 0, einkaufspreis = 0, festAnteil = 0;
                            String kategorie = "";
                            int order = 0;
                            eventType = xpp.next();
                            nodeName = (xpp.getName() != null ? xpp.getName() : "");
                            while (!nodeName.contentEquals("item")) {
                                if (nodeName.contentEquals("artikelName")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    artikelName = xpp.getText();
                                } else if (nodeName.contentEquals("einheit")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheit = xpp.getText();
                                } else if (nodeName.contentEquals(
                                        "einheitProBestellung")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProBestellung = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("schwund")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    schwund = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals(
                                        "verkaufsfaktor")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    verkaufsfaktor = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("bruttoPreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    bruttoPreis = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("nettoPreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    nettoPreis = Double.valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("einkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("festAnteil")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    festAnteil = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("kategorie")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    kategorie = String.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("order")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    order = Integer.valueOf(xpp
                                            .getText());
                                }
                                eventType = xpp.next();
                                nodeName = (xpp.getName() != null ? xpp.getName() : "");
                            }
                            fleischModel = new FleischModel(artikelName,
                                    einheit, einheitProBestellung, schwund,
                                    verkaufsfaktor, bruttoPreis, nettoPreis,
                                    einkaufspreis, kategorie, order);
                            result.add(fleischModel);
                        }
                        eventType = xpp.next();
                        nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    }
                }
                eventType = xpp.next();
                nodeName = (xpp.getName() != null ? xpp.getName() : "");
            }
            eventType = xpp.next();
            nodeName = (xpp.getName() != null ? xpp.getName() : "");
        }
        return result;
    }

    public ArrayList<FleischModel> fleischWithAnteilParsen()
            throws XmlPullParserException, IOException {

        ArrayList<FleischModel> result = new ArrayList<FleischModel>();
//		XmlPullParser xpp = context.getResources().getXml(R.xml.fleisch);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory() + "/BOK/Altona/fleisch.xml");
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("fleisch")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    while (!nodeName.contentEquals("fleisch")) {
                        if (nodeName.contentEquals("item") && eventType == XmlPullParser.START_TAG) {
                            FleischModel fleischModel;
                            String artikelName = "", einheit = "";
                            double einheitProBestellung = 0, schwund = 0, verkaufsfaktor = 0, bruttoPreis = 0,
                                    nettoPreis = 0, einkaufspreis = 0, anteil = 0;
                            String kategorie = "";
                            int order = 0;
                            eventType = xpp.next();
                            nodeName = (xpp.getName() != null ? xpp.getName() : "");
                            while (!nodeName.contentEquals("item")) {
                                if (nodeName.contentEquals("artikelName")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    artikelName = xpp.getText();
                                } else if (nodeName.contentEquals("einheit")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheit = xpp.getText();
                                } else if (nodeName.contentEquals(
                                        "einheitProBestellung")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProBestellung = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("schwund")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    schwund = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals(
                                        "verkaufsfaktor")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    verkaufsfaktor = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("bruttoPreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    bruttoPreis = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("nettoPreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    nettoPreis = Double.valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("einkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("anteil")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    anteil = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("kategorie")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    kategorie = String.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("order")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    order = Integer.valueOf(xpp
                                            .getText());
                                }
                                eventType = xpp.next();
                                nodeName = (xpp.getName() != null ? xpp.getName() : "");
                            }
                            fleischModel = new FleischModel(artikelName,
                                    einheit, einheitProBestellung, schwund,
                                    verkaufsfaktor, bruttoPreis, nettoPreis,
                                    einkaufspreis, kategorie, order, anteil);
                            result.add(fleischModel);
                        }
                        eventType = xpp.next();
                        nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    }
                }
                eventType = xpp.next();
                nodeName = (xpp.getName() != null ? xpp.getName() : "");
            }
            eventType = xpp.next();
            nodeName = (xpp.getName() != null ? xpp.getName() : "");
        }
        return result;
    }

}
