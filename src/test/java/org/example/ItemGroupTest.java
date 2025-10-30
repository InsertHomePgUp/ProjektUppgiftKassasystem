package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemGroupTest {
    private ItemGroup itemGroup;
    private ItemType itemType;
    private Money price;

    @BeforeEach
    void createTestData() {
        price = new Money(SEK.instance, 10000);
        Money deposit = new Money(SEK.instance, 200);
        itemGroup = new ItemGroup("TestItemGroup");
        itemType = new ItemType("TestItemType", 15.0, deposit, 0);
    }

    @Test
    void constructorTest() {
        assertEquals("TestItemGroup", itemGroup.getName());
    }

    @Test
    void cannotCreateItemGroupWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new ItemGroup(""));
    }

    @Test
    void cannotCreateItemGroupWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new ItemGroup(null));
    }

    @Test
    void newItemGroupIsEmpty() {
        assertEquals(0, itemGroup.getSize());
    }

    @Test
    void getItemsReturnsUnmodifiableList() {
        Item item = new Item("TestItem", itemType, price);
        List<Item> items = itemGroup.getItems();
        assertThrows(UnsupportedOperationException.class, () -> items.add(item));
    }

    @Test
    void addItemTest() {
        Item item = new Item("TestItem", itemType, price);
        assertEquals(0, itemGroup.getSize());
        itemGroup.addItem(item);
        assertEquals(1, itemGroup.getSize());
        assertTrue(itemGroup.contains(item));
    }

    @Test
    void cannotAddDuplicateItems() {
        Item item = new Item("TestItem", itemType, price);
        itemGroup.addItem(item);
        assertThrows(IllegalArgumentException.class, () -> itemGroup.addItem(item));
    }

    @Test
    void addingNullItemThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> itemGroup.addItem(null));
    }

    @Test
    void removeItemTest() {
        Item item = new Item("TestItem", itemType, price);
        itemGroup.addItem(item);
        assertEquals(1, itemGroup.getSize());
        itemGroup.removeItem(item);
        assertEquals(0, itemGroup.getSize());
        assertFalse(itemGroup.contains(item));
    }

    @Test
    void removingNullItemThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> itemGroup.removeItem(null));
    }

    @Test
    void removeItemThatIsNotInList() {
        Item itemOne = new Item("TestItemOne", itemType, price);
        Item itemTwo = new Item("TestItemTwo", itemType, price);
        itemGroup.addItem(itemOne);
        assertThrows(IllegalArgumentException.class, () -> itemGroup.removeItem(itemTwo));
    }

    @Test
    void setGroupDiscountTest() {
        ItemType costumes = new ItemType("Costumes", 10.0, new Money(SEK.instance, 0), 0);
        Item batmanCostume = new Item("Batman costume", costumes, new Money(SEK.instance, 10000));

        ItemType snacks = new ItemType("Snacks", 15.0, new Money(SEK.instance, 0), 0);
        Item batmanSnacks = new Item("Batman snacks", snacks, new Money(SEK.instance, 1500));

        ItemGroup halloweenClearance = new ItemGroup("Halloween clearance");

        halloweenClearance.addItem(batmanCostume);
        halloweenClearance.addItem(batmanSnacks);

        halloweenClearance.setGroupDiscount(30); //30% discount

        assertEquals(7000, batmanCostume.getPrice().getAmountInMinorUnit());
        assertEquals(1050, batmanSnacks.getPrice().getAmountInMinorUnit());

        halloweenClearance.setGroupDiscount(10, costumes); //Extra 10% on costumes

        assertEquals(6300, batmanCostume.getPrice().getAmountInMinorUnit());
    }
}
