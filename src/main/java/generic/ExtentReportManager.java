package generic;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized manager for ExtentReports configuration and test management
 * This class provides a singleton instance of ExtentReports and manages ExtentTest objects
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private static Map<String, ExtentTest> testMap = new HashMap<>();
    private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    
    /**
     * Get the singleton instance of ExtentReports
     * 
     * @return ExtentReports instance
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }
    
    /**
     * Create a new instance of ExtentReports
     */
    private static void createInstance() {
        extent = new ExtentReports();
        
        // Create reports directory if it doesn't exist
        String reportPath = System.getProperty("user.dir") + "/test-output/reports";
        new File(reportPath).mkdirs();
        
        // Create screenshots directory if it doesn't exist
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots";
        new File(screenshotPath).mkdirs();
        
        // Create timestamp for unique report name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportName = reportPath + "/TestReport_" + timestamp + ".html";
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportName);
        
        // Configure the reporter
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Corpstack Test Report");
        sparkReporter.config().setReportName("Corpstack Automation Test Results");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        
        extent.attachReporter(sparkReporter);
        
        // Add system information
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Environment", "PROD");
        extent.setSystemInfo("Application", "Corpstack Portal");
    }
    
    /**
     * Get or create an ExtentTest object for the specified test class and method
     * 
     * @param className The name of the test class
     * @param testName The name of the test method
     * @param description The description of the test
     * @return ExtentTest object
     */
    public static synchronized ExtentTest getTest(String className, String testName, String description) {
        String testKey = className + "." + testName;
        ExtentTest test = testMap.get(testKey);
        
        if (test == null) {
            test = getInstance().createTest(testName, description);
            testMap.put(testKey, test);
        }
        
        currentTest.set(test);
        return test;
    }
    
    /**
     * Get the current ExtentTest object
     * 
     * @return ExtentTest object
     */
    public static ExtentTest getCurrentTest() {
        return currentTest.get();
    }
    
    /**
     * Set the current ExtentTest object
     * 
     * @param test ExtentTest object
     */
    public static void setCurrentTest(ExtentTest test) {
        currentTest.set(test);
    }
    
    /**
     * Remove the current ExtentTest object
     */
    public static void removeTest() {
        currentTest.remove();
    }
    
    /**
     * Flush the ExtentReports instance
     */
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
