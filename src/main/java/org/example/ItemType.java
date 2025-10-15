package org.example;

public class ItemType {
    private final String name;
    private final double taxRate;
    private final Money deposit;
    private final int ageLimit;

    public ItemType(String name, double taxRate, Money deposit, int ageLimit) {
        validateName(name);
        validateTaxRate(taxRate);
        validateDeposit(deposit);
        validateAgeLimit(ageLimit);
        this.name = name;
        this.taxRate = taxRate / 100; //To store as fraction
        this.deposit = deposit;
        this.ageLimit = ageLimit;
    }


    public String getName() {
        return name;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public Money getDeposit() {
        return deposit;
    }

    public int getAgeLimit() {
        return ageLimit;
    }


    private void validateName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    private void validateTaxRate(double taxRate) {
        if(taxRate < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }

        if(taxRate >= 100) {
            throw new IllegalArgumentException("Tax rate cannot over 100");
        }

    }

    private void validateDeposit(Money deposit) {
        if(deposit.getAmountInMinorUnit() < 0) {
            throw new IllegalArgumentException("Deposit cannot be negative");
        }
    }

    private void validateAgeLimit(int ageLimit) {
        if(ageLimit < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}
