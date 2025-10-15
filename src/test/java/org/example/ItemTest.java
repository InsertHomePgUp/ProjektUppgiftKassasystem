package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private ItemType itemType;
    private Currency currency;
    private Money price;
    private Money deposit;


    @BeforeEach
    void createItemTypeAndMoney() {
        currency = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        price = new Money(currency, 10000);
        deposit = new Money(currency, 200);
        itemType = new ItemType("TestItemType", 15.0, deposit, 0);
    }

    @Test
    void constructorTest() {
        Item item = new Item("TestItem", itemType, price);
        assertEquals("TestItem", item.getName());
        assertEquals(itemType, item.getItemType());
        assertEquals(price, item.getPrice());
    }

    @Test
    void cannotCreateItemWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("", itemType, price);
        });
    }

    @Test
    void cannotCreateItemWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item(null,itemType, price);
        });
    }

    @Test
    void cannotCreateItemWithNullItemType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("TestItem", null, price);
        });
    }

    @Test
    void cannotCreateItemWithNullPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("TestItem", itemType, null);
        });
    }

    @Test
    void cannotCreateItemThatCostNothing() {
        Money priceIsZero = new Money(currency, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("TestItem", itemType, priceIsZero);
        });
    }

    @Test
    void cannotCreateItemWithNegativePrice() {
        Money negativePrice = new Money(currency, -500);
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("TestItem", itemType, negativePrice);
        });
    }

    @Test
    void priceWithTaxCalculatedCorrectly() {
        Item item = new Item("TestItem", itemType, price);
        assertEquals(11500L, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void priceWithZeroTaxCalculatedCorrectly() {
        ItemType itemTypeZeroTax = new ItemType("Test", 0.0, deposit, 0);
        Item item = new Item("TestItem", itemTypeZeroTax, price);
        assertEquals(10000L, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void priceWithFractionalTaxCalculatedCorrectly() {
        ItemType itemTypeZeroTax = new ItemType("Test", 12.5, deposit, 0);
        Item item = new Item("TestItem", itemTypeZeroTax, price);
        assertEquals(11250L, item.getPriceWithTax().getAmountInMinorUnit());
    }

    @Test
    void toStringPrintsProperly() {
        Item itemOne = new Item("TestItem", itemType, price);
        Item itemTwo = new Item("TestItem", itemType, new Money(currency, 1250));
        assertEquals("TestItem, TestItemType, 100,00 kr", itemOne.toString());
        assertEquals("TestItem, TestItemType, 12,50 kr", itemTwo.toString());
    }
}
