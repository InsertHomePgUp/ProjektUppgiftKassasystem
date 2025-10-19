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

	@BeforeEach
	void setUp() {
		bank = new Bank();
		currencies = bank.getAvaliableCurrencies();
		USD = currencies.get("USD");
		SEK = currencies.get("SEK");
		euro = currencies.get("EURO");
	}

	@Test
	public void testConversionSEKtoUSDTest() {

		double actualRate = bank.getConversionRate(USD, SEK);
		double expected = 10;

		assertEquals(actualRate, expected);

	}

	@Test
	public void testExchangeUSDtoSEKTest() {

		Money moneyUSD = new Money(USD, 1000);

		Money moneySEK = bank.exchange(moneyUSD, SEK);

		assertEquals(moneySEK.getAmountInMinorUnit(), 10000);

	}

	@Test
	public void testExchangeToSameCurrancy() {

		Money swedishMoney = new Money(SEK, 1000);

		Money newSwedishMoney = bank.exchange(swedishMoney, SEK);

		assertEquals(newSwedishMoney.getAmountInMinorUnit(), 1000);

	}

	@Test
	public void testExchangeToSameCurrancy2() {

		Money moneyUSD = new Money(USD, 1000);

		Money newMoneyUSD = bank.exchange(moneyUSD, USD);

		assertEquals(newMoneyUSD.getAmountInMinorUnit(), 1000);

	}

	@Test
	public void exchangeSEKtoUSDTest() {

		Money swedishMoney = new Money(SEK, 1000);

		Money USDollar = bank.exchange(swedishMoney, USD);

		assertEquals(USDollar.getAmountInMinorUnit(), 100);

	}

	@Test
	public void testExchangeUSDtoEuro() {

		Money moneyUSD = new Money(USD, 1000);

		Money moneyEuro = bank.exchange(moneyUSD, euro);

		assertEquals(moneyEuro.getAmountInMinorUnit(), 10000 / 11);

	}

	@Test
	public void testExchangeUnkownCurrancyToSek() {

		Money UnknowMoney = new Money(new Currency("Danska Kronor", "DKR", 100, 50, 1), 1000);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowMoney, SEK),
				"Expected exchange to throw, but it didn't");

	}

	@Test
	public void testExchangeSEKtoUnknowCurrancy() {

		Money UnknowCurrancy = new Money(new Currency("Danska Kronor", "DKR", 100, 50, 1), 1000);
		assertThrows(IllegalArgumentException.class, () -> bank.exchange(UnknowCurrancy, SEK),
				"Expected exchange to throw, but it didn't");

	}

}
