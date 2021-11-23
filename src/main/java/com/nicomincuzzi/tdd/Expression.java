package com.nicomincuzzi.tdd;

public interface Expression {
    Money reduce(Bank bank, String to);
}
