package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class Shop extends JFrame {
    private JLabel lbl_auswahl;
    private JPanel frame;
    public JComboBox combo_auswahl;
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
    private JTextArea textArea;
    private JButton bt_löschen;
    private JLabel lbl_preis;
    private JTextField tf_preis;
    private JButton bt_einkaufen;
    private JCheckBox check_rabatt;

    private Warenkorb warenkorb = new Warenkorb();
    private double endPreis;

    private ButtonGroup buttonGroupFarbe = new ButtonGroup();

    private boolean mitRabatt = false;

    private ButtonGroup buttonGroupGroesse = new ButtonGroup();
    private static double rabattRate = 0.5;


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



        bt_hinzufügen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String kleidung = getAusgewaehlteKleidung();
                    String groesse = getAusgewaehlteGroesse();
                    String farbe = getAusgewaehlteFarbe();

                    if (kleidung.equals("-")){
                        throw new Exception("Bitte wähle dein Kleidungsstück");
                    }
                    if (groesse == null) {
                        throw new Exception("Bitte wähle deine Größe");
                    }
                    if (farbe == null) {
                        throw new Exception("Bitte wähle eine Farbe");
                    }

                    hinzufuegen(kleidung, groesse, farbe);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame,exception.getMessage());
                }

                buttonGroupFarbe.clearSelection();
                buttonGroupGroesse.clearSelection();
                combo_auswahl.setSelectedItem("-");
            }
        });

        bt_löschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWarenkorb();
            }
        });

        bt_einkaufen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                einkaufen();
                clearWarenkorb();
            }
        });

        initObjekte();
        check_rabatt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mitRabatt = check_rabatt.isSelected();
                updatePreistext();
            }
        });
    }

    private void initObjekte() {
        hinzufuegen("Tshirt","S"," rot");
        hinzufuegen("Hose", "M", " blau");
        hinzufuegen("Cap", "L", " pink");
    }

    public String getAusgewaehlteKleidung() {
        return combo_auswahl.getSelectedItem().toString();
    }

    public String getAusgewaehlteFarbe() {
        for (AbstractButton b : Collections.list(buttonGroupFarbe.getElements())) {
            if (b.isSelected()) {
                return b.getText();
            }
        }
        return null;
    }

    public String getAusgewaehlteGroesse() {
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
        check_rabatt.setSelected(false);

    }

    private void einkaufen() {
        JOptionPane.showMessageDialog(frame, "Danke für ihren Einkauf!");
        clearWarenkorb();
    }

    private void generierenWarenkorbItem(String text) {

        textArea.append(text);
        textArea.append("\n");
           
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
        double preis = endPreis;
        if (mitRabatt){
            preis = preis * rabattRate;
        }
        preis = (Math.ceil(preis * 100)) / 100;//Runden auf zwei Kommastellen
        tf_preis.setText(preis + " €");
    }



    public static void main(String[] args) {
        new Shop();

    }
}



