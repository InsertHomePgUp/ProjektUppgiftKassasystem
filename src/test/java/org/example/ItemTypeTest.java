package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTypeTest {

    @Test
    void constructorTest() {
        ItemType test = new ItemType("Test", 15.0, 2, 15);
        assertEquals("Test", test.getName());
        assertEquals(15.0, test.getTaxRate());
        assertEquals(2, test.getDeposit());
        assertEquals(15, test.getAgeLimit());
    }

    @Test
    void cannotCreateItemTypeWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("", 15.0, 0, 0);
        });
    }

    @Test
    void cannotCreateItemTypeWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType(null, 15.0, 0 ,0);
        });
    }

    @Test
    void taxRateIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("test", -5.0, 0, 0);
        });
    }

    @Test
    void depositIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("test", 15.0, -2, 0);
        });
    }

    @Test
    void ageLimitIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("test", 15.0, 2, -15);
        });
    }
}
