package org.example;

import java.util.HashMap;

public interface Membership {
    boolean isActive();
    HashMap<Item, Integer> discounts();
    boolean addDiscountRate(double discountRate);
    String description();
    void setDescription(String description);
}
