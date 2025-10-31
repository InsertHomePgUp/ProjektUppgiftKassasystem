package org.example;

import java.util.Objects;

public class Item {
    private final String name;
    private Money price;
    private final ItemType itemType;

    public Item (String name, ItemType itemType, Money price) {
        validateName(name);
        validateItemType(itemType);
        validatePrice(price);
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

    public void setDiscount(double percent) {
        double fraction = 1 - (percent / 100); //To get fraction
        price = new Money(SEK.instance, price.multiply(fraction).getAmountInMinorUnit());
    }

    public Money getPrice() {
        return price;
    }

    public Money getDeposit() {
        return getItemType().getDeposit();
    }

    public Money getPriceWithTax() {
        return price.multiply((1 + itemType.getTaxRate()));
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(other == null || other.getClass() != getClass()) {
            return false;
        }
        Item item = (Item) other;
        return name.equals(item.name) && itemType.equals(item.itemType) && price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, itemType, price);
    }

    @Override
    public String toString () {
        return String.format("%s, %s, %s", name, itemType.getName(), price.toString());
    }

    private void validateName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    private void validateItemType(ItemType itemType) {
        if(itemType == null) {
            throw new IllegalArgumentException("Item type cannot be null");
        }
    }

    private void validatePrice(Money price) {
        if(price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        } else if (price.getAmountInMinorUnit() <= 0) {
            throw new IllegalArgumentException("Price cannot be zero or negative");

        }
    }
}
