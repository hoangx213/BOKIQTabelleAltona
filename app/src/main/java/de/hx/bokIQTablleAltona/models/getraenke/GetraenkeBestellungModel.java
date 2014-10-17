package de.hx.bokIQTablleAltona.models.getraenke;


public class GetraenkeBestellungModel {

    GetraenkeModel getraenkeModel;
    String proBestellung;
    int bestellungen;
    double total, portion, einkaufspreis, nettoEinkauf, bruttoEinkauf,
            verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz;

    public GetraenkeBestellungModel(GetraenkeModel getraenkeModel, String proBestellung,
                                    int bestellungen, double total, double portion, double einkaufspreis,
                                    double nettoEinkauf, double bruttoEinkauf, double verkaufspreis,
                                    double nettoUmsatz, double bruttoUmsatz, double wareneinsatz) {
        super();
        this.getraenkeModel = getraenkeModel;
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

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public GetraenkeBestellungModel(GetraenkeModel getraenkeModel) {
        this.getraenkeModel = getraenkeModel;
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


    public GetraenkeModel getGetraenkeModel() {
        return getraenkeModel;
    }


    public void setFleischModel(GetraenkeModel getraenkeModel) {
        this.getraenkeModel = getraenkeModel;
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getraenkeModel == null) ? 0 : getraenkeModel.hashCode());
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
        GetraenkeBestellungModel other = (GetraenkeBestellungModel) obj;
        if (getraenkeModel == null) {
            if (other.getraenkeModel != null)
                return false;
        } else if (!getraenkeModel.equals(other.getraenkeModel))
            return false;
        return true;
    }
}
