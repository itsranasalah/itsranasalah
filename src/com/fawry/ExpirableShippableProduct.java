package com.fawry;

import java.time.LocalDate;

public class ExpirableShippableProduct extends ExpirableProduct implements Shippable {
    private final double weight;

    public ExpirableShippableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        this.weight = weight;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
