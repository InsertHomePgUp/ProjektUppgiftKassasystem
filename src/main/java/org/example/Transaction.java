package org.example;

import java.util.*;

public class Transaction {

    private int totalPrice;
    private Set<Item> items;
    Set <Deductor> deductors;
    private boolean paid;

    public Transaction(Set<Item> items, Set<Deductor> deductors){
        this.items = items;
        this.deductors = deductors;
        this.paid = false;
    }

    public int getTotalPrice(){
        for (Item item : items){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    public void payWithCard(){

        paid = true;
    }

    public void payWithCash(){

        paid = true;
    }

    public StringBuilder getReceipt(){
        if(paid){
            StringBuilder receipt = new StringBuilder();
            for(Item item : items){
                receipt.append(item.getName())
                        .append("   ")
                        .append(item.getPrice())
                        .append("\n");
            }
            for(Deductor deductor : deductors){
                receipt.append(deductor.getType())
                        .append("   ")
                        .append(deductor.getAmount())
                        .append("\n");
            }
            return receipt;
        }else {
            throw new IllegalStateException("Betala för att få kvittot");
        }
    }
}
