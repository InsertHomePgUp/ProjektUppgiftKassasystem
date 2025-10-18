package org.example;

import java.util.HashMap;

public class NoMembership implements Membership {

    @Override
    public boolean isActive(){
        return false;
    };

    @Override
    public HashMap<Item, Double> getDiscounts(){
        return null;
    };

    @Override
    public boolean addDiscountRate(Item item, double discountRate){
        return false;
    };

    @Override
    public String description(){
        return "No membership.";
    };

    @Override
    public void setDescription(String description){
    };
}
