package org.example;

import java.util.Map;
import java.util.HashMap;

public class MockScanner {

    private final Map<String, Item> itemByCode = new HashMap<>();

    public MockScanner() {
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));
        Item cider = new Item("Cider", alcohol, new Money(SEK.instance, 2200));

        ItemType energyDrink = new ItemType("Energy drink", 12.5, new Money(SEK.instance, 100), 15);
        Item nocco = new Item("Nocco", energyDrink, new Money(SEK.instance, 2100));
        Item redBull = new Item("Red Bull", energyDrink, new Money(SEK.instance, 1800));


        ItemType sodaBigBottle = new ItemType("Soda", 10.0, new Money(SEK.instance, 200), 0);
        Item cocaCola = new Item("Coca Cola", sodaBigBottle, new Money(SEK.instance, 2400));
        Item sprite = new Item("Sprite", sodaBigBottle, new Money(SEK.instance, 2200));

        itemByCode.put("1", beer);
        itemByCode.put("2", cider);
        itemByCode.put("3", nocco);
        itemByCode.put("4", redBull);
        itemByCode.put("5", cocaCola);
        itemByCode.put("6", sprite);

    }

    public Item scanBarcode(String barcode) {
        if(barcode == null || barcode.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Item item = itemByCode.get(barcode);

        if(item == null) {
            throw new IllegalArgumentException("Barcode " + barcode + " is not in database");
        }

        return item;
    }
}
