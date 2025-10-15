package org.example;

public class Currency implements Comparable<Currency> {

	public final String name;
	public final String symbol;
	public final int[] denominations;

	public Currency(String name, String symbol, int... denominations) {
		this.name = name;
		this.symbol = symbol;
		this.denominations = denominations;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Currency other) {
		return this.name.compareTo(other.name);
	}

	public Object getSymbol() {

		return this.symbol;
	}
}