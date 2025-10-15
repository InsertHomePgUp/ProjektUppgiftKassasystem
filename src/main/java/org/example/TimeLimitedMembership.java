package org.example;

import java.util.HashMap;

public class TimeLimitedMembership implements Membership {

    @Override
    public boolean isActive(){
        return true;
    };

    @Override
    public HashMap<Item, Double> getDiscounts(){
        return null;
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
