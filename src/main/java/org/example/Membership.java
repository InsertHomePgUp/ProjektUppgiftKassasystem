package org.example;

import java.util.HashMap;

public class Membership implements MembershipInterface {

    private final HashMap<Item,Double> discountedItems = new HashMap<>();
    @Override
    public boolean isActive(){
        return true;
    };

    @Override
    public HashMap<Item, Double> getDiscounts(){
        return discountedItems; //unmod collection
    };

    @Override
    public boolean addDiscountRate(Item item, double discountRate){
        discountedItems.put(item,discountRate);
        return true;
    };

    @Override
    public String description(){
        return "Time limited.";
    };

    @Override
    public void setDescription(String description){
    };
}
