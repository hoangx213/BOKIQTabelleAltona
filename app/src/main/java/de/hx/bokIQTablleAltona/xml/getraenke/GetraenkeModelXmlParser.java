package de.hx.bokIQTablleAltona.xml.getraenke;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.Environment;

import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeModel;

public class GetraenkeModelXmlParser {

    Context context;

    public GetraenkeModelXmlParser(Context context) {
        this.context = context;
    }

    public ArrayList<GetraenkeModel> getraenkeParsen()
            throws XmlPullParserException, IOException {

        ArrayList<GetraenkeModel> result = new ArrayList<GetraenkeModel>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke.xml");
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("getraenke")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    while (!nodeName.contentEquals("getraenke")) {
                        if (nodeName.contentEquals("item")
                                && eventType == XmlPullParser.START_TAG) {
                            GetraenkeModel getraenkeModel;
                            String artikelName = "", einheit = "";
                            double einheitProBestellung = 0, einheitProGlas = 0, verkaufspreis = 0, einkaufspreis = 0,
                                    schwund = 0, festAnteil = 0;
                            String kategorie = "";
                            int order = 0;
                            eventType = xpp.next();
                            nodeName = (xpp.getName() != null ? xpp.getName()
                                    : "");
                            while (!nodeName.contentEquals("item")) {
                                if (nodeName.contentEquals("artikelName")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    artikelName = xpp.getText();
                                } else if (nodeName.contentEquals("einheit")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheit = xpp.getText();
                                } else if (nodeName
                                        .contentEquals("einheitProBestellung")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProBestellung = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("einheitProGlas")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProGlas = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("verkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    verkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("einkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("order")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    order = Integer.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("schwund")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    schwund = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("festAnteil")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    festAnteil = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("kategorie")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    kategorie = String.valueOf(xpp.getText());
                                }

                                eventType = xpp.next();
                                nodeName = (xpp.getName() != null ? xpp
                                        .getName() : "");
                            }
                            getraenkeModel = new GetraenkeModel(artikelName,
                                    einheit, einheitProBestellung,
                                    einheitProGlas, einkaufspreis,
                                    verkaufspreis, schwund, kategorie, order);
                            result.add(getraenkeModel);
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

    public ArrayList<GetraenkeModel> getraenkeWithAnteilParsen()
            throws XmlPullParserException, IOException {

        ArrayList<GetraenkeModel> result = new ArrayList<GetraenkeModel>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke.xml");
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("getraenke")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    while (!nodeName.contentEquals("getraenke")) {
                        if (nodeName.contentEquals("item")
                                && eventType == XmlPullParser.START_TAG) {
                            GetraenkeModel getraenkeModel;
                            String artikelName = "", einheit = "";
                            double einheitProBestellung = 0, einheitProGlas = 0, verkaufspreis = 0, einkaufspreis = 0,
                                    schwund = 0, anteil = 0;
                            String kategorie = "";
                            int order = 0;
                            eventType = xpp.next();
                            nodeName = (xpp.getName() != null ? xpp.getName()
                                    : "");
                            while (!nodeName.contentEquals("item")) {
                                if (nodeName.contentEquals("artikelName")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    artikelName = xpp.getText();
                                } else if (nodeName.contentEquals("einheit")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheit = xpp.getText();
                                } else if (nodeName
                                        .contentEquals("einheitProBestellung")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProBestellung = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("einheitProGlas")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einheitProGlas = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("verkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    verkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("einkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("order")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    order = Integer.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("schwund")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    schwund = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("anteil")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    anteil = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("kategorie")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    kategorie = String.valueOf(xpp.getText());
                                }

                                eventType = xpp.next();
                                nodeName = (xpp.getName() != null ? xpp
                                        .getName() : "");
                            }
                            getraenkeModel = new GetraenkeModel(artikelName,
                                    einheit, einheitProBestellung,
                                    einheitProGlas, einkaufspreis,
                                    verkaufspreis, schwund, kategorie, order, anteil);
                            result.add(getraenkeModel);
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
