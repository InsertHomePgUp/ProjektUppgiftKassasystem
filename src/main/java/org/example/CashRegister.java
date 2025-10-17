package org.example;

import java.util.*;

public class CashRegister {

    private final HashMap<Integer, Integer> inventory;

    public CashRegister(List<Integer> denominations){
        inventory = new HashMap<Integer, Integer>();
        for (int denominator : denominations){
            inventory.putIfAbsent(denominator, 1);
        }
    }

    public Money getBalance(Currency currency){
        Money balance = new Money(currency, 0);
        for (int denominator : inventory.keySet()){
            for(int i = 0 ; i < inventory.get(denominator) ; i++){
                System.out.println("valör registrerad: " + denominator);
                balance = balance.addInMajor(denominator);
            }
        }
        return balance;
    }

    public HashMap<Integer, Integer> getInventory(){
        return inventory;
    }

    public void addDenominators(List<Integer> denominators){
        for(int denominator : denominators){
            if(inventory.containsKey(denominator)) {
                int previousBalance = inventory.get(denominator);
                inventory.remove(denominator);
                inventory.put(denominator, previousBalance + 1);
            }else{
                inventory.putIfAbsent(denominator, 1);
            }
        }

    }

    public void addDenominator(int denominator){
        int previousBalance = inventory.get(denominator);
        inventory.remove(denominator);
        inventory.put(denominator, previousBalance + 1);
    }

    public void removeDenominators(List<Integer> denominators){
        for(int denominator : denominators){
            int previousBalance = inventory.get(denominator);
            inventory.remove(denominator);
            inventory.put(denominator, previousBalance - 1);
        }

    }

    public void removeDenominator(int denominator){
        int previousBalance = inventory.get(denominator);
        inventory.remove(denominator);
        inventory.put(denominator, previousBalance - 1);
    }

}
