package org.example;

public class Deductor {

    //rabatt, bonuscheckar, presentkort
    private int amount;
    private String type;

    public Deductor(int amount, String type){
        this.amount = amount;
        this.type=type;
    }

    public int getAmount(){
        return amount;
    }
    public String getType(){
        return type;
    }
}
