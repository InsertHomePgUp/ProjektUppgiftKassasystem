package org.example;

import java.util.HashMap;

public interface Membership {
    boolean isActive();
    HashMap<Item, Double> getDiscounts();
    boolean addDiscountRate(double discountRate);
    String description();
    void setDescription(String description);
}
