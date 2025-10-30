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
    } //Kan aldrig vara negativ eftersom det bestäms i money

    @Test
    void depositIsNotOver5Kr() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 15.0, new Money(SEK.instance, 600), 0));
    }

    @Test
    void ageLimitIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 15.0, deposit, -15));
    }

    @Test
    void taxRateAbove100ThrowsException () {
        assertThrows(IllegalArgumentException.class, () -> new ItemType(testItemTypeName, 100.0, deposit, 0));
    }

    @Test
    void equalsTestSameObject() {
        ItemType itemType = new ItemType("Test", 15.0, new Money(SEK.instance, 200), 0);
        assertEquals(itemType, itemType);
    }

    @Test
    void equalsTestNull() {
        ItemType itemType = new ItemType("Test", 15.0, new Money(SEK.instance, 200), 0);
        assertFalse(itemType.equals(null));
    }

    @Test
    void equalsTestDifferentClass() {
        ItemType itemType = new ItemType("Test", 15.0, new Money(SEK.instance, 200), 0);
        Item item = new Item("Test", itemType, new Money(SEK.instance, 500));
        assertFalse(itemType.equals(item));

    }

    @Test
    void equalsTestDifferentValues() {
        //Different name
        ItemType itemTypeA = new ItemType("A", 15.0, new Money(SEK.instance, 200), 0);
        ItemType itemTypeB = new ItemType("B", 15.0, new Money(SEK.instance, 200), 0);
               assertNotEquals(itemTypeA, itemTypeB);

        //Different taxRate
        ItemType itemTypeC = new ItemType("A", 14.0, new Money(SEK.instance, 200), 0);
        assertNotEquals(itemTypeA, itemTypeC);

        //Different deposit
        ItemType itemTypeD = new ItemType("A", 15.0, new Money(SEK.instance, 100), 0);
        assertNotEquals(itemTypeA, itemTypeD);

        //Different ageLimit
        ItemType itemTypeE = new ItemType("A", 15.0, new Money(SEK.instance, 200), 1);
        assertNotEquals(itemTypeA, itemTypeE);
    }

    @Test
    void equalsTestDifferentInstance() {
        ItemType itemTypeA = new ItemType("A", 15.0, new Money(SEK.instance, 200), 0);
        ItemType itemTypeB = new ItemType("A", 15.0, new Money(SEK.instance, 200), 0);

        assertEquals(itemTypeA, itemTypeB);
    }

    @Test
    void hashcodeIsConsistent() {
        ItemType itemType = new ItemType("Test", 15.0, new Money(SEK.instance, 200), 0);
        int hash1 = itemType.hashCode();
        int hash2 = itemType.hashCode();

        assertEquals(hash1, hash2);
    }


}
