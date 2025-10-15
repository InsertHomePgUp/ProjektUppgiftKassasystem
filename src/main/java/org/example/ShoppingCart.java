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
        if (cart.contains(item)){
            cart.remove(item);
        }else{
            throw new IllegalArgumentException("item not found in cart");
        }
    }

    public List<Item> viewCart(){
        return cart;
    }
}
