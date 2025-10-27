package org.example;

import java.util.*;

public class CashRegister {

    private final TreeMap<Integer, Integer> inventory;

    public CashRegister(List<Integer> denominations){
        inventory = new TreeMap<Integer, Integer>();
        for (int denominator : denominations){
            if(inventory.containsKey(denominator)){
                int newValue = inventory.get(denominator) + 1;
                inventory.remove(denominator);
                inventory.put(denominator, newValue);
            }else{
                inventory.put(denominator, 1);
            }
        }
    }

    public Money getBalance(Currency currency){
        Money balance = new Money(currency, 0);
        for (int denominator : inventory.keySet()){
            for(int i = 0 ; i < inventory.get(denominator) ; i++){
                balance = balance.addInMajor(denominator);
            }
        }
        return balance;
    }

    public TreeMap<Integer, Integer> getInventory(){
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
        if(inventory.containsKey(denominator)) {
            int previousBalance = inventory.get(denominator);
            inventory.remove(denominator);
            inventory.put(denominator, previousBalance + 1);
        }else {
            inventory.putIfAbsent(denominator, 1);
        }
    }

    public void removeDenominators(List<Integer> denominators){

        for(int denominator : denominators){
            if (inventory.containsKey(denominator)){
                int previousBalance = inventory.get(denominator);
                inventory.remove(denominator);
                if (previousBalance != 1) {
                    inventory.put(denominator, previousBalance - 1);
                } else {
                    inventory.remove(denominator);
                }
            }else{
                throw new NullPointerException("Denominator not in register");
            }
        }

    }

    public void removeDenominator(int denominator){
        if (inventory.containsKey(denominator)) {
            int previousBalance = inventory.get(denominator);
            inventory.remove(denominator);
            if (previousBalance != 1) {
                inventory.put(denominator, previousBalance - 1);
            } else {
                inventory.remove(denominator);
            }
        }else{
            throw new NullPointerException("Denominator not in register");
        }
    }

}
