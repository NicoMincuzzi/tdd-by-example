package com.nicomincuzzi.tdd;

public class Franc extends Money {

    public Franc(int amount, String currency) {
        super(amount, currency);
    }

    @Override
    public Money times(int multiplier) {
        return new Money(this.amount * multiplier, this.currency);
    }

}
