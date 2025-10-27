package org.example;

public class Deductor {

    //rabatt, bonuscheckar, presentkort
    private double amount;
    private String type;

    public Deductor(double amount, String type){
        this.amount = amount;
        this.type=type;
    }

    public double getAmount(){
        return amount;
    }
    public String getType(){
        return type;
    }

    public void lowerAmount(double subtractedBy){
        this.amount -= subtractedBy;
    }

    public void raiseAmount(double addedBy){
        this.amount += addedBy;
    }

    @Override
    public String toString(){
        return type + amount;
    }
}
