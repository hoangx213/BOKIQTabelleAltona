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

import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeModel;
import de.hx.bokIQTablleAltona.models.getraenke.OneDayGetraenkeBestellungenModel;

public class GetraenkeBestellungenXmlParser {

    Context context;

    public GetraenkeBestellungenXmlParser(Context context) {
        this.context = context;
    }

    public ArrayList<OneDayGetraenkeBestellungenModel> getraenkeParsen()
            throws XmlPullParserException, IOException {

        ArrayList<OneDayGetraenkeBestellungenModel> result = new ArrayList<OneDayGetraenkeBestellungenModel>();
        // XmlPullParser xpp = context.getResources().getXml(R.xml.getraenke);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_bestellungen.xml");
        if (!xmlFile.exists())
            return null;
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("bestellung")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    OneDayGetraenkeBestellungenModel thisDayGetraenkeBestellung;
                    String bestellungsdatum = "";
                    int daysFrom1970 = 0;
                    String bestellungID = "";
                    double portionSumme = 0, nettoUmsatzsumme = 0, einkaufssumme = 0;
                    ArrayList<GetraenkeBestellungModel> thisDayGetraenkeBestellungenList = new ArrayList<GetraenkeBestellungModel>();
                    while (!nodeName.contentEquals("bestellung")) {
                        if (nodeName.contentEquals("nettoUmsatzsumme")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            nettoUmsatzsumme = Double.valueOf(xpp.getText());
                        } else if (nodeName.contentEquals("bestellungID")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            bestellungID = xpp.getText();
                        } else if (nodeName.contentEquals("portionSumme")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            portionSumme = Double.valueOf(xpp.getText());
                        } else if (nodeName.contentEquals("einkaufssumme")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            einkaufssumme = Double.valueOf(xpp.getText());
                        } else if (nodeName.contentEquals("datum")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            bestellungsdatum = xpp.getText();
                        } else if (nodeName.contentEquals("daysFrom1970")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            daysFrom1970 = Integer.valueOf(xpp.getText());
                        } else if (nodeName.contentEquals("item")
                                && eventType == XmlPullParser.START_TAG) {
                            GetraenkeBestellungModel getraenkeBestellung;
                            String artikelName = "";
                            String proBestellung = "";
                            int bestellungen = 0;
                            double total = 0.0, portion = 0;
                            double einkaufspreis = 0, nettoEinkauf = 0, bruttoEinkauf = 0, verkaufspreis = 0, nettoUmsatz = 0, bruttoUmsatz = 0, wareneinsatz = 0;
                            eventType = xpp.next();
                            nodeName = (xpp.getName() != null ? xpp.getName()
                                    : "");
                            while (!nodeName.contentEquals("item")) {
                                if (nodeName.contentEquals("artikelName")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    artikelName = xpp.getText();
                                } else if (nodeName
                                        .contentEquals("proBestellung")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    proBestellung = xpp.getText();
                                } else if (nodeName
                                        .contentEquals("bestellungen")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    bestellungen = Integer.valueOf(xpp
                                            .getText());
                                } else if (nodeName.contentEquals("total")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    total = Double.valueOf(xpp.getText());
                                } else if (nodeName.contentEquals("portion")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    portion = Double.valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("einkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    einkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("nettoEinkauf")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    nettoEinkauf = Double
                                            .valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("bruttoEinkauf")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    bruttoEinkauf = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("verkaufspreis")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    verkaufspreis = Double.valueOf(xpp
                                            .getText());
                                } else if (nodeName
                                        .contentEquals("nettoUmsatz")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    nettoUmsatz = Double.valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("bruttoUmsatz")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    bruttoUmsatz = Double
                                            .valueOf(xpp.getText());
                                } else if (nodeName
                                        .contentEquals("wareneinsatz")
                                        && eventType == XmlPullParser.START_TAG) {
                                    eventType = xpp.next();
                                    wareneinsatz = Double
                                            .valueOf(xpp.getText());
                                }
                                eventType = xpp.next();
                                nodeName = (xpp.getName() != null ? xpp
                                        .getName() : "");
                            }
                            getraenkeBestellung = new GetraenkeBestellungModel(
                                    new GetraenkeModel(artikelName),
                                    proBestellung, bestellungen, total, portion,
                                    einkaufspreis, nettoEinkauf, bruttoEinkauf,
                                    verkaufspreis, nettoUmsatz, bruttoUmsatz,
                                    wareneinsatz);
                            thisDayGetraenkeBestellungenList
                                    .add(getraenkeBestellung);
                        }
                        eventType = xpp.next();
                        nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    }
                    thisDayGetraenkeBestellung = new OneDayGetraenkeBestellungenModel(
                            bestellungsdatum, daysFrom1970,
                            thisDayGetraenkeBestellungenList, portionSumme, nettoUmsatzsumme,
                            einkaufssumme);
                    thisDayGetraenkeBestellung.setBestellungID(bestellungID);
                    result.add(thisDayGetraenkeBestellung);
                }

                eventType = xpp.next();
                nodeName = (xpp.getName() != null ? xpp.getName() : "");
            }
            eventType = xpp.next();
            nodeName = (xpp.getName() != null ? xpp.getName() : "");
        }
        return result;
    }

    public ArrayList<OneDayGetraenkeBestellungenModel> getraenkeParsenSimple()
            throws XmlPullParserException, IOException {

        ArrayList<OneDayGetraenkeBestellungenModel> result = new ArrayList<OneDayGetraenkeBestellungenModel>();
        // XmlPullParser xpp = context.getResources().getXml(R.xml.getraenke);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        File xmlFile = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona/getraenke_bestellungen.xml");
        if (!xmlFile.exists())
            return null;
        FileInputStream fis = new FileInputStream(xmlFile);
        xpp.setInput(new InputStreamReader(fis));

        int eventType = xpp.getEventType();
        String nodeName;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (xpp.getName() != null) {
                if (xpp.getName().contentEquals("bestellung")
                        && eventType == XmlPullParser.START_TAG) {
                    eventType = xpp.next();
                    nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    OneDayGetraenkeBestellungenModel thisDayGetraenkeBestellung;
                    String bestellungsdatum = "";
                    int daysFrom1970 = 0;
                    String bestellungID = "";
                    while (!nodeName.contentEquals("bestellung")) {

                        if (nodeName.contentEquals("bestellungID")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            bestellungID = xpp.getText();
                        } else if (nodeName.contentEquals("datum")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            bestellungsdatum = xpp.getText();
                        } else if (nodeName.contentEquals("daysFrom1970")
                                && eventType == XmlPullParser.START_TAG) {
                            eventType = xpp.next();
                            daysFrom1970 = Integer.valueOf(xpp.getText());
                        }

                        eventType = xpp.next();
                        nodeName = (xpp.getName() != null ? xpp.getName() : "");
                    }
                    thisDayGetraenkeBestellung = new OneDayGetraenkeBestellungenModel(bestellungID,
                            bestellungsdatum, daysFrom1970);
                    result.add(thisDayGetraenkeBestellung);
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
