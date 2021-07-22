package com.nicomincuzzi.tdd;

public class Franc extends Money {

    public Franc(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public Money times(int multiplier) {
        return Money.franc(amount * multiplier);
    }

}
