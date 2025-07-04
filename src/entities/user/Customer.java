package entities.user;

// Customer with basic details
public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    // Pay for an order and deduct balance
    public void pay(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
        }
        else {
            throw new IllegalArgumentException("Insufficient balance for customer: " + this.name);
        }
    }
}