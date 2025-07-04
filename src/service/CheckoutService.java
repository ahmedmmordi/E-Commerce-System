package service;

import entities.cart.Cart;
import entities.cart.CartItem;
import entities.interfaces.Shippable;
import entities.product.Product;
import entities.user.Customer;

import java.util.ArrayList;
import java.util.List;

// Handles checkout operations
public class CheckoutService {
    private ShippingService shippingService = new ShippingService();

    // Flat shipping fee if there are shippable items
    private double calculateShippingCost(Cart cart) {
        double weight = 0;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                Shippable s = (Shippable) item.getProduct();
                weight += s.getWeight() * item.getQuantity();
            }
        }
        return weight > 0 ? 30 : 0;
    }

    // Collect all shippable items for shipping service
    private List<Shippable> collectShippables(Cart cart) {
        List<Shippable> shippables = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                Shippable s = (Shippable) item.getProduct();
                for (int i = 0; i < item.getQuantity(); i++) {
                    if (s.getWeight() > 0) shippables.add(s);
                }
            }
        }
        return shippables;
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        double subTotal = cart.getSubtotal();
        double shippingCost = calculateShippingCost(cart);
        double total = subTotal + shippingCost;

        if (customer.getBalance() < total) {
            throw new IllegalArgumentException("Insufficient balance for customer: " + customer.getName());
        }

        // Reduce product stock
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        customer.pay(total);

        List<Shippable> shippables = collectShippables(cart);
        if (!shippables.isEmpty()) {
            shippingService.shipItems(shippables);
        }

        printReceipt(cart, subTotal, shippingCost, total, customer);
    }

    // Print final receipt to console
    private void printReceipt(Cart cart, double subtotal, double shipping, double total, Customer customer) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
          if (item.getProduct().getPrice() <= 0) continue; // Skip free items
            System.out.printf("%dx %s %.0f%n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shipping);
        System.out.printf("Amount %.0f%n", total);
        System.out.printf("Customer balance now: %.0f%n", customer.getBalance());
    }
}