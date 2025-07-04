package entities.cart;

import entities.product.Product;

// Holds the product and its quantity in the cart
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}