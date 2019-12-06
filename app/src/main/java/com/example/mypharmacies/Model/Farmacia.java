package com.example.mypharmacies.Model;
// Atenció: Cal afegir un camp per la informació del Snippet!!! Adreça i telèfon de la farmacia
// codi TEXT PRIMARY KEY, nom TEXT, Lat REAL, Lon REAL, Poblacio TEXT, Foto TEXT,
public class Farmacia {
    private String codi;     private String nom; // 0, 1
    private String poblacio; private float lat; // 2, 3
    private float lon;       private String telefon; // 4, 5
    private String adresa;   private String foto; // 6, 7

    // Constructor
    public Farmacia(String codi, String nom, String poblacio, float lat, float lon,
                    String telefon, String adresa, String foto) {
        super();
        this.codi = codi;       this.nom = nom;         this.poblacio = poblacio;
        this.lat = lat;         this.lon = lon;
        this.telefon = telefon; this.adresa = adresa;   this.foto = foto;
    }
    public Farmacia(){         super();     }
    public String getCodi() {
        return codi;
    }
    public void setCodi(String codi) {
        this.codi = codi;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getLon() {
        return lon;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }
    public String getPoblacio() {
        return poblacio;
    }
    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon (String telefon) {
        this.telefon = telefon;
    }
    public String getAdresa() {
        return adresa;
    }
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}