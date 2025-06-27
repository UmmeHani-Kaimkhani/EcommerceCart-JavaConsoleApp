package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loginlogger {

    public void log(String username, String role, boolean success) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String status = success ? "Success" : "Failure";
        String logLine = String.format("[%s] Username: %s | Role: %s | %s\n", timestamp, username, role, status);

        try (FileWriter writer = new FileWriter("login_log.txt", true)) {
            writer.write(logLine);
        } catch (IOException e) {
            System.out.println("❌ Error writing to login log.");
        }
    }
}
