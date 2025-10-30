package org.example;

public class Money {

	private static final int MAJOR_UNIT = 100;

	private Currency currency;
	private final long amountInMinorUnit;

	public Money(Currency currency, long amountInMinorUnit) {
		
		if(currency == null) {
			throw new NullPointerException("Currency can not be null");
		}
		if(amountInMinorUnit < 0L) {
			throw new IllegalArgumentException("Ammount can not be less then 0");
		}
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
		long newAmount =  Math.round(amountInMinorUnit * factor);

		return new Money(currency, newAmount);
	}

	public Money addInMinor(long addedMinorUnits) {
		
		if(addedMinorUnits < 0) {
			throw new IllegalArgumentException("cannot add with negative number");
		}

		long newAmmount = amountInMinorUnit + addedMinorUnits;
		if (newAmmount < amountInMinorUnit) {
			throw new ArithmeticException("Overflow!");
		}
		return new Money(currency, newAmmount);

	}

	public Money addInMajor(long addedMajorUnits) {
		
		if(addedMajorUnits < 0) {
			throw new IllegalArgumentException("cannot add with negative number");
		}

		long newAmmount = amountInMinorUnit + addedMajorUnits * MAJOR_UNIT;

		if (newAmmount < 0) {
			throw new ArithmeticException("Overflow!");
		}

		return new Money(currency, newAmmount);
	}

	public Money subtractInMinor(long addedMinorUnits) {
		
		if(addedMinorUnits < 0) {
			throw new IllegalArgumentException("cannot subtract with negative number");
		}

		long newAmmount = amountInMinorUnit - addedMinorUnits;

		if (newAmmount < 0) {
			throw new ArithmeticException("Cannot subract more then current amount!");
		}
		return new Money(currency, newAmmount);
	}

	public Money subtractInMajor(long addedMajorUnits) {
		
		if(addedMajorUnits < 0) {
			throw new IllegalArgumentException("cannot subtract with negative number");
		}
		

		long newAmmount = amountInMinorUnit - addedMajorUnits * MAJOR_UNIT;

		if (newAmmount < 0) {
			throw new ArithmeticException("Cannot subract more then cureent amount!");
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
