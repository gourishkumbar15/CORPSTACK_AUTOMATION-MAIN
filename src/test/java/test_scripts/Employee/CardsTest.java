package test_scripts.Employee;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import generic.BaseTest;
import pom_scripts.Employee.CardsPagePOM;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for Cards
 */
public class CardsTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CardsTest.class);
    @Test(description = "Card actions Enable and Disable", 
    testName = "TC013_CardAction_Enable_Disable")
    @Description("Test card enable/disable functionality")
    @Epic("Employee")
    @Feature("Cards")
    @Story("Card actions Enable and Disable")
    @Severity(SeverityLevel.NORMAL)
    public void testCardActionEnableDisable() throws InterruptedException {
        try {
        CardsPagePOM page = new CardsPagePOM(driver);
        logger.info("🔍 Starting Card Enable/Disable Test...");
        Reporter.log("🔍 Starting Card Enable/Disable Test...");

        // Click on Cards & Wallets
        logger.info("Attempting to click on Cards & Wallets link");
        page.clickCardsAndWallets();
        logger.info("Successfully clicked on Cards & Wallets link");
        Reporter.log("✅ Clicked on Cards & Wallets");

        // Get initial card status
        logger.info("Getting initial card status");
        boolean initialStatus = page.isCardEnabled();
        logger.info("Initial card status: {}", initialStatus ? "Enabled" : "Disabled");
        Reporter.log("Initial card status: " + (initialStatus ? "Enabled" : "Disabled"));

        // Toggle card status
        logger.info("Attempting to toggle card status");
        page.toggleCardStatus();
        logger.info("Successfully toggled card status");
        Reporter.log("✅ Toggled card status");

        // Enter comment
        logger.info("Entering comment: Automated Test");
        page.enterComment("Automated Test");
        logger.info("Successfully entered comment");
        Reporter.log("✅ Entered comment: Automated Test");

        // Enter password
        logger.info("Entering password");
        page.enterPassword("Wallet_password");
        logger.info("Successfully entered password");
        Reporter.log("✅ Entered password");

        // Click proceed
        logger.info("Clicking proceed button");
        page.clickProceed();
        logger.info("Successfully clicked proceed button");
        Reporter.log("✅ Clicked proceed button");

        // Get and verify toast message
        logger.info("Getting toast message");
        String toastMessage = page.getToastMessage();
        logger.info("Toast message received: {}", toastMessage);
        Reporter.log("Toast message: " + toastMessage);
        
        Assert.assertNotNull(toastMessage, "Toast message should not be null");
        logger.info("Toast message verification passed");
        Reporter.log("✅ Verified toast message is present");

        // Close toast
        logger.info("Closing toast message");
        page.closeToast();
        logger.info("Successfully closed toast message");
        Reporter.log("✅ Closed toast message");

        // Verify card status has changed
        logger.info("Getting final card status");
        boolean finalStatus = page.isCardEnabled();
        logger.info("Final card status: {}", finalStatus ? "Enabled" : "Disabled");
        Reporter.log("Final card status: " + (finalStatus ? "Enabled" : "Disabled"));
        
        Assert.assertNotEquals(finalStatus, initialStatus, 
            "Card status did not change after toggle operation");
        logger.info("Card status change verification passed");
        Reporter.log("✅ Verified card status has changed");

        logger.info("✅ Card Enable/Disable Test Completed Successfully");
        Reporter.log("✅ Card Enable/Disable Test Completed Successfully");
        } catch (Exception e) {
            logger.error("❌ Test failed with error: {}", e.getMessage(), e);
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
} 