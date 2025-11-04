package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Map;

public class MoneyTest {

	private Currency usd;
	private Money tenDollars;

	@BeforeEach
	void setUp() {
		usd = new Currency("US dollar", "$", 10000, 5000, 2000, 1000, 500, 100, 50, 25, 10, 5, 1);
		tenDollars = new Money(usd, 1000L); // 1000 minor units = $10.00
	}

	@Test
	void testGetCurrency() {
		assertEquals(usd, tenDollars.getCurrency());
	}

	@Test
	void testGetAmountInMinorUnit() {
		assertEquals(1000L, tenDollars.getAmountInMinorUnit());
	}

	@Test
	void testGetAmountInMajorUnit() {
		assertEquals(10L, tenDollars.getAmountInMajorUnit());
	}

	@Test
	void testGetNothing() {
		Money zero = tenDollars.getNothing();
		assertEquals(0L, zero.getAmountInMinorUnit());
		assertEquals(usd, zero.getCurrency());
	}

	@Test
	void testMultiply() {
		Money doubled = tenDollars.multiply(2.0);
		assertEquals(2000L, doubled.getAmountInMinorUnit());
		assertEquals(usd, doubled.getCurrency());
	}

	@Test
	void testAmountCantbeNull() {
		assertThrows(NullPointerException.class, () -> new Money(null, 100));
	}
	
	@Test
	void testMultiplyNegativeFactor() {
		assertThrows(IllegalArgumentException.class, () -> new Money(usd, 100).multiply(-2));
	}

	@Test
	void testCurrencyWhereAmountIsLessThenZero() {
		assertThrows(IllegalArgumentException.class, () -> new Money(usd, -1L));
	}

	@Test
	void testMultiplyRoundingDown() {
		Money result = tenDollars.multiply(1.234);
		assertEquals((long) (1000 * 1.234), result.getAmountInMinorUnit());
	}

	@Test
	void testAddInMinor() {
		Money result = tenDollars.addInMinor(50L);
		assertEquals(1050L, result.getAmountInMinorUnit());
	}

	@Test
	void testAddInMajor() {
		Money result = tenDollars.addInMajor(3L);
		assertEquals(1000 + 3 * 100, result.getAmountInMinorUnit());
	}

	@Test
	void testSubtractInMinor() {
		Money result = tenDollars.subtractInMinor(300L);
		assertEquals(700L, result.getAmountInMinorUnit());
	}

	@Test
	void testSubtractInMajor() {
		Money result = tenDollars.subtractInMajor(2L);
		assertEquals(1000 - 2 * 100, result.getAmountInMinorUnit());
	}

	@Test
	void testToStringWholeAmount() {
		Money m = new Money(usd, 1500L);
		assertEquals("15,00 " + usd.getSymbol(), m.toString());
	}

	@Test
	void testToStringWithCents() {
		Money m = new Money(usd, 1525L);
		assertEquals("15,25 " + usd.getSymbol(), m.toString());
	}

	@Test
	void testImmutability() {
		Money original = new Money(usd, 500L);
		Money modified = original.addInMinor(200L);

		assertNotSame(original, modified);
		assertEquals(500L, original.getAmountInMinorUnit());
		assertEquals(700L, modified.getAmountInMinorUnit());
	}

	@Test
	void testAddInMinorOverflow() {
		Money original = new Money(usd, Long.MAX_VALUE);
		assertThrows(ArithmeticException.class, () -> original.addInMinor(100L));
	}

	@Test
	void testAddInMajorOverflow() {
		Money original = new Money(usd, Long.MAX_VALUE);
		assertThrows(ArithmeticException.class, () -> original.addInMajor(100L));
	}

	@Test
	void testSubtractInMajorBellowZero() {
		Money original = new Money(usd, 0);
		assertThrows(ArithmeticException.class, () -> original.subtractInMajor(100L));
	}

	@Test
	void testSubtractInMinorBellowZero() {
		Money original = new Money(usd, 0);
		assertThrows(ArithmeticException.class, () -> original.subtractInMinor(100L));
	}

	@Test
	void testSubtractWithNegativeNumberMajorUnit() {
		Money original = new Money(usd, 100L);
		assertThrows(IllegalArgumentException.class, () -> original.subtractInMajor(-10L));
	}

	@Test
	void testSubtractWithNegativeNumberMinorUnit() {
		Money original = new Money(usd, 100L);
		assertThrows(IllegalArgumentException.class, () -> original.subtractInMinor(-10L));
	}

	@Test
	void testAddtWithNegativeNumberMajorUnit() {
		Money original = new Money(usd, 100L);
		assertThrows(IllegalArgumentException.class, () -> original.addInMajor(-10L));
	}

	@Test
	void testAddWithNegativeNumberMinorUnit() {
		Money original = new Money(usd, 100L);
		assertThrows(IllegalArgumentException.class, () -> original.addInMinor(-10L));
	}

	@Test
	void testEquals() {
		Money original = new Money(usd, 100L);
		Money other = new Money(usd, 100L);
		Money otherCurrency = new Money(SEK.instance, 100L);
		Money otherAmount = new Money(usd, 10L);
		assertEquals(original.equals(original), true);
		assertEquals(original.equals(other), true);
		assertEquals(original.equals(otherCurrency), false);
		assertEquals(original.equals(otherAmount), false);
		assertEquals(original.equals(null), false);

	}
	
	


	@Test
	void testHashCode() {
		Money original = new Money(usd, 100L);
		Money other = new Money(usd, 100L);
		Money otherCurrency = new Money(SEK.instance, 100L);
		Money otherAmount = new Money(usd, 10L);
		assertEquals(original.hashCode(), other.hashCode());
		assertNotEquals(original.hashCode(), otherCurrency.hashCode());
		assertNotEquals(original.hashCode(), otherAmount.hashCode());
	}

}
