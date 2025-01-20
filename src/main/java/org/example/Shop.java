package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;


public class Shop extends JFrame {
    // Swing objects...
    private JLabel lbl_auswahl;
    private JPanel frame;
    public JComboBox cb_kleidungAuswahl;
    private JLabel lbl_farbe;
    private JRadioButton radio_rot;
    public JRadioButton radio_grün;
    private JRadioButton radio_blau;
    private JRadioButton radio_gelb;
    private JRadioButton radio_schwarz;
    private JRadioButton radio_pink;
    private JLabel lbl_größe;
    private JRadioButton radio_s;
    public JRadioButton radio_m;
    private JRadioButton radio_l;
    private JButton bt_hinzufügen;
    private JLabel lbl_warenkorb;
    private JTextArea ta_warenkorb;
    private JButton bt_löschen;
    private JLabel lbl_preis;
    private JTextField tf_preis;
    private JButton bt_einkaufen;
    private JCheckBox check_rabatt;

    // Attribute
    // Übersicht der Preise und Waren
    private Warenkorb warenkorb = new Warenkorb();
    private double endPreis;
    private boolean mitRabatt = false;
    private static double rabattRate = 0.5;

    // Manuelle Buttongroups um auf sie zugreifen zu können
    private ButtonGroup buttonGroupFarbe = new ButtonGroup();
    private ButtonGroup buttonGroupGroesse = new ButtonGroup();



    public Shop() {

        // Hinzufügen zu Buttongroups:
        buttonGroupFarbe.add(radio_rot);
        buttonGroupFarbe.add(radio_gelb);
        buttonGroupFarbe.add(radio_schwarz);
        buttonGroupFarbe.add(radio_pink);
        buttonGroupFarbe.add(radio_blau);
        buttonGroupFarbe.add(radio_grün);

        buttonGroupGroesse.add(radio_s);
        buttonGroupGroesse.add(radio_m);
        buttonGroupGroesse.add(radio_l);

        // Anzeige optimieren
        setTitle("Shop");
        setContentPane(frame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setVisible(true);
        setResizable(false);



        bt_hinzufügen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String kleidung = getAusgewaehlteKleidung();
                    String groesse = getAusgewaehlteGroesse();
                    String farbe = getAusgewaehlteFarbe();

                    // Falls etwas nicht ausgewählt ist, wird Exception geworfen
                    if (kleidung.equals("-")){
                        throw new Exception("Bitte wähle dein Kleidungsstück");
                    }
                    if (groesse == null) {
                        throw new Exception("Bitte wähle deine Größe");
                    }
                    if (farbe == null) {
                        throw new Exception("Bitte wähle eine Farbe");
                    }

                    hinzufuegen(kleidung, farbe, groesse);
                } catch (Exception exception) {
                    // Neues Fenster öffnen zur Fehlermeldung
                    JOptionPane.showMessageDialog(frame,exception.getMessage());
                }

                resetAuswahl();
            }
        });

        // Warenkorb leeren durch "löschen"
        bt_löschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWarenkorb();
            }
        });

        // Warenkorb leeren nach abgeschlossenem Einkauf
        bt_einkaufen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                einkaufen();
            }
        });

        // Rabattaktion hinzufügen
        check_rabatt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mitRabatt = check_rabatt.isSelected();
                updatePreistext();
            }
        });

        initObjekte();
    }

    private void initObjekte() {
        // Fügt automatisch drei Objekte zum Warenkorb hinzu
        hinzufuegen("Tshirt","rot","S");
        hinzufuegen("Hose", "blau", "M");
        hinzufuegen("Cap", "pink", "L");

    }


    public String getAusgewaehlteKleidung() {
        // Kleidungsstück wird aus ComboBox ausgewählt
        return cb_kleidungAuswahl.getSelectedItem().toString();
    }

    public String getAusgewaehlteFarbe() {
        // Quelle für Collections.list: https://stackoverflow.com/questions/1240077/why-cant-i-use-foreach-on-java-enumeration
        // Geht durch jeden Button in der Farben-ButtonGroup und prüft, ob dieser ausgewählt ist
        for (AbstractButton b : Collections.list(buttonGroupFarbe.getElements())) {
            // Wenn ausgewählt, wird Text des Buttons zurückgegeben
            if (b.isSelected()) {
                return b.getText();
            }
        }
        return null;
    }

    public String getAusgewaehlteGroesse() {
        // Quelle für Collections.list: https://stackoverflow.com/questions/1240077/why-cant-i-use-foreach-on-java-enumeration
        // Geht durch jeden Button in der Größen-ButtonGroup und prüft, ob dieser ausgewählt ist
        for (AbstractButton b : Collections.list(buttonGroupGroesse.getElements())){
            // Wenn ausgewählt, wird Text des Buttons zurückgegeben
            if (b.isSelected())
                return b.getText();
        }
        return null;
    }



    private String getKassenzettel() {
        // Ausgabe der gekauften Waren plus Summe
        // Quelle:  https://stackoverflow.com/questions/6431933/how-to-format-strings-in-java
        // und:     https://stackoverflow.com/questions/433958/java-decimal-formatting-using-string-format

        String result = "gekaufte Ware: \n";
        for (Ware w : warenkorb.waren){
            result += String.format(" %s, %s, %s\n", w.kleidung, w.farbe, w.groesse);
        }
        result += String.format("Summe: %.2f €", getPreismitRabatt());
        return result;
    }


    private void hinzufuegen(String kleidung, String farbe, String groesse) {
        // Fügt Objekte zum Warenkorb hinzu
        warenkorb.add(kleidung,farbe,groesse);

        // Berechnung von Preis und Addition zum Gesamtpreis
        double kleidungspreis = getKleidungspreis(kleidung) + getFarbenpreis(farbe) + getGroessenpreis(groesse);
        endPreis += kleidungspreis;

        // Updated Shop Fenster
        // Quelle:  https://stackoverflow.com/questions/6431933/how-to-format-strings-in-java
        // und:     https://stackoverflow.com/questions/433958/java-decimal-formatting-using-string-format
        generierenWarenkorbItem(String.format("%s (%s) %s: %.2f €", kleidung, groesse, farbe, kleidungspreis));
        updatePreistext();
    }

    private void einkaufen() {
        // Neues Fenster wird geöffnet mit Benachrichtigung
        JOptionPane.showMessageDialog(frame, getKassenzettel(), "Danke für Ihren Einkauf!", JOptionPane.PLAIN_MESSAGE);
        clearWarenkorb();
    }

    private void clearWarenkorb() {
        // Alles wird auf null gesetzt
        warenkorb.clear();
        ta_warenkorb.setText("");

        endPreis = 0;
        updatePreistext();

        check_rabatt.setSelected(false);
        mitRabatt = false;
    }

    private void resetAuswahl(){
        // Löscht Inhalt des Warenkorbs und Anzeige
        buttonGroupFarbe.clearSelection();
        buttonGroupGroesse.clearSelection();
        cb_kleidungAuswahl.setSelectedItem("-");
    }

    private void generierenWarenkorbItem(String text) {
        // Text und linebreak der "Warenkorb"-TextArea hinzufügen
        ta_warenkorb.append(text);
        ta_warenkorb.append("\n");
    }



    private double getKleidungspreis (String kleidung){
        // Grundpreis für mögliche Klamotten festsetzen
        switch (kleidung){
            case "Jacke":
                return 39.99;
            case "Cap":
                return 12.99;
            case "Tshirt":
                return 14.99;
            case "Hose":
                return 34.99;
            case "Pullover":
                return 23.99;
        }
        return 0;
    }

    private double getFarbenpreis (String farbe){
        // Preiszuschlag für versch. Farben festsetzen
        switch (farbe){
            case "rot":
                return 4;
            case "blau":
                return 1;
            case "grün":
                return 2;
            case "pink":
                return 4;
            case "schwarz":
                return 1;
            case "gelb":
                return 1;
        }
        return 0;
    }

    private double getGroessenpreis(String groesse){
        // Preiszuschlag für verschiedene Größen festsetzen
        switch (groesse){
            case "S":
                return 1;
            case "M":
                return 2;
            case "L":
                return 3;
        }
        return 0;
    }


    private double getPreismitRabatt(){
        // Rabattrate hinzufügen, falls aktiviert
        double preis = endPreis;
        if (mitRabatt) {
            preis = preis * rabattRate;
        }
        return preis;
    }

    private void updatePreistext() {
        double preis = getPreismitRabatt();

        // Runden auf zwei Nachkommastellen
        preis = (Math.ceil(preis * 100)) / 100;

        // Ausgabe im Preistextfeld
        tf_preis.setText(preis + " €");
    }


    public static void main(String[] args) {
        new Shop();
    }
}



