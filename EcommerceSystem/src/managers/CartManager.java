package managers;

import models.Order;
import models.Product;
import utils.ActionLogger;
import utils.ReceiptGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

public class CartManager {
    private String customerName;
    private InventoryManager inventory;
    private Map<Product, Integer> cartItems = new LinkedHashMap<>();

    public CartManager(String customerName, InventoryManager inventory) {
        this.customerName = customerName;
        this.inventory = inventory;
    }

//    public void addToCart(Product product, int quantity) {
//        if (product.getStock() < quantity) {
//            System.out.println("❌ Not enough stock available.");
//            return;
//        }
//
//        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
//        System.out.println("✅ Added to cart.");
//    }
public void addToCart(Product product, int quantity) {
    int currentInCart = cartItems.getOrDefault(product, 0);
    int totalAfterAdd = currentInCart + quantity;

    if (totalAfterAdd > product.getStock()) {
        System.out.println("❌ Cannot add " + quantity + " items. Only " + (product.getStock() - currentInCart) + " more available.");
        return;
    }

    cartItems.put(product, totalAfterAdd);
    System.out.println("✅ Added to cart.");
}


    public void removeFromCart(Product product) {
        if (cartItems.containsKey(product)) {
            cartItems.remove(product);
            System.out.println("🗑️ Removed from cart.");
        } else {
            System.out.println("❌ Product not found in cart.");
        }
    }

    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("🛒 Cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("🧺 Cart Items:");
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice() * quantity;
            total += price;

            System.out.println(quantity + "x " + product.getName() + " - Rs. " + price);
        }

        System.out.println("----------------------------");
        System.out.println("Total: Rs. " + total);
    }

    public void checkout(boolean confirmed, ActionLogger logger) {
        if (cartItems.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
            return;
        }

        if (confirmed) {
            inventory.saveInventoryToFile();
            double total = 0;
            for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                product.setStock(product.getStock() - quantity); // reduce stock
                total += product.getPrice() * quantity;
            }

            Order order = new Order(customerName, cartItems);
            ReceiptGenerator receiptGen = new ReceiptGenerator();
            receiptGen.saveReceipt(order, customerName);

            logger.log("Ordered: " + cartItems.size() + " item(s) | Total: Rs. " + total + " | Status: Completed");
            System.out.println("✅ Order confirmed! Thank you.");

            cartItems.clear(); // empty the cart
        } else {
            System.out.println("❌ Order cancelled. Items restored to inventory.");
            cartItems.clear(); // cancel: just empty cart
        }
    }
}
