package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
	public void testRoundingInExchange() {

		long ammountIn = 1005;
		long expected = 101;

		Money swedishMoney = new Money(SEK.instance, ammountIn);

		Money USDollar = bank.exchange(swedishMoney, USD.instance);

		assertEquals(expected, USDollar.getAmountInMinorUnit());

	}
	
	

	@Test
	public void testExchangeUSDtoEuro() {

		long ammountIn = 1000;
		long expected = 909;

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

		assertThrows(UnsupportedOperationException.class, () -> result.put("GBP", new Currency("GBP", "P", 1, 2, 3)));
	}
	
	@Test
    void testAvailableCurrenciesContent() {

        Map<String, Currency> currencies = bank.getAvaliableCurrencies();

        assertEquals(3, currencies.size(), "Expected 3 available currencies");

        assertTrue(currencies.containsKey(USD.instance.toString()), "Should contain USD");
        assertTrue(currencies.containsKey(EURO.instance.toString()), "Should contain EUR");
        assertTrue(currencies.containsKey(SEK.instance.toString()), "Should contain EUR");

        assertEquals(SEK.instance, currencies.get(SEK.instance.toString()));
        assertEquals(USD.instance, currencies.get(USD.instance.toString()));
        assertEquals(EURO.instance, currencies.get(EURO.instance.toString()));
    }
	
	 @Test
	void testSpyExchangeDivisionWithZero() {

		Bank realBank = new Bank();

		Bank spyBank = spy(realBank);

        Currency from = SEK.instance;
        Currency to = SEK.instance;
        
        when(spyBank.getConversionRate(from, to)).thenReturn(0.0);

        assertThrows(ArithmeticException.class, () -> spyBank.exchange(new Money(from,100L), to),
				"Expected exchange to throw, but it didn't");

    }

}
