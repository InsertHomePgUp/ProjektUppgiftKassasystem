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
        Money negativePrice = new Money(SEK.instance, -500);
        assertThrows(IllegalArgumentException.class, () -> new Item(testName, itemType, negativePrice));
    }

    @Test
    void priceWithTaxCalculatedCorrectly() {
        Item item = new Item(testName, itemType, price);
        assertEquals(11500L, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void priceWithZeroTaxCalculatedCorrectly() {
        ItemType itemTypeZeroTax = new ItemType("Test", 0.0, deposit, 0);
        Item item = new Item(testName, itemTypeZeroTax, price);
        assertEquals(10000L, item.getPriceWithTax().getAmountInMinorUnit());
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

}
