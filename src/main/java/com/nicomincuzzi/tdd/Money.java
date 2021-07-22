package com.nicomincuzzi.tdd;

public class Money {
    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(int amount) {
        return new Dollar(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Franc(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(this.amount * multiplier, this.currency);
    }

    protected String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount &&
                currency().equals(money.currency());
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
