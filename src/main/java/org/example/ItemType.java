package org.example;

public class ItemType {
    private final String name;
    private final double taxRate;
    private final int deposit;
    private final int ageLimit;

    public ItemType(String name, double taxRate, int deposit, int ageLimit) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if(taxRate < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }

        if(deposit < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }


        if(ageLimit < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }

        this.name = name;
        this.taxRate = taxRate;
        this.deposit = deposit;
        this.ageLimit = ageLimit;
    }

    public String getName() {
        return name;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public int getDeposit() {
        return deposit;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

}
