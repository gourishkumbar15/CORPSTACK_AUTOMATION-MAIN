package test_scripts.Corpstack.Privileged;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.CorpBaseTest;
import pom_scripts.Privileged.UsersPagePOM;
import property.Corp_Test_DataByPropertyFILE;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Privileged Users functionality
 */
public class UsersTest extends CorpBaseTest {
    
    /**
     * Test case for exporting wallet limit history
     */
    @Test(priority = 1,description = "Export Wallet Limit History", testName = "TC026_Export_wallet_limi_History_email")
    @Description("Export Wallet Limit History")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Export Wallet Limit History")
    @Severity(SeverityLevel.NORMAL)
    public void testExportWalletLimitHistory() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickExportWalletLimitHistoryOption();
            usersPage.clickDownloadButton();
            
            // Report success
            reportPass("TC026_Export_wallet_limi_History_email", "Successfully exported wallet limit history");
        } catch (Exception e) {
            reportFail("TC026_Export_wallet_limi_History_email", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for loading wallet using Excel
     */
    @Test(priority = 2,description = "Load Wallet using Excel", testName = "TC027_Bulk_Load_Wallet_S3_Download")
    @Description("Load Wallet using Excel")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Load Wallet using Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testBulkLoadWalletS3Download() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickLoadWalletUsingExcelOption();
            usersPage.clickDownloadUserInformationButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().contains("Download initiated"), "Toast message should indicate download initiated");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC027_Bulk_Load_Wallet_S3_Download", "Successfully downloaded user information for bulk wallet load");
        } catch (Exception e) {
            reportFail("TC027_Bulk_Load_Wallet_S3_Download", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for withdrawing wallet using Excel
     */
    @Test(priority = 3,description = "Withdraw Wallet using Excel", testName = "TC029_Bulk_Withdraw_Wallet")
    @Description("Withdraw Wallet using Excel")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Withdraw Wallet using Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testBulkWithdrawWallet() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickWithdrawWalletUsingExcelOption();
            usersPage.clickDownloadUserInformationButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC029_Bulk_Withdraw_Wallet", "Successfully downloaded user information for bulk wallet withdraw");
        } catch (Exception e) {
            reportFail("TC029_Bulk_Withdraw_Wallet", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting users
     */
    @Test(priority = 4,description = "Default - Export users", testName = "TC030_Export_Users_Local")
    @Description("Default - Export users")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Default - Export users")
    @Severity(SeverityLevel.NORMAL)
    public void testExportUsersLocal() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickDownloadIcon();
            usersPage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC030_Export_Users_Local", "Successfully exported users locally");
        } catch (Exception e) {
            reportFail("TC030_Export_Users_Local", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting users to email
     */
    @Test(priority = 5,description = "Email - Export users", testName = "TC031_Export_Users_Email")
    @Description("Email - Export users")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Email - Export users")
    @Severity(SeverityLevel.NORMAL)
    public void testExportUsersEmail() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickDownloadIcon();
            
            // Enter email address - using test email from properties
            Corp_Test_DataByPropertyFILE testData = new Corp_Test_DataByPropertyFILE();
            String testEmail = testData.getTestData("test_email");
            usersPage.enterEmail(testEmail);
            
            usersPage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC031_Export_Users_Email", "Successfully exported users to email");
        } catch (Exception e) {
            reportFail("TC031_Export_Users_Email", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for self/single user wallet load
     */
    @Test(priority = 6,description = "Self/ Single user Wallet load", testName = "TC032_Self_User_Wallet_Load")
    @Description("Self/ Single user Wallet load")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Self/ Single user Wallet load")
    @Severity(SeverityLevel.NORMAL)
    public void testSelfUserWalletLoad() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Get test data
            Corp_Test_DataByPropertyFILE testData = new Corp_Test_DataByPropertyFILE();
            String password = testData.getTestData("wallet_password");
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickSelfWalletLoadOption();
            //usersPage.clickImprestOption();
            usersPage.enterAmount("1");
            usersPage.enterPassword(password);
            usersPage.enterComment("Automated Load");
            usersPage.clickSubmitButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().length() > 0, "Toast message should appear after wallet load");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC032_Self_User_Wallet_Load", "Successfully loaded wallet for self user");
        } catch (Exception e) {
            reportFail("TC032_Self_User_Wallet_Load", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for self/single user wallet unload
     */
    @Test(priority = 7,description = "Self/Single user Wallet Unload", testName = "TC033_Self_User_Wallet_Withdraw")
    @Description("Self/Single user Wallet Unload")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Self/Single user Wallet Unload")
    @Severity(SeverityLevel.NORMAL)
    public void testSelfUserWalletWithdraw() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Get test data
            Corp_Test_DataByPropertyFILE testData = new Corp_Test_DataByPropertyFILE();
            String password = testData.getTestData("wallet_password");
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickSelfWalletWithdrawOption();
            usersPage.enterAmount("1");
            usersPage.enterPassword(password);
            usersPage.enterComment("Automated Load");
            usersPage.clickSubmitButton();
            
            // Verify toast message appears
            Assert.assertTrue(usersPage.getToastMessageText().length() > 0, "Toast message should appear after wallet withdraw");
            
            // Close toast message
            usersPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC033_Self_User_Wallet_Withdraw", "Successfully withdrew wallet for self user");
        } catch (Exception e) {
            reportFail("TC033_Self_User_Wallet_Withdraw", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for updating wallet limits using Excel
     */
    @Test(priority = 8,description = "Update Wallet Limits using Excel", testName = "TC034_Update_limit_download__attachment")
    @Description("Update Wallet Limits using Excel")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Update Wallet Limits using Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLimitDownloadAttachment() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickMoreButton();
            usersPage.clickUpdateWalletLimitsUsingExcelOption();
            usersPage.clickDownloadUserInformationButton();
            
            // Report success
            reportPass("TC034_Update_limit_download__attachment", "Successfully downloaded user information for updating wallet limits");
        } catch (Exception e) {
            reportFail("TC034_Update_limit_download__attachment", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for setting wallet limit
     */
    @Test(priority = 9,description = "Set wallet limit", testName = "TC036_Set_user_wallet_limit")
    @Description("Set wallet limit")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Set wallet limit")
    @Severity(SeverityLevel.NORMAL)
    public void testSetUserWalletLimit() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickUserPopupIcon();
            usersPage.clickSetLimitOption();
            usersPage.clickeditLimitButton();
            usersPage.enterCount("11");
            usersPage.clickSaveButton();
            
            // Report success
            reportPass("TC036_Set_user_wallet_limit", "Successfully set wallet limit for user");
        } catch (Exception e) {
            reportFail("TC036_Set_user_wallet_limit", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting user with user status filter
     */
    @Test(priority = 10,description = "Search by - User status - Export", testName = "TC037_Export_user_with_userStatus_filter")
    @Description("Search by - User status - Export")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Search by - User status - Export")
    @Severity(SeverityLevel.NORMAL)
    public void testExportUserWithUserStatusFilter() {
        try {
            // Create page object
            UsersPagePOM usersPage = new UsersPagePOM(driver);
            
            // Test steps
            usersPage.clickPrivilegedLink();
            usersPage.clickUsersLink();
            usersPage.clickActiveCheckbox();
            usersPage.clickApplyFiltersButton();
            usersPage.clickDownloadIcon();
            usersPage.clickDownloadButton();
            
            // Report success
            reportPass("TC037_Export_user_with_userStatus_filter", "Successfully exported users with user status filter");
        } catch (Exception e) {
            reportFail("TC037_Export_user_with_userStatus_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}