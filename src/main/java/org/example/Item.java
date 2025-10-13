package org.example;

public class Item {

    private final String name;
    private final Money price;
    private final ItemType itemType;

    public Item (String name, ItemType itemType, Money price) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if(itemType == null) {
            throw new IllegalArgumentException("Item type cannot be null");
        }

        if(price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if(price.getAmountInMinorUnit() <= 0) {
            throw new IllegalArgumentException("Price cannot be zero or negative");
        }

        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Money getPrice() {
        return price;
    }

    public Money getPriceWithTax() {
        return price.multiply((1 + itemType.getTaxRate()));
    }

}
