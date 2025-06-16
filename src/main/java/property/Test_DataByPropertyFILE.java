package property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading test data from properties file
 */
public class Test_DataByPropertyFILE {
    
    private static Properties properties;
    private static final String PROPERTY_FILE_PATH = "property/test_data.properties";
    
    static {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PROPERTY_FILE_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Constructor to initialize the properties file
     */
    public Test_DataByPropertyFILE() {
        // Properties already loaded in static block
    }
    
    /**
     * Get test data from properties file
     * 
     * @param key Key to get value for
     * @return Value for the key
     */
    public String getTestData(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get test data from properties file with default value
     * 
     * @param key Key to get value for
     * @param defaultValue Default value to return if key is not found
     * @return Value for the key or default value if key is not found
     */
    public String getTestData(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Static method to get test data from properties file
     * 
     * @param key Key to get value for
     * @return Value for the key
     */
    public static String getData(String key) {
        return properties.getProperty(key);
    }
}
