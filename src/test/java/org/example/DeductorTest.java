package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testConstructorAndGetters() {
        Deductor d = new Deductor(100.0, "rabatt");
        assertEquals(100.0, d.getAmount());
        assertEquals("Rabatt", d.getType());
    }

    @Test
    void testLowerAmount() {
        Deductor d = new Deductor(100.0, "bonuscheck");
        d.lowerAmount(30.0);
        assertEquals(70.0, d.getAmount());
    }

    @Test
    void testRaiseAmount() {
        Deductor d = new Deductor(50.0, "presentkort");
        d.raiseAmount(25.0);
        assertEquals(75.0, d.getAmount());
    }

    @Test
    void testToString() {
        Deductor d = new Deductor(200.0, "rabatt");
        assertEquals("Rabatt200.0", d.toString());
    }

    @Test
    void testMultipleOperations() {
        Deductor d = new Deductor(100.0, "Bonuscheck");
        d.lowerAmount(20.0);
        d.raiseAmount(50.0);
        assertEquals(130.0, d.getAmount());
    }

    @Test
    void typeNotValid(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Deductor(100, "Alfred");
        });
    }

    @Test
    void negativeAmountBecomes0(){
        Deductor d = new Deductor(100.0, "Bonuscheck");
        d.lowerAmount(200.0);
        assertEquals(0.0, d.getAmount());
    }

    @Test
    void raisingToNegativeAmountBecomes0(){
        Deductor d = new Deductor(100.0, "Bonuscheck");
        d.raiseAmount(-200.0);
        assertEquals(0.0, d.getAmount());
    }
}
