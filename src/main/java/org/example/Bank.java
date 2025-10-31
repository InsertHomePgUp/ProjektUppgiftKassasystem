package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bank {

	private Map<Currency, Double> conversionRates;

	private Map<String, Currency> currencySet;

	private final double sekToSekConversionRate = 1;
	private final double usdToSekConversionRate = 10;
	private final double euroToSekConversionRate = 11;

	public Bank() {
		this.currencySet = new HashMap<String, Currency>();
		this.conversionRates = new HashMap<Currency, Double>();
		setAvailableCurrencies();

	}

	private void setAvailableCurrencies() {

		this.addNewCurrency(SEK.instance.toString(), SEK.instance, sekToSekConversionRate);

		this.addNewCurrency(USD.instance.toString(), USD.instance, usdToSekConversionRate);

		this.addNewCurrency(EURO.instance.toString(), EURO.instance, euroToSekConversionRate);

	}

	private void addNewCurrency(String name, Currency currency, double rateRelativeToBase) {

		currencySet.put(name, currency);
		setConversionRate(currency, rateRelativeToBase);
	}

	private void setConversionRate(Currency from, double rate) {

		this.conversionRates.put(from, rate);
	}

	public double getConversionRate(Currency from, Currency to) {

		if (from.compareTo(SEK.instance) == 0) {
			return 1 / conversionRates.get(to);
		} else if (to.compareTo(SEK.instance) == 0) {
			return conversionRates.get(from);
		} else {
			return getConversionRate(from, SEK.instance) * getConversionRate(SEK.instance, to);
		}

	}

	public Money exchange(Money money, Currency newCurrency) {

		Currency previousCurrency = money.getCurrency();

		if (!conversionRates.containsKey(previousCurrency)) {
			throw new IllegalArgumentException("No conversion rate found for " + previousCurrency);
		}

		double rate = this.getConversionRate(previousCurrency, newCurrency);
		Money moneyInNewCurrency = new Money(newCurrency, (long) (money.getAmountInMinorUnit() * rate));

		return moneyInNewCurrency;

	}

	public Map<String, Currency> getAvaliableCurrencies() {

		return Collections.unmodifiableMap(this.currencySet);
	}

}
