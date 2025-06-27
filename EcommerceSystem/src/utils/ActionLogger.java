package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActionLogger {
    private final String filename;

    public ActionLogger(String username, String role) {
        this.filename = role.toLowerCase() + "_log_" + username + ".txt";
    }

    public void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String logLine = String.format("[%s] %s\n", timestamp, message);

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(logLine);
        } catch (IOException e) {
            System.out.println("❌ Error writing to action log.");
        }
    }
}
