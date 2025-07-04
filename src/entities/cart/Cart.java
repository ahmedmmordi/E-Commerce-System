package entities.cart;

import entities.product.Product;
import entities.interfaces.Expirable;

import java.util.ArrayList;
import java.util.List;

// Holds all cart items and some basic operations
public class Cart {
    private List<CartItem> items = new ArrayList<>();
    public void addProduct(Product product, int quantity) {
        // Check quantity
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        // Check expiry
        if (product instanceof Expirable) {
            Expirable expirable = (Expirable) product;
            if (expirable.isExpired()) {
                throw new IllegalArgumentException("Product expired: " + product.getName());
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
      return this.items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Calculates the total price of all items in the cart by summing their individual total prices
    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}