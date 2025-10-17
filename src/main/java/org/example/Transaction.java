package org.example;

import java.util.*;

public class Transaction {

    private double totalPrice;
    private List<Item> items;
    private List <Deductor> deductors;
    private boolean paid;

    public Transaction(List<Item> items, List<Deductor> deductors){
        this.items = items;
        this.deductors = deductors;
        this.paid = false;
    }

    public double getTotalPrice(){
        for (Item item : items){
            totalPrice += item.getPrice().getAmountInMinorUnit();
            System.out.println(totalPrice);
        }
        for (Deductor deductor : deductors){
            switch (deductor.getType()){
                case("Presentkort"):
                    System.out.println("presentkort appliceras");
                    totalPrice -= deductor.getAmount()*100;
                    System.out.println(totalPrice);
                    break;

                case("Bonuscheck"):
                    System.out.println("bonuscheck appliceras");
                    totalPrice -= deductor.getAmount()*100;
                    System.out.println(totalPrice);
                    break;

                case("Rabatt"):
                    System.out.println("rabatt appliceras: " + deductor.getAmount() * 0.01);
                    totalPrice -= totalPrice * (deductor.getAmount() * 0.01);
                    System.out.println(totalPrice);
                    break;

                default:
                        throw new IllegalArgumentException("Invalid deductor");
            }
        }
        //if totalprice < 0 --> totalprice == 0
        return totalPrice / 100;
    }

    public void payWithCard(Money amount){
        paid = true;
    }

    public void payWithCash(Money amount, int[] denominators){

        //lägg in det som betalats i kassan

        paid = true;
    }

    public StringBuilder getReceipt(){
        if(paid){
            StringBuilder receipt = new StringBuilder();
            for(Item item : items){
                //System.out.println(items);
                receipt.append(item.getName())
                        .append("   ")
                        .append(item.getPrice())
                        .append("\n");
                //System.out.println("added " + item.getName());
            }
            for(Deductor deductor : deductors){
                receipt.append(deductor.getType())
                        .append("   ")
                        .append(deductor.getAmount())
                        .append("\n");
            }
            receipt.append("Totalt:   ")
                    .append(getTotalPrice());
            return receipt;
        }else {
            throw new IllegalStateException("Betala för att få kvittot");
        }
    }
}
