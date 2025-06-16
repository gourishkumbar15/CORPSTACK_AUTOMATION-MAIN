package generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import java.time.Duration;

import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.Description;

/**
 * BaseTest class that provides common functionality for all test classes.
 * This class handles browser setup, reporting configuration, and test cleanup.
 */
public abstract class BaseTest implements Framework_Constants {
    private static final Logger logger = LoggerUtil.getLogger(BaseTest.class);
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected ExtentTest test;
    protected String url = Test_DataByPropertyFILE.getData("base.url");
    protected String username = Test_DataByPropertyFILE.getData("corpstack_username");
    protected String password = Test_DataByPropertyFILE.getData("corpstack_password");

    /**
     * Get the WebDriver instance
     * 
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Configure reporting before test execution
     */
    @BeforeTest
    @Step("Report Configuration")
    @Description("Setting up the test report configuration")
    public void repConfig() {
        try {
            // Initialize ExtentReportManager
            ExtentReportManager.getInstance();
            logger.info("🚀 Before Test - Extent Reports configured successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to configure extent reports: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Initialize test object for each test class
     */
    @BeforeClass
    @Step("Test Initialization")
    @Description("Initializing test object for reporting")
    public void initTest() {
        try {
            // Create test object for the class
            test = ExtentReportManager.getTest(getClass().getName(), getClass().getSimpleName(), 
                "Test execution for " + getClass().getSimpleName());
            logger.debug("Test object initialized for {}", getClass().getSimpleName());
        } catch (Exception e) {
            logger.error("❌ Failed to initialize test object: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Initialize browser before test class execution
     */
    @BeforeClass
    @Step("Browser Setup")
    @Description("Initializing Chrome browser and setting up WebDriver")
    public void openBrowser(ITestContext context) {
        try {
            logger.info("🔧 Setting up Chrome browser");
            
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            js = (JavascriptExecutor) driver;
            
            // Set driver in test context for ListenerClass
            context.setAttribute("driver", driver);
            logger.debug("WebDriver initialized and set in test context");
            
        } catch (Exception e) {
            logger.error("❌ Failed to initialize browser: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Login to the application before each test method
     * 
     * @throws InterruptedException if the thread is interrupted
     */
    @BeforeMethod
    @Step("Login Process")
    @Description("Logging into the application")
    public void login() throws InterruptedException {
        try {
            // Step 1: Navigate to URL
            driver.get(url);
            logger.info("Login process started - Navigating to URL: {}", url);

            // Step 2: Enter Phone Number
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement phoneField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='phone-no']")));
            phoneField.clear();
            phoneField.sendKeys(username);
            logger.debug("Entered username: {}", username);
            
            // Step 3: Click Next Button
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Next']")));
            nextButton.click();
            logger.debug("Clicked Next button");
            
            // Step 4: Enter Password
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password-field']")));
            passwordField.clear();
            passwordField.sendKeys(password);
            logger.debug("Entered password");
            
            // Step 5: Click Sign In Button
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign In']")));
            signInButton.click();
            logger.debug("Clicked Sign In button");
            
            // Wait for dashboard to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'dashboardLayout')]")));
            logger.info("✅ Login process completed successfully");
            
        } catch (Exception e) {
            logger.error("❌ Login process failed: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Handle test result after each test method
     * 
     * @param result TestNG ITestResult object
     */
    @AfterMethod
    public void handleTestResult(ITestResult result) {
        // Check if the test has the retry analyzer
        boolean hasRetryAnalyzer = result.getMethod().getRetryAnalyzer(result) != null;
        
        // Check if the test will be retried
        boolean willRetry = false;
        
        if (result.getStatus() == ITestResult.FAILURE && hasRetryAnalyzer) {
            // Get the retry analyzer
            RetryAnalyzer retryAnalyzer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
            
            // Check if the test will be retried
            willRetry = retryAnalyzer.retry(result);
            
            if (willRetry) {
                // Get the current retry count
                int currentRetryCount = retryAnalyzer.getRetryCount(result);
                int maxRetryCount = retryAnalyzer.getMaxRetryCount();
                
                // Don't log as failure if it will be retried
                if (test == null) {
                    test = ExtentReportManager.getCurrentTest();
                }
                if (test != null) {
                    test.log(Status.WARNING, "Test Failed but will be retried: " + result.getThrowable());
                }
                saveScreenshot(driver);
                logger.warn("⚠️ Test failed but will be retried: {} (Attempt {} of {})", 
                           result.getName(), currentRetryCount, maxRetryCount);
                return; // Skip sign out if the test will be retried
            }
        }
        
        // Handle final test result
        if (result.getStatus() == ITestResult.FAILURE) {
            if (test == null) {
                test = ExtentReportManager.getCurrentTest();
            }
            if (test != null) {
                test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            }
            saveScreenshot(driver);
            if (hasRetryAnalyzer) {
                logger.error("❌ Test failed after all retry attempts: {}", result.getName());
            } else {
                logger.error("❌ Test failed: {}", result.getName());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (test == null) {
                test = ExtentReportManager.getCurrentTest();
            }
            if (test != null) {
                // Check if the test passed after retries
                Object retryCountObj = result.getAttribute("RETRY_COUNT");
                if (retryCountObj != null) {
                    int retryCount = (Integer) retryCountObj;
                    test.log(Status.PASS, "Test Passed after " + retryCount + " retry attempts");
                    logger.info("✅ Test passed after {} retry attempts: {}", retryCount, result.getName());
                } else {
                    test.log(Status.PASS, "Test Passed");
                    logger.info("✅ Test passed: {}", result.getName());
                }
            }
        } else {
            if (test == null) {
                test = ExtentReportManager.getCurrentTest();
            }
            if (test != null) {
                test.log(Status.SKIP, "Test Skipped");
                logger.info("⏭️ Test skipped: {}", result.getName());
            }
        }

        // Sign out after test completion
        try {
            signOut();
        } catch (Exception e) {
            logger.error("❌ Failed to sign out after test: {}", e.getMessage(), e);
        }
    }

    /**
     * Sign out from the application
     * @throws InterruptedException 
     */
    @Step("Sign Out Process")
    @Description("Signing out from the application")
    public void signOut() throws InterruptedException {
        try {
            Thread.sleep(3000);
            logger.info("🔍 SignOut Process started");

            // Try multiple possible XPaths for profile icon
            WebElement profileIcon = null;
            try {
                profileIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'profileIcon')]")));
                logger.debug("Found profile icon using first XPath");
            } catch (Exception e) {
                try {
                    profileIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'hw-avatar-container')]")));
                    logger.debug("Found profile icon using second XPath");
                } catch (Exception e2) {
                    profileIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'user-avatar')]")));
                    logger.debug("Found profile icon using third XPath");
                }
            }
            
            // Try JavaScript click if regular click fails
            try {
                profileIcon.click();
                logger.debug("Clicked profile icon using regular click");
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", profileIcon);
                logger.debug("Clicked profile icon using JavaScript click");
            }

            logger.info("🔍 Step 2: Looking for Sign Out option...");
            // Use the simpler XPath for Sign Out
            WebElement signOutOption = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(text(),'Sign Out')]")));
            
            // Try JavaScript click if regular click fails
            try {
                logger.debug("⏳ Attempting regular click on Sign Out...");
                signOutOption.click();
            } catch (Exception e) {
                logger.debug("⚠️ Regular click failed, trying JavaScript click...");
                js.executeScript("arguments[0].click();", signOutOption);
            }

            // Wait for login page to be ready after sign out
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone-no")));
            logger.info("✅ Login page loaded after sign out");
            
        } catch (Exception e) {
            logger.error("❌ Sign out failed: {}", e.getMessage(), e);
            saveScreenshot(driver);
            // Don't throw the exception, just log it
        }
    }
    
    /**
     * Close browser after test class execution
     */
    @AfterClass
    @Step("Browser Cleanup")
    @Description("Closing the browser after test execution")
    public void closeBrowser() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("✅ Browser closed successfully");
            }
        } catch (Exception e) {
            logger.error("❌ Failed to close browser: {}", e.getMessage(), e);
        }
    }

    /**
     * Finalize and close the test report after test execution
     */
    @AfterTest
    @Step("Report Cleanup")
    @Description("Finalizing and closing the test report")
    public void repClose() {
        try {
            ExtentReportManager.flush();
            logger.info("📊 Test Report generated successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to generate extent report: {}", e.getMessage(), e);
        }
    }

    /**
     * Take screenshot and attach to Allure report
     * 
     * @param driver WebDriver instance
     * @return Screenshot as byte array
     */
    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    
    /**
     * Report test pass with message
     * 
     * @param testId Test case ID
     * @param message Success message
     */
    protected void reportPass(String testId, String message) {
        if (test == null) {
            test = ExtentReportManager.getCurrentTest();
            if (test == null) {
                test = ExtentReportManager.getTest(getClass().getName(), getClass().getSimpleName(), 
                    "Test execution for " + getClass().getSimpleName());
            }
        }
        test.log(Status.PASS, testId + ": " + message);
        logger.info("✅ {}: {}", testId, message);
    }
    
    /**
     * Report test failure with message
     * 
     * @param testId Test case ID
     * @param message Failure message
     */
    protected void reportFail(String testId, String message) {
        if (test == null) {
            test = ExtentReportManager.getCurrentTest();
            if (test == null) {
                test = ExtentReportManager.getTest(getClass().getName(), getClass().getSimpleName(), 
                    "Test execution for " + getClass().getSimpleName());
            }
        }
        test.log(Status.FAIL, testId + ": " + message);
        logger.error("❌ {}: {}", testId, message);
        saveScreenshot(driver);
    }
}
