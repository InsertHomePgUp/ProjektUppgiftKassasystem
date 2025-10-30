package org.example;

import java.util.*;

public class Transaction {

    private double totalPrice;
    private final List<Item> items;
    private final List <Deductor> deductors;
    private boolean paid;
    private final Customer customer;
    private final long minorUnitConversion = 100;

    public Transaction(List<Item> items, List<Deductor> deductors, Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("Customer can not be null");
        }
        this.items = items;
        this.deductors = deductors;
        this.paid = false;
        this.customer = customer;
    }

    public boolean isPaid(){
        return this.paid;
    }

    public double getTotalPrice(){

        totalPrice = 0;

        for (Item item : items){
            totalPrice += item.getPrice().getAmountInMinorUnit();
        }
        for (Deductor deductor : deductors){
            switch (deductor.getType()){
                case("Presentkort"):

                case("Bonuscheck"):
                    totalPrice -= deductor.getAmount()*minorUnitConversion;
                    break;

                case("Rabatt"):
                    totalPrice -= totalPrice * (deductor.getAmount() * 0.01);
                    break;

                default:
                        throw new IllegalArgumentException("Invalid deductor");
            }
        }
        if(totalPrice < 0){
            totalPrice = 0;
        }

        return totalPrice / minorUnitConversion;
    }

    public void applyDeductors() {
        totalPrice = 0;

        for (Item item : items){
            totalPrice += item.getPrice().getAmountInMinorUnit();
        }
        for (Deductor deductor : deductors) {
            switch (deductor.getType()) {
                case ("Presentkort"):
                    if (totalPrice - deductor.getAmount() * minorUnitConversion < 0) {
                        deductor.lowerAmount(totalPrice / minorUnitConversion);
                        totalPrice = 0;
                    } else {
                        totalPrice -= deductor.getAmount() * minorUnitConversion;
                        deductor.lowerAmount(deductor.getAmount());
                    }
                    break;

                case ("Bonuscheck"):
                    totalPrice -= deductor.getAmount() * minorUnitConversion;
                    if (deductor.getAmount() <= totalPrice) {
                        customer.addOrSubtractBonusPoints(-(long) deductor.getAmount());
                    } else {
                        customer.addOrSubtractBonusPoints((long) totalPrice);
                    }
                    customer.addOrSubtractBonusPoints(-(long) deductor.getAmount());
                    break;

                case ("Rabatt"):
                    totalPrice -= totalPrice * (deductor.getAmount() * 0.01);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid deductor");
            }
        }
    }

    public void payWithCard(){
        customer.addOrSubtractBonusPoints((long)getTotalPrice() / minorUnitConversion);
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

            int amountOwed = (int)(amountGiven - getTotalPrice());

            List<Integer> sortedDenoms = new ArrayList<>(register.getInventory().keySet());
            Collections.sort(sortedDenoms, Collections.reverseOrder());

            while (amountOwed > 0) {
                boolean gaveChange = false;

                for (int denom : sortedDenoms) {

                    if (denom <= amountOwed && register.getInventory().get(denom) != null) {
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
            customer.addOrSubtractBonusPoints((long)getTotalPrice() / minorUnitConversion);
            return change;
        }

        throw new IllegalArgumentException("Payment not sufficient");
    }

    public void makeCustomerMember(){
        customer.setMembership(new Membership());
        customer.addOrSubtractBonusPoints((long)getTotalPrice() / minorUnitConversion);
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
