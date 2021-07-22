package com.nicomincuzzi.tdd;

public class Dollar extends Money {

    public Dollar(int amount) {
        this.amount = amount;
        currency = "USD";
    }

    @Override
    public Money times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

}
