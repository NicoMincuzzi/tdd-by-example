package com.nicomincuzzi.tdd;

public class Money implements Expression {
    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(this.amount * multiplier, this.currency);
    }

    protected String currency() {
        return currency;
    }

    public Expression plus(Money addend) {
        return new Sum(this, addend);
    }

    public Money reduce(String to) {
        int rate = (currency.equals("CHF") && to.equals("USD"))
                ? 2
                : 1;
        return new Money(amount / rate, to);
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
