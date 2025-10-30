package org.example;

import java.util.Arrays;

public class Currency implements Comparable<Currency> {

	public final String name;
	public final String symbol;
	public final int[] denominations;

	public Currency(String name, String symbol, int... denominations) {
		
		if(name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		
		if(name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		
		if(symbol == null) {
			throw new IllegalArgumentException("Symbol cannot be null");
		}
		
		if(symbol.isBlank()) {
			throw new IllegalArgumentException("Symbol cannot be empty");
		}
		
		if(denominations == null) {
			throw new IllegalArgumentException("denominations cannot be null");
		}
		
		this.name = name;
		this.symbol = symbol;
		this.denominations = denominations;
	}

	public int [] getDenominations(){
		return Arrays.copyOf(denominations, denominations.length);
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Currency other) {
		return this.name.compareTo(other.name) ;
	}

	public Object getSymbol() {

		return this.symbol;
	}
}