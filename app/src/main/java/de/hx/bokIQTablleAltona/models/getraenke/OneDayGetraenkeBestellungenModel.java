package de.hx.bokIQTablleAltona.models.getraenke;

import java.util.ArrayList;

public class OneDayGetraenkeBestellungenModel {
    String datum;
    int daysFrom1970;
    ArrayList<GetraenkeBestellungModel> getraenkeBestellungen;
    double portionSumme, nettoUmsatzssumme, einkaufssumme;
    String bestellungID;

    public OneDayGetraenkeBestellungenModel(String datum, int daysFrom1970,
                                            ArrayList<GetraenkeBestellungModel> getraenkeBestellungen, double portionSumme,
                                            double nettoUmsatzssumme, double einkaufssumme) {
        super();
        this.datum = datum;
        this.daysFrom1970 = daysFrom1970;
        this.getraenkeBestellungen = getraenkeBestellungen;
        this.portionSumme = portionSumme;
        this.nettoUmsatzssumme = nettoUmsatzssumme;
        this.einkaufssumme = einkaufssumme;
    }

    public OneDayGetraenkeBestellungenModel(String bestellungID, String datum, int daysFrom1970) {
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

    public ArrayList<GetraenkeBestellungModel> getGetraenkebestellungen() {
        return getraenkeBestellungen;
    }

    public void setGetraenkebestellungen(
            ArrayList<GetraenkeBestellungModel> getraenkebestellungen) {
        this.getraenkeBestellungen = getraenkebestellungen;
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
