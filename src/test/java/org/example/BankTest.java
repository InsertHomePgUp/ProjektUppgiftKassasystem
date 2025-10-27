package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class BankTest {

	private Bank bank;
	private Map<String, Currency> currencies;
	private Currency USD;
	private Currency SEK;
	private Currency euro;
	private Currency unknownCurrency;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		currencies = bank.getAvaliableCurrencies();
		USD = currencies.get("USD");
		SEK = currencies.get("SEK");
		euro = currencies.get("EURO");
		unknownCurrency = new Currency("Danska Kronor", "DKR", 100, 50, 1);
	}

	@Test
	public void testConversionSEKtoUSDTest() {

		double actualRate = bank.getConversionRate(USD, SEK);
		double expected = 10;

		assertEquals(actualRate, expected);

	}

	@Test
	public void testExchangeUSDtoSEKTest() {
		
		long ammountIn = 1000;
		long expected = 10000;

		Money moneyUSD = new Money(USD, ammountIn);

		Money moneySEK = bank.exchange(moneyUSD, SEK);

		assertEquals(moneySEK.getAmountInMinorUnit(), expected);

	}

	@Test
	public void testExchangeToSameCurrancy() {
		
		long ammountIn = 1000;
		long expected = 1000;

		Money swedishMoney = new Money(SEK, ammountIn);

		Money newSwedishMoney = bank.exchange(swedishMoney, SEK);

		assertEquals(newSwedishMoney.getAmountInMinorUnit(), expected);

	}

	@Test
	public void testExchangeToSameCurrancy2() {
		
		long ammountIn = 1000;
		long expected = 1000;

		Money moneyUSD = new Money(USD, ammountIn);

		Money newMoneyUSD = bank.exchange(moneyUSD, USD);

		assertEquals(newMoneyUSD.getAmountInMinorUnit(), expected);

	}

	@Test
	public void exchangeSEKtoUSDTest() {
		
		long ammountIn = 1000;
		long expected = 100;

		Money swedishMoney = new Money(SEK, ammountIn);

		Money USDollar = bank.exchange(swedishMoney, USD);

		assertEquals(USDollar.getAmountInMinorUnit(), expected);

	}

	@Test
	public void testExchangeUSDtoEuro() {
		
		long ammountIn = 1000;
		long expected = 10000 / 11;

		Money moneyUSD = new Money(USD, ammountIn);

		Money moneyEuro = bank.exchange(moneyUSD, euro);

		assertEquals(moneyEuro.getAmountInMinorUnit(), expected);

	}

	@Test
	public void testExchangeUnkownCurrancyToSek() {
		
		long ammountIn = 1000;

		Money UnknowMoney = new Money(unknownCurrency, ammountIn);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowMoney, SEK),
				"Expected exchange to throw, but it didn't");

	}

	@Test
	public void testExchangeSEKtoUnknowCurrancy() {
		
		long ammountIn = 1000;

		Money UnknowCurrancy = new Money(unknownCurrency, ammountIn);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowCurrancy, SEK),
				"Expected exchange to throw, but it didn't");

	}

}
