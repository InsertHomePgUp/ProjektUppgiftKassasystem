package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTypeTest {

    private Money deposit;
    private final String testItemTypeName = "ItemTypeName";

    @BeforeEach
    void createMoneyAndCurrency() {
        deposit = new Money(SEK.instance, 200);
    }

    @Test
    void constructorTest() {
        ItemType test = new ItemType(testItemTypeName, 15.0, deposit, 15);
        assertEquals(testItemTypeName, test.getName());
        assertEquals(0.15, test.getTaxRate());
        assertEquals(2, test.getDeposit().getAmountInMajorUnit());
        assertEquals(15, test.getAgeLimit());
    }

    @Test
    void cannotCreateItemTypeWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType("", 15.0, deposit, 0));
    }

    @Test
    void cannotCreateItemTypeWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(null, 15.0, deposit ,0));
    }

    @Test
    void taxRateIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, -5.0, deposit, 0));
    }

    @Test
    void depositIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 15.0, new Money(SEK.instance, -2), 0));
    }

    @Test
    void ageLimitIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 15.0, deposit, -15));
    }

    @Test
    void taxRateAbove100ThrowsException () {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 100.0, deposit, 0));
    }

}
