package org.example;

public class Item {

    private final String name;
    private final Money price;
    private final ItemType itemType;

    public Item (String name, ItemType itemType, Money price) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

}
