package Ecommerce;

import managers.InventoryManager;
import roles.Customer;
import roles.Manager;
import utils.Loginlogger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManager inventory = new InventoryManager();
        Loginlogger loginLogger = new Loginlogger();

        while (true) {
            System.out.println("\n🛒 Welcome to the E-Commerce System");

            System.out.print("Enter your name (or type 'exit' to quit): ");
            String username = sc.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                inventory.saveInventoryToFile(); // ✅ Save on exit
                System.out.println("👋 Exiting system. Goodbye!");
                break;
            }

            System.out.print("Are you a (1) Customer or (2) Manager? Enter 1 or 2: ");
            int roleChoice;

            try {
                roleChoice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter 1 or 2.");
                continue;
            }

            if (roleChoice == 1) {
                loginLogger.log(username, "Customer", true);
                Customer customer = new Customer(username, inventory);
                customer.menu();  // after checkout, returns here and loops to next user
            } else if (roleChoice == 2) {
                System.out.print("Enter manager password: ");
                String password = sc.nextLine();

                if (password.equals("admin123")) {
                    loginLogger.log(username, "Manager", true);
                    Manager manager = new Manager(username, inventory);
                    manager.menu();  // after manager is done, next login starts
                } else {
                    loginLogger.log(username, "Manager", false);
                    System.out.println("❌ Invalid password. Access denied.");
                }
            } else {
                System.out.println("❌ Invalid role choice. Please enter 1 or 2.");
            }
        }
    }
}
