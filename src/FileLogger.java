import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is the Logger where it logs every transaction that happens in the server
 */
public class FileLogger {

    private static Logger logger;

    // Initialize the logger
    static {
        try {
            // Create a logger instance
            logger = Logger.getLogger(FileLogger.class.getName());

            // Create a FileHandler to write logs to a file
            FileHandler fileHandler = new FileHandler("application.log", true); // true for appending
            fileHandler.setFormatter(new SimpleFormatter()); // Use simple text format

            // Add the FileHandler to the logger
            logger.addHandler(fileHandler);

            // Set the logging level
            logger.setLevel(Level.INFO);

            // Disable logging to the console
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    // Log an INFO message
    public static void info(String message) {
        logger.info(message);
    }

    // Log a WARNING message
    public static void warning(String message) {
        logger.warning(message);
    }

    // Log a SEVERE message
    public static void severe(String message) {
        logger.severe(message);
    }

    // Log an exception
    public static void logException(Throwable throwable) {
        logger.log(Level.SEVERE, "Exception occurred: ", throwable);
    }
}