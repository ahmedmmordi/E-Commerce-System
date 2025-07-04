package entities.product;

import entities.interfaces.Shippable;
import entities.interfaces.Expirable;

import java.time.LocalDate;

// Product that is both shippable and perishable
public class PerishableShippableProduct extends Product implements Shippable, Expirable {
    private LocalDate expiryDate; // Expiry date of the product
    private double weight; // Weight of the product in kilograms

    public PerishableShippableProduct(String name, int quantity, double price, LocalDate expiryDate, double weight) {
        super(name, quantity, price);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
      return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public double getWeight() {
      return this.weight;
    }
}