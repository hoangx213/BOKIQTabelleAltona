package de.hx.bokIQTablleAltona.models.fleisch;


public class FleischBestellungModel {

    FleischModel fleischModel;
    String proBestellung;
    int bestellungen;
    double total, portion, einkaufspreis, nettoEinkauf, bruttoEinkauf,
            verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz;

    public FleischBestellungModel(FleischModel fleischModel, String proBestellung,
                                  int bestellungen, double total, double portion, double einkaufspreis,
                                  double nettoEinkauf, double bruttoEinkauf, double verkaufspreis,
                                  double nettoUmsatz, double bruttoUmsatz, double wareneinsatz) {
        super();
        this.fleischModel = fleischModel;
        this.proBestellung = proBestellung;
        this.bestellungen = bestellungen;
        this.total = total;
        this.portion = portion;
        this.einkaufspreis = einkaufspreis;
        this.nettoEinkauf = nettoEinkauf;
        this.bruttoEinkauf = bruttoEinkauf;
        this.verkaufspreis = verkaufspreis;
        this.nettoUmsatz = nettoUmsatz;
        this.bruttoUmsatz = bruttoUmsatz;
        this.wareneinsatz = wareneinsatz;
    }

    public FleischBestellungModel(FleischModel fleischModel) {
        this.fleischModel = fleischModel;
    }


    public String getProBestellung() {
        return proBestellung;
    }


    public void setProBestellung(String proBestellung) {
        this.proBestellung = proBestellung;
    }


    public void setEinkaufspreis(double einkaufspreis) {
        this.einkaufspreis = einkaufspreis;
    }


    public FleischModel getFleischModel() {
        return fleischModel;
    }


    public void setFleischModel(FleischModel fleischModel) {
        this.fleischModel = fleischModel;
    }


    public int getBestellungen() {
        return bestellungen;
    }


    public void setBestellungen(int bestellungen) {
        this.bestellungen = bestellungen;
    }


    public double getTotal() {
        return total;
    }


    public void setTotal(double total) {
        this.total = total;
    }


    public double getEinkaufspreis() {
        return einkaufspreis;
    }

    public double getNettoEinkauf() {
        return nettoEinkauf;
    }


    public void setNettoEinkauf(double nettoEinkauf) {
        this.nettoEinkauf = nettoEinkauf;
    }


    public double getBruttoEinkauf() {
        return bruttoEinkauf;
    }


    public void setBruttoEinkauf(double bruttoEinkauf) {
        this.bruttoEinkauf = bruttoEinkauf;
    }


    public double getVerkaufspreis() {
        return verkaufspreis;
    }


    public void setVerkaufspreis(double verkaufspreis) {
        this.verkaufspreis = verkaufspreis;
    }


    public double getNettoUmsatz() {
        return nettoUmsatz;
    }


    public void setNettoUmsatz(double nettoUmsatz) {
        this.nettoUmsatz = nettoUmsatz;
    }


    public double getBruttoUmsatz() {
        return bruttoUmsatz;
    }


    public void setBruttoUmsatz(double bruttoUmsatz) {
        this.bruttoUmsatz = bruttoUmsatz;
    }


    public double getWareneinsatz() {
        return wareneinsatz;
    }


    public void setWareneinsatz(double wareneinsatz) {
        this.wareneinsatz = wareneinsatz;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fleischModel == null) ? 0 : fleischModel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FleischBestellungModel other = (FleischBestellungModel) obj;
        if (fleischModel == null) {
            if (other.fleischModel != null)
                return false;
        } else if (!fleischModel.equals(other.fleischModel))
            return false;
        return true;
    }
}
