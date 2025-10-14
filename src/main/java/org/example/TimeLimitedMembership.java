package org.example;

import java.util.HashMap;

public class TimeLimitedMembership implements Membership {

    @Override
    public boolean isActive(){
        return true;
    };

    @Override
    public HashMap<Item, Double> getDiscounts(){
        ItemType vegetable = new ItemType("vegetable", 1.0, 0, 0);
        Money cost = new Money(SEK.instance, 500);
        Item carrot = new Item("carrot", vegetable, cost);

        HashMap<Item, Double> discounts = new HashMap<>();

        discounts.putIfAbsent(carrot, 0.2);
        return discounts;
    };

    @Override
    public boolean addDiscountRate(double discountRate){
        return false;
    };

    @Override
    public String description(){
        return "Time limited.";
    };

    @Override
    public void setDescription(String description){
    };
}
