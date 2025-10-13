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
}
