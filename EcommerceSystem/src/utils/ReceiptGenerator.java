package utils;

import models.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptGenerator {
    public void saveReceipt(Order order, String customerName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String filename = "receipt_" + customerName + "_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(order.generateReceipt());
            System.out.println("🧾 Receipt saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving receipt.");
        }
    }
}
