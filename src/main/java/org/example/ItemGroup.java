package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemGroup {
    private final String name;
    private final List<Item> items = new ArrayList<>();

    public ItemGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return items.size();
    }

    public void addItem(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if(items.contains(item)) {
            throw new IllegalArgumentException("Item is already in this group");
        }
        items.add(item);
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public void removeItem(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if(items.contains(item)) {
            items.remove(item);
        } else {
            throw new IllegalArgumentException("Item is not in this group");
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
