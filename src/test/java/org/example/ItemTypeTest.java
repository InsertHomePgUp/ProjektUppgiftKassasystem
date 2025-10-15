package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTypeTest {

    private Currency currency;
    private Money deposit;

    @BeforeEach
    void createMoneyAndCurrency() {
        currency = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        deposit = new Money(currency, 200);
    }

    @Test
    void constructorTest() {
        ItemType test = new ItemType("ItemTypeName", 15.0, deposit, 15);
        assertEquals("ItemTypeName", test.getName());
        assertEquals(0.15, test.getTaxRate());
        assertEquals(2, test.getDeposit());
        assertEquals(15, test.getAgeLimit());
    }

    @Test
    void cannotCreateItemTypeWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("", 15.0, deposit, 0);
        });
    }

    @Test
    void cannotCreateItemTypeWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType(null, 15.0, deposit ,0);
        });
    }

    @Test
    void taxRateIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("ItemTypeName", -5.0, deposit, 0);
        });
    }

    @Test
    void depositIsNotNegative() {
        Money negativeDeposit = new Money(currency, -2);
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("ItemTypeName", 15.0, negativeDeposit, 0);
        });
    }

    @Test
    void ageLimitIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemType("ItemTypeName", 15.0, deposit, -15);
        });
    }
}
