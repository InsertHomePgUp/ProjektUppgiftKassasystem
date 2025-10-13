package org.example;

public class Currency {
    public final String name;
    public final String symbol;
    public final int[] denominations;

    public Currency(String name, String symbol, int... denominations) {
        this.name = name;
        this.symbol = symbol;
        this.denominations = denominations;
    }
}
