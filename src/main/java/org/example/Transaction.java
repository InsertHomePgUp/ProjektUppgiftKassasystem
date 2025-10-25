package org.example;

import java.util.*;

public class Transaction {

    private double totalPriceInMinorUnit;
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

        totalPriceInMinorUnit = 0;

        for (Item item : items){
            totalPriceInMinorUnit += item.getPrice().getAmountInMinorUnit();
        }

        for (Deductor deductor : deductors){
            switch (deductor.getType()){

                case("Presentkort"):
                    totalPriceInMinorUnit -= deductor.getAmount()*100;
                    break;

                case("Bonuscheck"):
                    //totalPriceInMinorUnit -= deductor.getAmount()*100;
                    //om antalet bonuspoäng är mindre än totalet
                    if (deductor.getAmount()*100 <= totalPriceInMinorUnit){
                        totalPriceInMinorUnit -= deductor.getAmount()*100;
                        customer.addOrSubtractBonusPoints(-(long)deductor.getAmount()*100);
                    //om man har mer bonuspoäng än kostnaden, alltså att man har poäng över
                    }else {
                        customer.addOrSubtractBonusPoints(-(long)totalPriceInMinorUnit);
                        totalPriceInMinorUnit = 0;
                    }
                    break;

                case("Rabatt"):
                    totalPriceInMinorUnit -= totalPriceInMinorUnit * (deductor.getAmount() * 0.01);
                    break;

                default:
                        throw new IllegalArgumentException("Invalid deductor");
            }
        }
        //if totalprice < 0 --> totalprice == 0
        customer.addOrSubtractBonusPoints((long)totalPriceInMinorUnit/100);
        return totalPriceInMinorUnit / 100;
    }

    public void payWithCard(){
        customer.addOrSubtractBonusPoints((long)getTotalPrice() / 100);
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
            customer.addOrSubtractBonusPoints((long)getTotalPrice() / 100);
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
}
