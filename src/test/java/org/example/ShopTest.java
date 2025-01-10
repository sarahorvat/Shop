package org.example;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    Shop shop = new Shop();

    @org.junit.jupiter.api.Test
    void getKleidungspreis() {
        shop.combo_auswahl.setSelectedItem("Hose");
        assertEquals("Hose", shop.getAusgewaehlteKleidung());
    }

    @org.junit.jupiter.api.Test
    void getFarbenpreis() {
        shop.radio_grün.setSelected(true);
        assertEquals("grün", shop.getAusgewaehlteFarbe());
    }

    @org.junit.jupiter.api.Test
    void getGroessenpreis() {
        shop.radio_m.setSelected(true);
        assertEquals("M", shop.getAusgewaehlteGroesse());
    }
}