package de.hx.bokIQTablleAltona.models.fleisch;

import java.util.ArrayList;

public class OneDayFleischBestellungenModel {
    String datum;
    int daysFrom1970;
    ArrayList<FleischBestellungModel> fleischbestellungen;
    double portionSumme, nettoUmsatzssumme, einkaufssumme;
    String bestellungID;

    public OneDayFleischBestellungenModel(String datum, int daysFrom1970,
                                          ArrayList<FleischBestellungModel> fleischbestellungen, double portionSumme,
                                          double nettoUmsatzssumme, double einkaufssumme) {
        super();
        this.datum = datum;
        this.daysFrom1970 = daysFrom1970;
        this.portionSumme = portionSumme;
        this.fleischbestellungen = fleischbestellungen;
        this.nettoUmsatzssumme = nettoUmsatzssumme;
        this.einkaufssumme = einkaufssumme;
    }

    public OneDayFleischBestellungenModel(String bestellungID, String datum, int daysFrom1970) {
        this.bestellungID = bestellungID;
        this.datum = datum;
        this.daysFrom1970 = daysFrom1970;
    }

    public double getPortionSumme() {
        return portionSumme;
    }

    public void setPortionSumme(double portionSumme) {
        this.portionSumme = portionSumme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getDaysFrom1970() {
        return daysFrom1970;
    }

    public void setDaysFrom1970(int daysFrom1970) {
        this.daysFrom1970 = daysFrom1970;
    }

    public ArrayList<FleischBestellungModel> getFleischbestellungen() {
        return fleischbestellungen;
    }

    public void setFleischbestellungen(
            ArrayList<FleischBestellungModel> fleischbestellungen) {
        this.fleischbestellungen = fleischbestellungen;
    }

    public double getNettoUmsatzssumme() {
        return nettoUmsatzssumme;
    }

    public void setNettoUmsatzssumme(double nettoUmsatzssumme) {
        this.nettoUmsatzssumme = nettoUmsatzssumme;
    }

    public double getEinkaufssumme() {
        return einkaufssumme;
    }

    public void setEinkaufssumme(double einkaufssumme) {
        this.einkaufssumme = einkaufssumme;
    }

    public void setBestellungID(String id) {
        this.bestellungID = id;
    }

    public String getBestellungID() {
        return this.bestellungID;
    }

    public String toString() {
        return datum;
    }
}
