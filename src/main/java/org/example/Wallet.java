package org.example;

import java.util.*;

public class Wallet {

    private Money balance;
    private HashMap<Integer, Integer> inventory = new HashMap<>();

    public Wallet(Money balance){
        this.balance = balance;
    }

    public Money getBalance(){
        return balance;
    }

    public void addMoney(Money money){
        //balance.addMoney();
    }

    public void removeMoney(Money money){
        //balance.removeMoney();
    }
}
