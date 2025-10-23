package org.example;

import java.util.*;

public class Transaction {

    private double totalPrice;
    private List<Item> items;
    private List <Deductor> deductors;
    private boolean paid;
    private Customer customer;

    public Transaction(List<Item> items, List<Deductor> deductors, Customer customer){
        this.items = items;
        this.deductors = deductors;
        this.paid = false;
        this.customer = customer;
    }

    public double getTotalPrice(){

        totalPrice = 0;

        for (Item item : items){
            totalPrice += item.getPrice().getAmountInMinorUnit();
        }
        for (Deductor deductor : deductors){
            switch (deductor.getType()){
                case("Presentkort"):
                    totalPrice -= deductor.getAmount()*100;
                    break;

                case("Bonuscheck"):
                    totalPrice -= deductor.getAmount()*100;
                    if (deductor.getAmount() <= totalPrice){
                        customer.addOrSubtractBonusPoints(-(long)deductor.getAmount());
                    }else {
                        customer.addOrSubtractBonusPoints((long)totalPrice);
                    }
                    customer.addOrSubtractBonusPoints(-(long)deductor.getAmount());
                    break;

                case("Rabatt"):
                    totalPrice -= totalPrice * (deductor.getAmount() * 0.01);
                    break;

                default:
                        throw new IllegalArgumentException("Invalid deductor");
            }
        }
        //if totalprice < 0 --> totalprice == 0
        return totalPrice / 100;
    }

    public void payWithCard(){
        paid = true;
    }

    public List<Integer> payWithCash(CashRegister register, List<Integer> denominators) {
        int amountGiven = 0;
        ArrayList<Integer> change = new ArrayList<>();

        for (int denom : denominators) {
            amountGiven += denom;
        }

        if (getTotalPrice() == amountGiven) {
            register.addDenominators(denominators);
            paid = true;
            return change;

        } else if (getTotalPrice() < amountGiven) {
            register.addDenominators(denominators);
            double amountOwed = amountGiven - getTotalPrice();

            List<Integer> sortedDenoms = new ArrayList<>(register.getInventory().keySet());
            Collections.sort(sortedDenoms, Collections.reverseOrder());

            while (amountOwed > 0.0) {
                boolean gaveChange = false;

                for (int denom : sortedDenoms) {

                    if (denom <= amountOwed && register.getInventory().get(denom) > 0.0) {
                        register.removeDenominator(denom);
                        change.add(denom);
                        amountOwed -= denom;
                        gaveChange = true;
                        break;
                    }
                }

                if (!gaveChange) {
                    throw new IllegalStateException("Cannot give exact change for " + amountOwed + " kr!");
                }
            }

            paid = true;
            return change;
        }

        throw new IllegalArgumentException("Payment not sufficient");
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

    //ta bort varor från inventory metod

    //
}
