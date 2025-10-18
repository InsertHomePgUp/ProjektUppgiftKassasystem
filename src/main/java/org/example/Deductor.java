package org.example;

public class Deductor {

    //rabatt, bonuscheckar, presentkort
    private float amount;
    private String type;

    public Deductor(int amount, String type){
        this.amount = amount;
        this.type=type;
    }

    public float getAmount(){
        return amount;
    }
    public String getType(){
        return type;
    }

    //100 intar = 1 kr
    //returnerade intar men tänkte att det kan bli decimaler när man delar med 100
    //så gjorde om att den returnerade en double
    public double checkBonus(Customer customer){
        double bonusPointsInMoney = (double) customer.getBonusPoints() /100;
        customer.addOrSubtractBonusPoints(customer.getBonusPoints());
        return bonusPointsInMoney;
    }

    @Override
    public String toString(){
        return type + amount;
    }
}
