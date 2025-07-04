package app;

import entities.cart.Cart;
import entities.product.PerishableShippableProduct;
import entities.product.Product;
import entities.product.ShippableProduct;
import entities.user.Customer;

import service.CheckoutService;

import java.time.LocalDate;

// Testing
public class Main {
    public static void main(String[] args) {
        // Cheese: perishable, shippable, price: 100, weight: 0.2kg
        PerishableShippableProduct cheese = new PerishableShippableProduct("Cheese", 10, 100, LocalDate.now().plusDays(5),
        0.2);

        // Biscuits: perishable, shippable, price: 150, weight: 0.7kg
        PerishableShippableProduct biscuits = new PerishableShippableProduct("Biscuits", 10, 150, LocalDate.now().plusDays(5),
        0.7);

        // TV: shippable, non-perishable, price: 0, weight: 0 (will be ignored)
        ShippableProduct tv = new ShippableProduct("TV", 5, 0, 0.0);

        // Scratch Card: non-shippable, non-perishable, price: 0
        Product scratchCard = new Product("Mmobile Scratch Card", 10, 0);

        Customer customer = new Customer("Ahmed", 500);
        Cart cart = new Cart();

        cart.addProduct(cheese, 2);
        cart.addProduct(tv, 3);
        cart.addProduct(scratchCard, 1);
        cart.addProduct(biscuits, 1);

        CheckoutService checkoutService = new CheckoutService();
        checkoutService.checkout(customer, cart);

        System.out.println("\n---------------------------------------");

        // Testing Some Cornet Cases
        try { // Empty cart
            Cart emptyCart = new Cart();
            checkoutService.checkout(customer, emptyCart);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // Insufficient balance
            Customer brokeCustomer = new Customer("Mohamed", 50); // Not enough money
            cart.addProduct(cheese, 1); // Price 100
            checkoutService.checkout(brokeCustomer, cart);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // Out of stock
            Cart stockCart = new Cart();
            Product fewStock = new Product("Diamond", 1, 100000); // 1 in stock
            stockCart.addProduct(fewStock, 2); // Try to buy 2
            checkoutService = new CheckoutService();
            checkoutService.checkout(customer, stockCart);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // Expired product
            PerishableShippableProduct expiredCheese = new PerishableShippableProduct(
                    "Old Cheese", 10, 100, LocalDate.now().minusDays(1), 0.2
            );
            Cart expiredCart = new Cart();
            expiredCart.addProduct(expiredCheese, 1);
            checkoutService = new CheckoutService();
            checkoutService.checkout(customer, expiredCart);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
