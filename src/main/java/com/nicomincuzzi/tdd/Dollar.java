package com.nicomincuzzi.tdd;

public class Dollar extends Money {

    public Dollar(int amount, String currency) {
        super(amount, currency);
    }

    @Override
    public Money times(int multiplier) {
        return new Dollar(this.amount * multiplier, this.currency);
    }

}
