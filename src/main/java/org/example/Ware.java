package org.example;

public class Ware {
    //Festlegung der Attribute für Objekte
    public String kleidung;
    public String farbe;
    public String groesse;

    //Konstruktor: übergibt parameter Werte auf eigene Attribute
    public Ware(String kleidung, String farbe, String groesse) {
        this.kleidung = kleidung;
        this.farbe = farbe;
        this.groesse = groesse;
    }
}
