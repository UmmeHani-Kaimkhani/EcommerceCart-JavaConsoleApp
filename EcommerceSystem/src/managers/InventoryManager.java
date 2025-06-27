package managers;

import models.Product;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class InventoryManager {
    private List<Product> products = new ArrayList<>();
    private final String INVENTORY_FILE = "inventory.dat";

    public InventoryManager() {
        loadInventoryFromFile();
    }

    public void addProduct(Product product) {
        // Check for duplicate ID
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(product.getId())) {
                System.out.println("❌ Product with ID '" + product.getId() + "' already exists.");
                return;
            }
            if (!product.getId().matches("^P\\d{3}$")) {
                System.out.println("❌ Invalid Product ID format. Use 'P###' (e.g., P101)");
                return;
            }
        }

        products.add(product);
        saveInventoryToFile();
        System.out.println("✅ Product added successfully.");
    }


    public void removeProduct(String id) {
        products.removeIf(p -> p.getId().equalsIgnoreCase(id));
        saveInventoryToFile();
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void showInventory() {
        if (products.isEmpty()) {
            System.out.println("❌ No products in inventory.");
            return;
        }

        System.out.println("📦 Available Products:");
        for (Product p : products) {
            System.out.println(p);
            if (p.getStock() < 5) {
                System.out.println("⚠️ Low Stock Alert: Only " + p.getStock() + " left!");
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void saveInventoryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.out.println("❌ Error saving inventory to file.");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadInventoryFromFile() {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            products = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading inventory from file.");
        }
    }
}

