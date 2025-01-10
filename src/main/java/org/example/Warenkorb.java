package org.example;

import java.util.ArrayList;

public class Warenkorb {

    ArrayList<Ware> waren = new ArrayList <> ();

    public Ware add(String kleidung, String farbe, String groesse) {

        Ware w = new Ware (kleidung,farbe,groesse);

        waren.add(w);

        return w;
    }

    public void remove(Ware ware) {

        waren.remove(ware);
    }

    public void clear() {

    }
}
