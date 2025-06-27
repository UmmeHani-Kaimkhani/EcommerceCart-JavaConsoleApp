package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    private String customerName;
    private Map<Product, Integer> items;
    private LocalDateTime dateTime;

    public Order(String customerName, Map<Product, Integer> items) {
        this.customerName = customerName;
        this.items = new LinkedHashMap<>(items); // preserve order
        this.dateTime = LocalDateTime.now();
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Date: ").append(dateTime.format(formatter)).append("\n\n");
        sb.append("Items:\n");

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            sb.append(quantity).append("x ").append(product.getName())
                    .append("    Rs. ").append(product.getPrice() * quantity).append("\n");
        }

        sb.append("----------------------------\n");
        sb.append("Total:             Rs. ").append(getTotal()).append("\n");

        return sb.toString();
    }
}