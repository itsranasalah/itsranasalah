package com.fawry;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // create products
        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 100, 5, LocalDate.now().plusDays(5), 0.2);
        ExpirableShippableProduct biscuits = new ExpirableShippableProduct("Biscuits", 150, 3, LocalDate.now().plusDays(5), 0.7);
        ShippableProduct tv = new ShippableProduct("TV", 300, 3, 5.0);
        SimpleProduct scratchCard = new SimpleProduct("Scratch Card", 50, 10);

        Customer customer = new Customer("Ahmed", 500);

        Cart cart = new Cart();
        cart.addProduct(cheese, 2);
        cart.addProduct(biscuits, 1);
        cart.addProduct(scratchCard, 1);
        // tv not added to replicate example output

        CheckoutService service = new CheckoutService();
        service.checkout(customer, cart);

        System.out.printf("Customer balance after payment: %.0f\n", customer.getBalance());
    }
}
