package org.example;

import javax.swing.*;
import java.util.Collections;

public class Shop extends JFrame {
    private JLabel lbl_auswahl;
    private JPanel frame;
    private JComboBox combo_auswahl;
    private JLabel lbl_farbe;
    private JRadioButton radio_rot;
    private JRadioButton radio_grün;
    private JRadioButton radio_blau;
    private JRadioButton radio_gelb;
    private JRadioButton radio_schwarz;
    private JRadioButton radio_pink;
    private JLabel lbl_größe;
    private JRadioButton radio_s;
    private JRadioButton radio_m;
    private JRadioButton radio_l;
    private JButton bt_hinzufügen;
    private JLabel lbl_warenkorb;
    private JTextArea textArea;
    private JButton bt_delete;
    private JLabel lbl_preis;
    private JTextField tf_preis;
    private JButton bt_einkaufen;
    private JCheckBox ckeck_rabatt;


    private Warenkorb warenkorb;
    private double endPreis;

    private ButtonGroup buttonGroupFarbe = new ButtonGroup();

    private ButtonGroup buttonGroupGroesse = new ButtonGroup();


    public Shop() {

        buttonGroupFarbe.add(radio_rot);
        buttonGroupFarbe.add(radio_gelb);
        buttonGroupFarbe.add(radio_schwarz);
        buttonGroupFarbe.add(radio_pink);
        buttonGroupFarbe.add(radio_blau);
        buttonGroupFarbe.add(radio_grün);

        buttonGroupGroesse.add(radio_s);
        buttonGroupGroesse.add(radio_m);
        buttonGroupGroesse.add(radio_l);


        setTitle("Simple Shop");
        setContentPane(frame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setVisible(true);
        setResizable(false);
    }

    private void initObjekte() {
        hinzufuegen("Tshirt","S"," rot");
        hinzufuegen("Hose", "M", " blau");
        hinzufuegen("Cap", "L", " pink");

    }


    private String getAusgewaehlteKleidung() {
        return combo_auswahl.getSelectedItem().toString();
    }

    private String getAusgewaehlteFarbe() {
        for (AbstractButton b : Collections.list(buttonGroupFarbe.getElements())) {
            if (b.isSelected()) {
                return b.getText();
            }
        }
        return null;
    }

    private String getAusgewaehlteGroesse() {
        for (AbstractButton g : Collections.list(buttonGroupGroesse.getElements())){
            if (g.isSelected())
                return g.getText();
        }
        return null;
    }


    private void hinzufuegen(String kleidung, String farbe, String groesse) {

        warenkorb.add(kleidung,farbe,groesse);

        double kleidungspreis = getKleidungspreis(kleidung) + getFarbenpreis(farbe) + getGroessenpreis(groesse);
        endPreis += kleidungspreis;

        generierenWarenkorbItem(kleidung +" (" + groesse + ") " + farbe + "  :" + kleidungspreis + "€");
        updatePreistext();

    }

    private void clearWarenkorb() {
        warenkorb.clear();
        endPreis = 0;
        updatePreistext();
        textArea.setText("");
    }

    private void einkaufen() {
        JOptionPane.showMessageDialog(frame, "Danke für ihren Einkauf!");
        clearWarenkorb();
    }

    private void generierenWarenkorbItem(String text) {

    }
    public double getKleidungspreis (String kleidung){
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

    public double getFarbenpreis (String farbe){
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

    public double getGroessenpreis(String groesse){
        // Preiszuschlag für versch. Größen festsetzen

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

    private void updatePreistext() {

    }


    public static void main(String[] args) {
        new Shop();

    }
}



