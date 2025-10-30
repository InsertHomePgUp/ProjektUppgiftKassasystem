package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class BankTest {

	private Bank bank;
	private Currency unknownCurrency;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		unknownCurrency = new Currency("Danska Kronor", "DKR", 100, 50, 1);
	}

	@Test
	public void testConversionSEKtoUSDTest() {

		double actualRate = bank.getConversionRate(USD.instance, SEK.instance);
		double expected = 10;

		assertEquals(expected, actualRate);

	}

	@Test
	public void testExchangeUSDtoSEKTest() {
		
		long ammountIn = 1000;
		long expected = 10000;

		Money moneyUSD = new Money(USD.instance, ammountIn);

		Money moneySEK = bank.exchange(moneyUSD, SEK.instance);

		assertEquals(expected, moneySEK.getAmountInMinorUnit());

	}

	@Test
	public void testExchangeSekToSek() {
		
		long ammountIn = 1000;
		long expected = 1000;

		Money swedishMoney = new Money(SEK.instance, ammountIn);

		Money newSwedishMoney = bank.exchange(swedishMoney, SEK.instance);

		assertEquals(expected, newSwedishMoney.getAmountInMinorUnit());

	}

	@Test
	public void testExchangeUsdToUsd() {
		
		long ammountIn = 1000;
		long expected = 1000;

		Money moneyUSD = new Money(USD.instance, ammountIn);

		Money newMoneyUSD = bank.exchange(moneyUSD, USD.instance);

		assertEquals(expected, newMoneyUSD.getAmountInMinorUnit());

	}

	@Test
	public void exchangeSEKtoUSDTest() {
		
		long ammountIn = 1000;
		long expected = 100;

		Money swedishMoney = new Money(SEK.instance, ammountIn);

		Money USDollar = bank.exchange(swedishMoney, USD.instance);

		assertEquals(expected, USDollar.getAmountInMinorUnit());

	}

	@Test
	public void testExchangeUSDtoEuro() {
		
		long ammountIn = 1000;
		long expected = 10000 / 11;

		Money moneyUSD = new Money(USD.instance, ammountIn);

		Money moneyEuro = bank.exchange(moneyUSD, EURO.instance);

		assertEquals(expected, moneyEuro.getAmountInMinorUnit());

	}

	@Test
	public void testExchangeUnkownCurrancyToSek() {
		
		long ammountIn = 1000;

		Money UnknowMoney = new Money(unknownCurrency, ammountIn);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowMoney, SEK.instance),
				"Expected exchange to throw, but it didn't");

	}

	@Test
	public void testExchangeSEKtoUnknowCurrancy() {
		
		long ammountIn = 1000;

		Money UnknowCurrancy = new Money(unknownCurrency, ammountIn);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowCurrancy, SEK.instance),
				"Expected exchange to throw, but it didn't");

	}
	
	@Test
	 void testGetAvailableCurrenciesIsUnmodifiable() {
	        Map<String, Currency> result = bank.getAvaliableCurrencies();

	        // Verify it's unmodifiable
	        assertThrows(UnsupportedOperationException.class, () -> result.put("GBP", new Currency("GBP", "P", 1,2,3)));
	    }

}
