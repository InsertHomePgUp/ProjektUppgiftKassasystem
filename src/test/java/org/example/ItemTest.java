package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private ItemType itemType;
    private Money price;
    private Money deposit;
    private final String testName = "TestItem";


    @BeforeEach
    void createItemTypeAndMoney() {
        price = new Money(SEK.instance, 10000);
        deposit = new Money(SEK.instance, 200);
        itemType = new ItemType("TestItemType", 15.0, deposit, 0);
    }

    @Test
    void constructorTest() {
        Item item = new Item(testName, itemType, price);
        assertEquals(testName, item.getName());
        assertEquals(itemType, item.getItemType());
        assertEquals(price, item.getPrice());
    }

    @Test
    void cannotCreateItemWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new Item("", itemType, price));
    }

    @Test
    void cannotCreateItemWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Item(null,itemType, price));
    }

    @Test
    void cannotCreateItemWithNullItemType() {
        assertThrows(IllegalArgumentException.class, () -> new Item(testName, null, price));
    }

    @Test
    void cannotCreateItemWithNullPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Item(testName, itemType, null));
    }

    @Test
    void cannotCreateItemThatCostNothing() {
        Money priceIsZero = new Money(SEK.instance, 0);
        assertThrows(IllegalArgumentException.class, () -> new Item(testName, itemType, priceIsZero));
    }

    @Test
    void cannotCreateItemWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Item(testName, itemType, new Money(SEK.instance, -500)));
    }

    @Test
    void priceWithTaxCalculatedCorrectly() {
        Item item = new Item(testName, itemType, price);
        assertEquals(11500, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void priceWithZeroTaxCalculatedCorrectly() {
        ItemType itemTypeZeroTax = new ItemType("Test", 0.0, deposit, 0);
        Item item = new Item(testName, itemTypeZeroTax, price);
        assertEquals(10000, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void priceWithFractionalTaxCalculatedCorrectly() {
        ItemType itemTypeFractionalTax = new ItemType("Test", 12.5, deposit, 0);
        Item item = new Item(testName, itemTypeFractionalTax, price);
        assertEquals(11250, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void depositNotIncludedInPrice() {
        Item item = new Item(testName, itemType, price);
        assertEquals(price, item.getPrice());
        assertEquals(itemType.getDeposit(), item.getDeposit());
    }

    @Test
    void toStringPrintsProperly() {
        Item itemOne = new Item(testName, itemType, price);
        Item itemTwo = new Item(testName, itemType, new Money(SEK.instance, 1250));
        assertEquals("TestItem, TestItemType, 100,00 kr", itemOne.toString());
        assertEquals("TestItem, TestItemType, 12,50 kr", itemTwo.toString());
    }

    @Test
    void setDiscountTest() {
        Item item = new Item(testName, itemType, price);
        assertEquals(price, item.getPrice());
        item.setDiscount(12.5);
        assertEquals(8750, item.getPrice().getAmountInMinorUnit());
    }

    @Test
    void equalsTestSameObject() {
        Item item = new Item(testName, itemType, price);
        assertEquals(item, item);
    }

    @Test
    void equalsTestNull() {
        Item item = new Item(testName, itemType, price);
        assertFalse(item.equals(null));
    }

    @Test
    void equalsTestDifferentClass() {
        Item item = new Item(testName, itemType, price);
        ItemType itemType = new ItemType("Test", 15.0, new Money(SEK.instance, 100), 0);
        assertNotEquals(item, itemType);
    }

    @Test
    void equalsTestDifferentValues() {
        //Different name
        Item itemA = new Item("A", itemType, price);
        Item itemB = new Item("B", itemType, price);
        assertNotEquals(itemA, itemB);

        //Different ItemTyoe
        Item itemC = new Item("A", new ItemType("Test", 15.0, new Money(SEK.instance, 0), 0), price);
        assertNotEquals(itemA, itemC);

        //Different price
        Item itemD = new Item("A", itemType, new Money(SEK.instance, 12345));
        assertNotEquals(itemA, itemD);
    }

    @Test
    void equalsTestDifferentInstance() {
        Item itemA = new Item("A", itemType, price);
        Item itemB = new Item("A", itemType, price);

        assertEquals(itemA, itemB);
    }

    @Test
    void hashcodeIsConsistent() {
        Item itemA = new Item(testName, itemType, price);
        int hash1 = itemA.hashCode();
        int hash2 = itemA.hashCode();

        assertEquals(hash1, hash2);
    }
}
