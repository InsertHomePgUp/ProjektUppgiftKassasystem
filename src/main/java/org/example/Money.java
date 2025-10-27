package org.example;

public class Money {

	private static final int MAJOR_UNIT = 100;

	private Currency currency;
	private final long amountInMinorUnit;

	public Money(Currency currency, long amountInMinorUnit) {
        this.currency = currency;
		this.amountInMinorUnit = amountInMinorUnit;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money getNothing() {
		return new Money(currency, 0L);
	}

	public long getAmountInMinorUnit() {
		return amountInMinorUnit;
	}

	private long getRestAfterMajorUnit() {
		return amountInMinorUnit % MAJOR_UNIT;
	}

	public long getAmountInMajorUnit() {
		return amountInMinorUnit / MAJOR_UNIT;
	}

	public Money multiply(double factor) {
		long newAmmount = (long) (amountInMinorUnit * factor);

		return new Money(currency, newAmmount);
	}

	public Money addInMinor(long addedMinorUnits) {

		long newAmmount = amountInMinorUnit + addedMinorUnits;
		if (newAmmount < amountInMinorUnit) {
			throw new ArithmeticException("Overflow!");
		}
		return new Money(currency, newAmmount);

	}

	public Money addInMajor(long addedMajorUnits) {

		long newAmmount = amountInMinorUnit + addedMajorUnits * MAJOR_UNIT;

		if (newAmmount < amountInMinorUnit) {
			throw new ArithmeticException("Overflow!");
		}

		return new Money(currency, newAmmount);
	}

	public Money subtractInMinor(long addedMinorUnits) {

		long newAmmount = amountInMinorUnit - addedMinorUnits;

		if (newAmmount > amountInMinorUnit) {
			throw new ArithmeticException("Underflow!");
		}
		return new Money(currency, newAmmount);
	}

	public Money subtractInMajor(long addedMajorUnits) {

		long newAmmount = amountInMinorUnit - addedMajorUnits * MAJOR_UNIT;

		if (newAmmount > amountInMinorUnit) {
			throw new ArithmeticException("Underflow!");
		}
		return new Money(currency, newAmmount);
	}

	@Override
	public String toString() {
		if (getRestAfterMajorUnit() == 0) {
			return String.format("%d,%d0 %s", getAmountInMajorUnit(), getRestAfterMajorUnit(),
					getCurrency().getSymbol());
		}
		return String.format("%d,%d %s", getAmountInMajorUnit(), getRestAfterMajorUnit(), getCurrency().getSymbol());
	}


}
