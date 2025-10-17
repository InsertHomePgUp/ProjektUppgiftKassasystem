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
    public void createNewWalletWith20Test(){
        moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(20);
        CashRegister c = new CashRegister(moneyToAdd);
        assertEquals(new Money(currency, 2000).toString(), c.getBalance(currency).toString());
    }

    @Test
    public void addToWalletTest(){
        moneyToAdd = new ArrayList<Integer>(20);
        CashRegister c = new CashRegister(moneyToAdd);
        moneyToAdd = new ArrayList<Integer>(100);
        c.addDenominators(moneyToAdd);
    }

    @Test
    public void removeFromWalletTest(){
        moneyToAdd = new ArrayList<Integer>(20);
        CashRegister c = new CashRegister(moneyToAdd);
        moneyToAdd = new ArrayList<Integer>(100);
        c.addDenominators(moneyToAdd);
        moneyToRemove = new ArrayList<Integer>(100);
        c.removeDenominators(moneyToRemove);
    }

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
}
