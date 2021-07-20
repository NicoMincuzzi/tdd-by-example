package com.nicomincuzzi.tdd;

import java.util.Objects;

public class Money {
    protected int amount;

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount &&
                Objects.equals(getClass(), money.getClass());
    }
}
