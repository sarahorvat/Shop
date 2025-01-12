package org.example;

import java.util.ArrayList;

public class Warenkorb {

    // Speichert die Waren in einer dynamischen Liste
    ArrayList<Ware> waren = new ArrayList<>();

    public Ware add(String kleidung, String farbe, String groesse) {
        // Erstellt neues Waren-Objekt, fügt es der Liste hinzu und gibt es zurück
        Ware w = new Ware (kleidung,farbe,groesse);
        waren.add(w);
        return w;
    }

    public void clear() {
        waren.clear();
    }
}
