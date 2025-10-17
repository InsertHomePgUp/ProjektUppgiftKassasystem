package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class BankTest {

	public Bank bank = new Bank();
	Map<String, Currency> currencies = bank.getAvaliableCurrencies();
	Currency USD = currencies.get("USD");
	Currency SEK = currencies.get("SEK");
	Currency euro = currencies.get("EURO");

	@Test
	public void conversionSEKtoUSDTest() {

		double actualRate = bank.getConversionRate(USD, SEK);
		double expected = 10;

		assertEquals(actualRate, expected);

	}

	@Test
	public void exchangeUSDtoSEKTest() {

		Money moneyUSD = new Money(USD, 1000);

		Money moneySEK = bank.exchange(moneyUSD, SEK);

		assertEquals(moneySEK.getAmountInMinorUnit(), 10000);

	}

	@Test
	public void exchangeSEKtoUSDTest() {

		Money swedishMoney = new Money(SEK, 1000);

		Money USDollar = bank.exchange(swedishMoney, USD);

		assertEquals(USDollar.getAmountInMinorUnit(), 100);

	}
	
	@Test
	public void exchangeUSDtoEuro() {

		Money moneyUSD = new Money(USD, 1000);

		Money moneyEuro = bank.exchange(moneyUSD, euro);

		assertEquals(moneyEuro.getAmountInMinorUnit(),10000/11);

	}
	
	@Test
	public void exchangeUnkownCurrancyToSek() {

		Money UnknowMoney = new Money(new Currency("Danska Kronor", "DKR", 100,50,1), 1000);
		 assertThrows(IllegalArgumentException.class,() -> bank.exchange(UnknowMoney, SEK),
		           "Expected exchange to throw, but it didn't"
		    );


	}

}
