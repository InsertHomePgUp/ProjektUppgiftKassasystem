package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemGroup {
    private final String name;
    private final List<Item> items = new ArrayList<>();

    public ItemGroup(String name) {
        validateName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return items.size();
    }

    public void addItem(Item item) {
        validateItem(item);
        if (items.contains(item)) {
            throw new IllegalArgumentException("Item is already in this group");
        } else {
            items.add(item);
        }
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public void removeItem(Item item) {
        validateItem(item);
        if(!items.contains(item)) {
            throw new IllegalArgumentException("Item is not in this group");
        } else {
            items.remove(item);
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setGroupDiscount(double percent) {
        for(Item i : items) {
            i.setDiscount(percent);
        }
    }

    public void setGroupDiscount(double percent, ItemType itemType) {
        for(Item i : items) {
            if(i.getItemType().equals(itemType)) {
                i.setDiscount(percent);
            }
        }
    }

    private void validateName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    private void validateItem(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }
}
