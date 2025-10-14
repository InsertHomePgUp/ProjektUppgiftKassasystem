package org.example;

import java.util.*;

public class ShoppingCart {

    private List<Item> cart;

    public ShoppingCart() {
        cart = new ArrayList<>();
    }

    public void addItem(Item item){
        cart.add(item);
    }

    public void removeItem(Item item){
        cart.remove(item);
    }

    public List<Item> viewCart(){
        return cart;
    }
}
