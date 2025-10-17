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
    public int checkBonus(Customer customer){
        return customer.getBonus();
    }

    @Override
    public String toString(){
        return type + amount;
    }
}
