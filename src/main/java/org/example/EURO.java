package org.example;

public class EURO extends Currency{
	
	public static final EURO instance = new EURO();

	public EURO() {
		super("Euro", "€",50000,20000, 10000, 5000, 2000, 1000, 500);
	}

}
