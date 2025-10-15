package org.example;

import java.util.HashMap;
import java.util.Map;

public class Bank {

	private Map<Currency, Double> conversionRates;

	private Map<String, Currency> currencySet;

	public Bank() {
		this.currencySet = new HashMap<String, Currency>();
		this.conversionRates = new HashMap<Currency, Double>();

		setAvailableCurrencies();

	}
	
	

	private void setAvailableCurrencies() {
		Currency USD = new Currency("US dollar", "$", 10000, 5000, 2000, 1000, 500, 100, 50, 25, 10, 5, 1);
		this.addNewCurrency("USD", USD, 10);

		Currency SEK = new Currency("Svenska kronor", "kr", 100000, 50000, 10000, 5000, 2000, 1000, 500, 100, 1);
		this.addNewCurrency("SEK", SEK, 1);

		Currency EURO = new Currency("Euro", "€", 50000, 20000, 10000, 5000, 2000, 1000, 500);
		this.addNewCurrency("EURO", EURO, 11);

	}
	
	
	

	private void addNewCurrency(String name, Currency currency, double rateRelativeToSEK) {

		if (currencySet.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		currencySet.put(name, currency);
		setConversionRate(currency, rateRelativeToSEK);
	}
	
	
	

	private void setConversionRate(Currency from, double rate) {

		this.conversionRates.put(from, rate);
	}

	public double getConversionRate(Currency from, Currency to) {

		if (from.compareTo(currencySet.get("SEK")) == 0) {
			return 1 / conversionRates.get(to);
		}

		return conversionRates.get(from) * conversionRates.get(to);
	}
	
	

	public Money exchange(Money money, Currency newCurrency) {

		Currency previousCurrency = money.getCurrency();

		if (!conversionRates.containsKey(newCurrency)) {
			throw new IllegalArgumentException(
					"No conversion rate found from " + previousCurrency + " to " + newCurrency);
		}

		double rate = this.getConversionRate(previousCurrency, newCurrency);
		Money moneyInNewCurrency = new Money(newCurrency, (long) (money.getAmountInMinorUnit() * rate));

		return moneyInNewCurrency;

	}

	public Map<String, Currency> getAvaliableCurrencies() {

		return this.currencySet;
	}

}
