package org.example;

public class Money {

    private static final int MAJOR_UNIT = 100;

    private Currency currency;
    private final long amountInMinorUnit;

    public Money(Currency currency, long amountInMinorUnit) {
        this.currency = currency;
        this.amountInMinorUnit = amountInMinorUnit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money getNothing() {
        return new Money(currency, 0L);
    }

    public long getAmountInMinorUnit() {
        return amountInMinorUnit;
    }

    private long getRestAfterMajorUnit() {
        return amountInMinorUnit % MAJOR_UNIT;
    }

    public long getAmountInMajorUnit() {
        return amountInMinorUnit / MAJOR_UNIT;
    }

    public Money multiply(double factor) {
        long newAmount = (long) (amountInMinorUnit * factor);
        return new Money(currency, newAmount);
    }


    public String toString() {
        if(getRestAfterMajorUnit() == 0) {
            return String.format("%d,%d0 %s", getAmountInMajorUnit(), getRestAfterMajorUnit(), getCurrency().getSymbol());
        }
        return String.format("%d,%d %s", getAmountInMajorUnit(), getRestAfterMajorUnit(), getCurrency().getSymbol());
    }


}
