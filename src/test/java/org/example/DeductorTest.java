package org.example;

import org.junit.jupiter.api.*;

public class DeductorTest {

    @Test
    void createPresentKort50(){
        Deductor presentkort50 = new Deductor(50, "Presentkort");
    }

    @Test
    void createRabatt25(){
        Deductor rabatt25 = new Deductor(25, "Rabatt");
    }

    @Test
    void createBonuscheck25(){
        Deductor bonuscheck25 = new Deductor(25, "Bonuscheck");
    }
}
