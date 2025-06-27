package roles;

import managers.CartManager;
import managers.InventoryManager;
import models.Product;
import utils.ActionLogger;

import java.util.Scanner;

public class Customer {
    private String name;
    private InventoryManager inventory;
    private CartManager cart;
    private Scanner sc = new Scanner(System.in);
    private ActionLogger actionLogger;

    public Customer(String name, InventoryManager inventory) {
        this.name = name;
        this.inventory = inventory;
        this.cart = new CartManager(name, inventory);
        this.actionLogger = new ActionLogger(name, "customer");
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n🛍️ Customer Menu");
            System.out.println("1. View Inventory");
            System.out.println("2. Add to Cart");
            System.out.println("3. Remove from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> inventory.showInventory();
                case 2 -> addToCart();
                case 3 -> removeFromCart();
                case 4 -> cart.viewCart();
                case 5 -> checkout();
                case 6 -> System.out.println("👋 Thank you for visiting!");
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 6);
    }

    private void addToCart() {
        System.out.print("Enter product ID: ");
        String id = sc.nextLine();
        Product product = inventory.findById(id);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(sc.nextLine());
        cart.addToCart(product, quantity);
        actionLogger.log("Added to cart: " + quantity + "x " + product.getName() + " (ID: " + product.getId() + ")");
    }

    private void removeFromCart() {
        System.out.print("Enter product ID to remove: ");
        String id = sc.nextLine();
        Product product = inventory.findById(id);
        if (product != null) {
            cart.removeFromCart(product);
            actionLogger.log("Removed from cart: " + product.getName() + " (ID: " + product.getId() + ")");
        } else {
            System.out.println("❌ Product not found.");
        }
    }

    private void checkout() {
        cart.viewCart();
        System.out.print("Do you want to confirm the order? (Y/N): ");
        String response = sc.nextLine();
        boolean confirmed = response.equalsIgnoreCase("Y");
        cart.checkout(confirmed, actionLogger);
    }
}
