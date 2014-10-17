package de.hx.bokIQTablleAltona.models;

public class ArtikelBerichtModel {
    String artikelName, kategorie;
    int order;
    double total, portion, nettoEinkauf, bruttoEinkauf, nettoUmsatz, bruttoUmsatz;

    public ArtikelBerichtModel(String artikelName, String kategorie, int order,
                               double total, double portion, double nettoEinkauf, double bruttoEinkauf, double nettoUmsatz,
                               double bruttoUmsatz) {
        super();
        this.artikelName = artikelName;
        this.kategorie = kategorie;
        this.order = order;
        this.total = total;
        this.portion = portion;
        this.nettoEinkauf = nettoEinkauf;
        this.bruttoEinkauf = bruttoEinkauf;
        this.nettoUmsatz = nettoUmsatz;
        this.bruttoUmsatz = bruttoUmsatz;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public void addPortion(double portion) {
        this.portion += portion;
    }

    public void addTotal(double total) {
        this.total += total;
    }

    public void addNettoEinkauf(double nettoEinkauf) {
        this.nettoEinkauf += nettoEinkauf;
    }

    public void addBruttoEinkauf(double bruttoEinkauf) {
        this.bruttoEinkauf += bruttoEinkauf;
    }

    public void addNettoUmsatz(double nettoUmsatz) {
        this.nettoUmsatz += nettoUmsatz;
    }

    public void addBruttoUmsatz(double bruttoUmsatz) {
        this.bruttoUmsatz += bruttoUmsatz;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getArtikelName() {
        return artikelName;
    }

    public void setArtikelName(String artikelName) {
        this.artikelName = artikelName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((artikelName == null) ? 0 : artikelName.hashCode());
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
        ArtikelBerichtModel other = (ArtikelBerichtModel) obj;
        if (artikelName == null) {
            if (other.artikelName != null)
                return false;
        } else if (!artikelName.equals(other.artikelName))
            return false;
        return true;
    }

}
