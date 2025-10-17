package org.example;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    public Currency createSEK(){
        Currency SEK = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        return SEK;
    }

    public List<Item> createItems(){
        List<Item> items = new ArrayList<>();
        Currency SEK = createSEK();

        Money deposit = new Money(SEK, 100);

        ItemType vegetable = new ItemType("Vegetable", 0.25, deposit, 0);
        Money tomatoPrice = new Money(SEK, 625);
        Item tomato = new Item("Tomato", vegetable, tomatoPrice);
        items.add(tomato);
        Money cucumberPrice = new Money(SEK, 1000);
        Item cucumber = new Item("Cucumber", vegetable, cucumberPrice);
        items.add(cucumber);

        ItemType dairy = new ItemType("Dairy", 0.25, deposit, 0);
        Money milkPrice = new Money(SEK, 1500);
        Item milk = new Item("Milk", dairy, milkPrice);
        items.add(milk);
        Money cheesePrice = new Money(SEK, 8000);
        Item cheese = new Item("Cheese", dairy, cheesePrice);
        items.add(cheese);

        return items;
    }

    public List<Deductor> createDeductors(){
        List<Deductor> deductors = new ArrayList<Deductor>();

        Deductor Presentkort50 = new Deductor(50, "Presentkort");
        deductors.add(Presentkort50);

        Deductor Rabatt20 = new Deductor(20, "Rabatt");
        deductors.add(Rabatt20);

        return deductors;
    }

    @Test
    public void createTransaction(){
        Transaction t = new Transaction(createItems(), createDeductors());
    }

    @Test
    public void createReceipt(){
        Transaction t = new Transaction(createItems(), createDeductors());
        t.payWithCard();
        t.getReceipt();
    }

    @Test
    public void correctTotalWithoutDeductors(){
        Currency SEK = createSEK();
        List<Deductor> emptyList = new ArrayList<Deductor>();
        Transaction t = new Transaction(createItems(), emptyList);
        assertEquals(111.25, t.getTotalPrice());
    }
    @Test
    public void correctTotalWithDeductors(){
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), createDeductors());
        assertEquals(49, t.getTotalPrice());
    }

    @Test
    public void receiptContainsAll(){
        Transaction t = new Transaction(createItems(), createDeductors());
        t.payWithCard();
        assertEquals("Tomato   6,25 kr\n" +
                "Cucumber   10,00 kr\n" +
                "Milk   15,00 kr\n" +
                "Cheese   80,00 kr\n" +
                "Presentkort   50.0\n" +
                "Rabatt   20.0\n" +
                "Totalt:   49.0", t.getReceipt().toString());
    }

    @Test
    public void paidWithCashExactAmount(){
        ArrayList<Integer> moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(2);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(20);
        moneyToAdd.add(20);
        CashRegister register = new CashRegister(moneyToAdd);
        Transaction t = new Transaction(createItems(), createDeductors());
        assertEquals(new ArrayList<>().toString() , t.payWithCash(register, moneyToAdd).toString());
    }

    @Test
    public void paidWithCashHigherAmount(){
        ArrayList<Integer> moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(1);
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(10);

        ArrayList<Integer> customerPaysWith = new ArrayList<>();
        customerPaysWith.add(50);

        ArrayList<Integer> expectedChange = new ArrayList<>();
        expectedChange.add(1);

        CashRegister register = new CashRegister(moneyToAdd);
        Transaction t = new Transaction(createItems(), createDeductors());

        assertEquals(expectedChange.toString() , t.payWithCash(register, customerPaysWith).toString());
    }

    @Test
    public void receiptWithoutPaying(){
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), createDeductors());
        assertThrows(IllegalStateException.class, t::getReceipt);
    }

    @Test
    public void correctValueWithBonuscheck100(){
        List<Deductor> deductors = new ArrayList<Deductor>();
        Deductor Bonuscheck100 = new Deductor(100, "Bonuscheck");
        deductors.add(Bonuscheck100);
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), deductors);
        assertEquals(11.25, t.getTotalPrice());
    }
}
