package com.fawry;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private final ShippingService shippingService = new ShippingService();

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        List<Shippable> shippableItems = new ArrayList<>();
        double subtotal = 0.0;

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int qty = item.getQuantity();
            // check expiry
            if (product instanceof ExpirableProduct) {
                if (((ExpirableProduct) product).isExpired()) {
                    throw new IllegalStateException(product.getName() + " is expired");
                }
            }
            if (qty > product.getQuantity()) {
                throw new IllegalStateException(product.getName() + " is out of stock");
            }
            subtotal += product.getPrice() * qty;
            product.reduceQuantity(qty);
            // prepare shippable items
            if (product instanceof Shippable) {
                Shippable shipItem = (Shippable) product;
                for (int i = 0; i < qty; i++) {
                    shippableItems.add(shipItem);
                }
            }
        }

        double shippingFee = shippingService.calculateShippingFee(shippableItems);
        double amount = subtotal + shippingFee;
        if (amount > customer.getBalance()) {
            throw new IllegalStateException("Customer's balance is insufficient");
        }

        customer.deductBalance(amount);

        shippingService.ship(shippableItems);
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f\n", item.getQuantity(), item.getProduct().getName(), item.getProduct().getPrice() * item.getQuantity());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f\n", subtotal);
        System.out.printf("Shipping %.0f\n", shippingFee);
        System.out.printf("Amount %.0f\n", amount);
        System.out.println("END.");
    }
}
