package org.example;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    Shop shop = new Shop();

    @org.junit.jupiter.api.Test
    void getKleidungspreis() {
        shop.cb_kleidungAuswahl.setSelectedItem("Hose");
        assertEquals("Hose", shop.getAusgewaehlteKleidung());
    }

    @org.junit.jupiter.api.Test
    void getFarbenpreis() {
        shop.radio_grün.setSelected(true);
        assertEquals("grün", shop.getAusgewaehlteFarbe());
    }
}