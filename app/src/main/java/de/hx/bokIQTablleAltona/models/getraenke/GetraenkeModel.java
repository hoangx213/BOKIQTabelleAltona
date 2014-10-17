package de.hx.bokIQTablleAltona.models.getraenke;

public class GetraenkeModel {
    String artikelName, einheit;
    double einheitProBestellung, einheitProGlas, einkaufspreis,
            verkaufspreis, schwund;
    String kategorie;
    int order;
    double anteil;

    public GetraenkeModel(String artikelName, String einheit,
                          double einheitProBestellung, double einheitProGlas,
                          double einkaufspreis, double verkaufspreis, double schwund,
                          String kategorie, int order) {
        super();
        this.artikelName = artikelName;
        this.einheit = einheit;
        this.einheitProBestellung = einheitProBestellung;
        this.einheitProGlas = einheitProGlas;
        this.einkaufspreis = einkaufspreis;
        this.verkaufspreis = verkaufspreis;
        this.schwund = schwund;
        this.kategorie = kategorie;
        this.order = order;
    }

    public GetraenkeModel(String artikelName, String einheit,
                          double einheitProBestellung, double einheitProGlas,
                          double einkaufspreis, double verkaufspreis, double schwund,
                          String kategorie, int order, double anteil) {
        super();
        this.artikelName = artikelName;
        this.einheit = einheit;
        this.einheitProBestellung = einheitProBestellung;
        this.einheitProGlas = einheitProGlas;
        this.einkaufspreis = einkaufspreis;
        this.verkaufspreis = verkaufspreis;
        this.schwund = schwund;
        this.kategorie = kategorie;
        this.order = order;
        this.anteil = anteil;
    }

    public GetraenkeModel(String artikelName) {
        super();
        this.artikelName = artikelName;
    }

    public GetraenkeModel(String artikelName, String kategorie, int order) {
        super();
        this.artikelName = artikelName;
        this.kategorie = kategorie;
        this.order = order;
    }

    public double getAnteil() {
        return anteil;
    }

    public void setAnteil(double anteil) {
        this.anteil = anteil;
    }

    public double getSchwund() {
        return schwund;
    }

    public void setSchwund(double schwund) {
        this.schwund = schwund;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getArtikelName() {
        return artikelName;
    }

    public void setArtikelName(String artikelName) {
        this.artikelName = artikelName;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public double getEinheitProBestellung() {
        return einheitProBestellung;
    }

    public void setEinheitProBestellung(double einheitProBestellung) {
        this.einheitProBestellung = einheitProBestellung;
    }

    public double getEinheitProGlas() {
        return einheitProGlas;
    }

    public void setEinheitProGlas(double einheitProGlas) {
        this.einheitProGlas = einheitProGlas;
    }

    public double getEinkaufspreis() {
        return einkaufspreis;
    }

    public void setEinkaufspreis(double einkaufspreis) {
        this.einkaufspreis = einkaufspreis;
    }

    public double getVerkaufspreis() {
        return verkaufspreis;
    }

    public void setVerkaufspreis(double verkaufspreis) {
        this.verkaufspreis = verkaufspreis;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}


