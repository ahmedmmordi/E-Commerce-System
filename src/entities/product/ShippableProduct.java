package entities.product;

import entities.interfaces.Shippable;

// Extends base product with shipping capability
public class ShippableProduct extends Product implements Shippable {
    private double weight; // In kilograms

    public ShippableProduct (String name, int quantity, double price, double weight) {
        super(name, quantity, price);
        this.weight = weight;
    }

    @Override
    public double getWeight () {
        return this.weight;
    }
}