package Server.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    FileLogger logger;
    private final String className;
    private static String logFile;
    private static final String logDir = "logs";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String timestamp = LocalDateTime.now().format(FORMATTER);

    public FileLogger(Class<?> name) {
        this.className = name.getSimpleName();
        File directory = new File(logDir);
        // Create a directory if it does not exist
        if (!directory.exists()){
            directory.mkdir();
        }
        // name the log file as the classname
        this.logFile = logDir + File.separator + className + ".log";
    }

    public void info(String message) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(String.format("[%s] [INFO] %s\n", timestamp, message));
        } catch (IOException e) {
           logger.warning("Failed to write to log file: " + e.getMessage());
        }
    }

    public void warning(String message) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(String.format("[%s] [WARNING] %s\n", timestamp, message));
        } catch (IOException e) {
            logger.warning("Failed to write to log file: " + e.getMessage());
        }
    }

    public void severe(String message) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(String.format("[%s] [SEVERE] %s\n", timestamp, message));
        } catch (IOException e) {
            logger.warning("Failed to write to log file: " + e.getMessage());
        }
    }
}