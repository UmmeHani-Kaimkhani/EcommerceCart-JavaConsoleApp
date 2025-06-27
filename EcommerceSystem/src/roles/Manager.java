package roles;

import managers.InventoryManager;
import models.Product;
import utils.ActionLogger;

import java.util.Scanner;

public class Manager {
    private String name;
    private InventoryManager inventory;
    private ActionLogger logger;

    public Manager(String name, InventoryManager inventory) {
        this.name = name;
        this.inventory = inventory;
        this.logger = new ActionLogger("manager_log_" + name + ".txt", "Manager");
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n🔧 Manager Menu:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    inventory.showInventory();
                    break;

                case 2:
                    System.out.print("Enter Product ID (Format: P###): ");
                    String id = sc.nextLine();

                    // Check ID format: P followed by 3 digits
                    if (!id.matches("^P\\d{3}$")) {
                        System.out.println("❌ Invalid Product ID format. Use 'P###' (e.g., P101)");
                        break;
                    }

                    // Check uniqueness
                    if (inventory.findById(id) != null) {
                        System.out.println("❌ A product with ID '" + id + "' already exists.");
                        break;
                    }

                    System.out.print("Enter Product Name: ");
                    String pname = sc.nextLine();

                    System.out.print("Enter Product Price: ");
                    double price;
                    try {
                        price = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid price.");
                        break;
                    }

                    System.out.print("Enter Product Stock: ");
                    int stock;
                    try {
                        stock = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid stock.");
                        break;
                    }

                    Product newProduct = new Product(id, pname, price, stock);
                    inventory.addProduct(newProduct);
                    logger.log("Added Product: " + pname + " - ID: " + id + " - Stock: " + stock);
                    break;

                case 3:
                    System.out.print("Enter Product ID to remove: ");
                    String removeId = sc.nextLine();

                    Product toRemove = inventory.findById(removeId);
                    if (toRemove != null) {
                        inventory.removeProduct(removeId);
                        logger.log("Removed Product: " + toRemove.getName() + " - ID: " + removeId);
                        System.out.println("✅ Product removed.");
                    } else {
                        System.out.println("❌ Product not found.");
                    }
                    break;

                case 4:
                    inventory.saveInventoryToFile();
                    System.out.println("👋 Logged out.");
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
