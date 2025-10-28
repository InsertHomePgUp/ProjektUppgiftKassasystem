package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

	private Currency usd;
	private Currency eur;
	private Currency anotherUsd;

	@BeforeEach
	void setUp() {
		usd = new Currency("US Dollar", "$", 1, 5, 10, 20, 50, 100);
		eur = new Currency("Euro", "€", 5, 10, 20, 50, 100, 200);
		anotherUsd = new Currency("US Dollar", "$", 1, 5, 10, 20, 50, 100);
	}

	@Test
	void testConstructorAndFields() {
		assertEquals("US Dollar", usd.name);
		assertEquals("$", usd.symbol);
		assertArrayEquals(new int[] { 1, 5, 10, 20, 50, 100 }, usd.denominations);
	}

	@Test
	void testGetDenominations() {
		int[] expected = { 1, 5, 10, 20, 50, 100 };
		assertArrayEquals(expected, usd.getDenominations());
	}

	@Test
	void testToStringReturnsName() {
		assertEquals("US Dollar", usd.toString());
		assertEquals("Euro", eur.toString());
	}

	@Test
	void testCompareTo() {
		assertTrue(eur.compareTo(usd) < 0);
		assertTrue(usd.compareTo(eur) > 0);
		assertEquals(0, usd.compareTo(anotherUsd));
	}

	@Test
	void testGetSymbol() {

		assertEquals("$", usd.getSymbol());
		assertEquals("€", eur.getSymbol());
	}

	@Test
	void testDifferentCurrenciesNotEqualByReference() {
		assertNotSame(usd, anotherUsd);
		assertArrayEquals(usd.getDenominations(), anotherUsd.getDenominations());
		assertEquals(usd.name, anotherUsd.name);
		assertEquals(usd.symbol, anotherUsd.symbol);
	}
	
	@Test
    public void testSingletonInstance() {
        SEK instance1 = SEK.instance;
        SEK instance2 = SEK.instance;
        
        assertSame(instance1, instance2, "SEK should have a single instance");
    }

}