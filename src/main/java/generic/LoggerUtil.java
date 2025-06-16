package generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging throughout the application.
 * This class provides methods to get logger instances for different classes.
 */
public class LoggerUtil {
    
    /**
     * Private constructor to prevent instantiation
     */
    private LoggerUtil() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Get a logger instance for the specified class
     * 
     * @param clazz The class to get a logger for
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Get a logger instance for the specified name
     * 
     * @param name The name to get a logger for
     * @return Logger instance
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}
