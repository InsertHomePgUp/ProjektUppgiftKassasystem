package org.example;

import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CashRegisterTest {

    Currency currency;
    Money deposit;
    List<Integer> moneyToAdd;
    List<Integer> moneyToRemove;

    @BeforeEach
    void createCurrency(){
        currency = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        deposit = new Money(currency, 0);
    }

    @Test
    public void getInventoryTest(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(20);
        CashRegister c = new CashRegister(moneyToAdd);
        assertEquals("{20=1}", c.getInventory().toString());
    }


    @Test
    public void createNewWalletWith20Test(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(20);
        CashRegister c = new CashRegister(moneyToAdd);
        assertEquals(new Money(currency, 2000).toString(), c.getBalance(currency).toString());
    }

    @Test
    public void addOneDenominatorTest(){
        ArrayList<Integer> oneOfEach = new ArrayList<Integer>();

        oneOfEach.add(1);
        oneOfEach.add(2);
        oneOfEach.add(5);
        oneOfEach.add(10);
        oneOfEach.add(20);
        oneOfEach.add(50);
        oneOfEach.add(100);
        oneOfEach.add(200);
        oneOfEach.add(500);
        oneOfEach.add(1000);

        CashRegister cr = new CashRegister(oneOfEach);

        cr.addDenominator(50);
        assertEquals(2, cr.getInventory().get(50));
    }

    @Test
    public void removeOneDenominatorTest(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        CashRegister c = new CashRegister(moneyToAdd);

        c.removeDenominator(10);

        assertEquals(1, c.getInventory().get(10));
    }

    @Test
    public void addMultipleDenominatorsTest(){
        ArrayList<Integer> oneOfEach = new ArrayList<Integer>();

        oneOfEach.add(1);
        oneOfEach.add(2);
        oneOfEach.add(5);
        oneOfEach.add(10);
        oneOfEach.add(20);
        oneOfEach.add(50);
        oneOfEach.add(100);
        oneOfEach.add(200);
        oneOfEach.add(500);
        oneOfEach.add(1000);

        CashRegister cr = new CashRegister(oneOfEach);

        ArrayList<Integer> toAdd = new ArrayList<>();

        toAdd.add(50);
        toAdd.add(100);
        toAdd.add(200);
        toAdd.add(50);


        cr.addDenominators(toAdd);

        assertAll(
                () -> assertEquals(3, cr.getInventory().get(50)),
                () -> assertEquals(1, cr.getInventory().get(10)),
                () -> assertEquals(2, cr.getInventory().get(100)),
                () -> assertEquals(2, cr.getInventory().get(200))
                );

    }

    @Test
    public void removeMultipleDenominatorsTest(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        CashRegister c = new CashRegister(moneyToAdd);

        ArrayList<Integer> toRemove = new ArrayList<>();
        toRemove.add(1);
        toRemove.add(2);
        toRemove.add(5);
        toRemove.add(10);

        c.removeDenominators(toRemove);

        assertAll(
                () -> assertEquals(1, c.getInventory().get(1)),
                () -> assertEquals(1, c.getInventory().get(2)),
                () -> assertEquals(1, c.getInventory().get(5)),
                () -> assertEquals(1, c.getInventory().get(10))
        );    }

    @Test
    public void addAllDenominatorsToWalletTest(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        CashRegister c = new CashRegister(moneyToAdd);
        assertEquals(new Money(currency, 188800).toString(), c.getBalance(currency).toString());
    }

    @Test
    public void addAllDenominatorsTwiceToWalletTest(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(10);
        moneyToAdd.add(20);
        moneyToAdd.add(50);
        moneyToAdd.add(100);
        moneyToAdd.add(200);
        moneyToAdd.add(500);
        moneyToAdd.add(1000);
        CashRegister c = new CashRegister(moneyToAdd);
        assertEquals(new Money(currency, 377600).toString(), c.getBalance(currency).toString());
    }

    @Test
    public void removeOneToNull(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        CashRegister c = new CashRegister(moneyToAdd);
        c.removeDenominator(1);
        assertThrows(NullPointerException.class, () -> {
            c.removeDenominator(1);
        });
    }

    @Test
    public void removeMultipleToNull(){

        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(1);

        CashRegister c = new CashRegister(moneyToAdd);

        ArrayList<Integer> toRemove = new ArrayList<>();
        toRemove.add(1);
        toRemove.add(1);

        c.removeDenominators(toRemove);

        assertThrows(NullPointerException.class, () -> {
            c.removeDenominators(toRemove);
        });

    }

    @Test
    public void addNewDenominatorTest() {
        ArrayList<Integer> initial = new ArrayList<>();
        initial.add(10);
        CashRegister cr = new CashRegister(initial);

        cr.addDenominator(25);

        assertEquals(1, cr.getInventory().get(25));
        assertEquals(1, cr.getInventory().get(10));
    }

    @Test
    public void addMultipleNewDenominatorsTest() {
        ArrayList<Integer> initial = new ArrayList<>();
        initial.add(5);
        CashRegister cr = new CashRegister(initial);

        ArrayList<Integer> toAdd = new ArrayList<>();
        toAdd.add(15);
        toAdd.add(25);

        cr.addDenominators(toAdd);

        assertEquals(1, cr.getInventory().get(5));
        assertEquals(1, cr.getInventory().get(15));
        assertEquals(1, cr.getInventory().get(25));
    }

}
