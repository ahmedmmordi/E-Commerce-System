package entities.product;

// The base product class which has only the name, quantity, and price
public class Product {
    protected String name;
    protected int quantity;
    protected double price;

    public Product (String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName () {
        return name;
    }

    public int getQuantity () {
        return quantity;
    }

    public double getPrice () {
        return price;
    }

    // Reduce the quantity when item is sold
    public void reduceQuantity (int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
        }
        else {
            throw new IllegalArgumentException("Not enough quantity available for product: " + this.name);
        }
    }
}