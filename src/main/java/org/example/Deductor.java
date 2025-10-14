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

    @Override
    public String toString(){
        return type + amount;
    }
}
