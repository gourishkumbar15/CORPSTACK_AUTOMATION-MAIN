package pom_scripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import generic.BasePage;
import generic.LoggerUtil;
import org.slf4j.Logger;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.qameta.allure.Attachment;

/**
 * Page Object class for Sign Out functionality
 */
public class SignOutPOM extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(SignOutPOM.class);
    
    // XPath locators for profile icon
    private static final String[] PROFILE_ICON_XPATHS = {
        "//div[contains(@class,'profileIcon')]",
        "//div[contains(@class,'hw-avatar-container')]",
        "//div[contains(@class,'user-avatar')]"
    };
    
    // XPath for sign out option
    private static final String SIGN_OUT_XPATH = "//div[contains(text(),'Sign Out')]";
    
    // XPath for login page verification
    private static final String LOGIN_PAGE_XPATH = "//input[@id='phone-no']";
    
    /**
     * Constructor to initialize SignOutPOM with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public SignOutPOM(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Sign out from the application
     * 
     * @throws InterruptedException if the thread is interrupted
     */
    @Step("Sign Out Process")
    @Description("Signing out from the application")
    public void signOut() throws InterruptedException {
        try {
            logger.info("🔍 Starting Sign Out Process");
            
            // Find and click profile icon
            WebElement profileIcon = findProfileIcon();
            clickProfileIcon(profileIcon);
            
            // Find and click sign out option
            WebElement signOutOption = findSignOutOption();
            clickSignOutOption(signOutOption);
            
            // Verify successful sign out
            verifySignOut();
            
            logger.info("✅ Sign Out Process completed successfully");
            
        } catch (Exception e) {
            logger.error("❌ Sign Out Process failed: {}", e.getMessage(), e);
            saveScreenshot(driver);
            throw e;
        }
    }
    
    /**
     * Find profile icon using multiple possible XPaths
     * 
     * @return WebElement representing the profile icon
     */
    private WebElement findProfileIcon() {
        WebElement profileIcon = null;
        for (String xpath : PROFILE_ICON_XPATHS) {
            try {
                profileIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                logger.debug("Found profile icon using XPath: {}", xpath);
                break;
            } catch (Exception e) {
                logger.debug("Profile icon not found using XPath: {}", xpath);
            }
        }
        
        if (profileIcon == null) {
            throw new RuntimeException("Profile icon not found using any of the defined XPaths");
        }
        
        return profileIcon;
    }
    
    /**
     * Click profile icon using regular or JavaScript click
     * 
     * @param profileIcon WebElement representing the profile icon
     */
    private void clickProfileIcon(WebElement profileIcon) {
        try {
            waitForElementToBeClickable(profileIcon);
            profileIcon.click();
            logger.debug("Clicked profile icon using regular click");
        } catch (Exception e) {
            logger.debug("Regular click failed, trying JavaScript click");
            jsClick(profileIcon);
        }
    }
    
    /**
     * Find sign out option
     * 
     * @return WebElement representing the sign out option
     */
    private WebElement findSignOutOption() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SIGN_OUT_XPATH)));
    }
    
    /**
     * Click sign out option using regular or JavaScript click
     * 
     * @param signOutOption WebElement representing the sign out option
     */
    private void clickSignOutOption(WebElement signOutOption) {
        try {
            waitForElementToBeClickable(signOutOption);
            signOutOption.click();
            logger.debug("Clicked sign out option using regular click");
        } catch (Exception e) {
            logger.debug("Regular click failed, trying JavaScript click");
            jsClick(signOutOption);
        }
    }
    
    /**
     * Verify successful sign out by checking login page
     */
    private void verifySignOut() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGIN_PAGE_XPATH)));
        logger.debug("Login page loaded after sign out");
    }

    /**
     * Take screenshot and attach to Allure report
     * 
     * @param driver WebDriver instance
     * @return Screenshot as byte array
     */
    @Attachment(value = "Page Screenshot", type = "image/png")
    private byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
} 