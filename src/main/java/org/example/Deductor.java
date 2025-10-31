package org.example;

public class Deductor {

    //rabatt, bonuscheckar, presentkort
    private double amount;
    private final String type;

    public Deductor(double amount, String type){
        if ((type.equalsIgnoreCase("Presentkort")) ||
                (type.equalsIgnoreCase("Bonuscheck")) ||
                (type.equalsIgnoreCase("Rabatt"))) {
            this.amount = amount;
            this.type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        }else{
            throw new IllegalArgumentException("Type has to be Presentkort, Bonuscheck or Rabatt");
        }
    }

    public double getAmount(){
        return amount;
    }
    public String getType(){
        return type;
    }

    public void lowerAmount(double subtractedBy){
        this.amount -= subtractedBy;
        if(this.amount < 0){
            this.amount = 0;
        }
    }

    public void raiseAmount(double addedBy){
        this.amount += addedBy;
        if(this.amount < 0){
            this.amount = 0;
        }
    }

    @Override
    public String toString(){
        return type + amount;
    }
}
