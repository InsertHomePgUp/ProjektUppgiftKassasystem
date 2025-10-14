package org.example;

import java.util.*;

public class ShoppingCart {

    private TreeSet<Item> cart;

    public ShoppingCart() {
        cart = new TreeSet<>();
    }

    public void addItem(Item item){
        cart.add(item);
    }

    public void removeItem(Item item){
        cart.remove(item);
    }

    public Set<Item> viewCart(){
        return cart;
    }
}
