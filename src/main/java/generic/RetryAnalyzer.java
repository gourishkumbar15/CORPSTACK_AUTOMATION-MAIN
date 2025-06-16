package generic;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.util.HashMap;
import java.util.Map;

/**
 * RetryAnalyzer class to retry failed tests
 * This class implements IRetryAnalyzer interface to provide retry functionality for failed tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    
    // Maximum retry count
    private int maxRetryCount = 2;
    
    // Map to store retry count for each test method
    private Map<String, Integer> retryCountMap = new HashMap<>();
    
    /**
     * This method is called when a test fails
     * 
     * @param result The result of the test method that just ran
     * @return true if the test method should be retried, false otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        // Get the unique identifier for the test method
        String testMethodId = getTestMethodId(result);
        
        // Get the current retry count for this test method
        int retryCount = retryCountMap.getOrDefault(testMethodId, 0);
        
        if (retryCount < maxRetryCount) {
            System.out.println("⚠️ Retrying test: " + result.getName() + " for the " + (retryCount + 1) + " time out of " + maxRetryCount);
            retryCountMap.put(testMethodId, retryCount + 1);
            
            // Store the retry count in the test result attribute for reporting
            result.setAttribute("RETRY_COUNT", retryCount + 1);
            
            return true;
        }
        return false;
    }
    
    /**
     * Get the current retry count for a test method
     * 
     * @param result The test result
     * @return The current retry count
     */
    public int getRetryCount(ITestResult result) {
        String testMethodId = getTestMethodId(result);
        return retryCountMap.getOrDefault(testMethodId, 0);
    }
    
    /**
     * Get the maximum retry count
     * 
     * @return The maximum retry count
     */
    public int getMaxRetryCount() {
        return maxRetryCount;
    }
    
    /**
     * Get a unique identifier for the test method
     * 
     * @param result The test result
     * @return A unique identifier for the test method
     */
    private String getTestMethodId(ITestResult result) {
        return result.getTestClass().getName() + "." + result.getMethod().getMethodName();
    }
}
