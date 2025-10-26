package org.example;

import java.util.*;

public class ShoppingCart {

    private List<Item> cart;
    private final Scanner scanner;

    public ShoppingCart(Scanner scanner) {
        cart = new ArrayList<>();
        this.scanner = scanner;
    }


    public void addItem(Item item){
        cart.add(item);
    }

    public void scanAndAdd(String barcode) {
        addItem(scanner.scanBarcode(barcode));
    }

    public void removeItem(Item item){
        if (cart.contains(item)){
            cart.remove(item);
        }else{
            throw new IllegalArgumentException("item not found in cart");
        }
    }

    public void scanAndRemove(String barcode) {
        removeItem(scanner.scanBarcode(barcode));
    }

    public List<Item> viewCart(){
        return cart;
    }
}
