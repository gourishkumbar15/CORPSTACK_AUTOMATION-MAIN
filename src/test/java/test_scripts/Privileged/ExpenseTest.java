package test_scripts.Privileged;

import org.testng.annotations.Test;
import org.testng.Assert;

import generic.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pom_scripts.Privileged.ExpensePagePOM;
import property.Test_DataByPropertyFILE;

public class ExpenseTest extends BaseTest{  

    /**
     * Test case for exporting expenses with default download
     */
    @Test(priority = 1,description = "Export Expenses Default download", testName = "TC043_Emp_Expense_Download_Local")
    @Description("Export Expenses Default download")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Export Expenses Default download")
    @Severity(SeverityLevel.NORMAL)
    public void testEmpExpenseDownloadLocal() {
        try {
            // Create page object
            ExpensePagePOM ExpensePagePOM = new ExpensePagePOM(driver);
            
            // Test steps
            ExpensePagePOM.clickPrivilegedLink();
            ExpensePagePOM.clickExpensesLink();
            ExpensePagePOM.clickDownloadIcon();
            ExpensePagePOM.clickDownloadExpenseReportText();
            ExpensePagePOM.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(ExpensePagePOM.getToastMessageText().length() > 0, "Toast message should appear after download");
            // Close toast message
            ExpensePagePOM.clickCloseToastMessage();
            
            // Report success
            reportPass("TC043_Emp_Expense_Download_Local", "Successfully downloaded expenses locally");
        } catch (Exception e) {
            reportFail("TC043_Emp_Expense_Download_Local", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting expenses to email
     */
    @Test(priority = 2, description = "Export Expenses Email attachment", testName = "TC044_Emp_Expense_Download_Email")
    @Description("Export Expenses Email attachment")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Export Expenses Email attachment")
    @Severity(SeverityLevel.NORMAL)
    public void testEmpExpenseDownloadEmail() {
        try {
            // Create page object
            ExpensePagePOM ExpensePagePOM = new ExpensePagePOM(driver);
            
            // Get test data
            Test_DataByPropertyFILE testData = new Test_DataByPropertyFILE();
            String testEmail = testData.getTestData("test_email");
            
            // Test steps
            ExpensePagePOM.clickPrivilegedLink();
            ExpensePagePOM.clickExpensesLink();
            ExpensePagePOM.clickDownloadIcon();
            ExpensePagePOM.clickDownloadExpenseReportText();
            ExpensePagePOM.enterEmail(testEmail);
            ExpensePagePOM.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(ExpensePagePOM.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            ExpensePagePOM.clickCloseToastMessage();
            
            // Report success
            reportPass("TC044_Emp_Expense_Download_Email", "Successfully downloaded expenses to email");
        } catch (Exception e) {
            reportFail("TC044_Emp_Expense_Download_Email", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for viewing export history success message
     */
    @Test(priority = 3,description = "Export history view and success message", testName = "TC045_Export_History_Success")
    @Description("Export history view and success message")
    @Epic("Privileged")
    @Feature("Users")
    @Story("Export history view and success message")
    @Severity(SeverityLevel.NORMAL)
    public void testExportHistorySuccess() {
        try {
            // Create page object
            ExpensePagePOM ExpensePagePOM = new ExpensePagePOM(driver);
            
            // Test steps
            ExpensePagePOM.clickPrivilegedLink();
            ExpensePagePOM.clickExpensesLink();
            ExpensePagePOM.clickDownloadIcon();
            ExpensePagePOM.clickviewExportHistoryLink();
            Assert.assertTrue(ExpensePagePOM.getToastMessageText().length() > 0, "Toast message should appear after download");
            // Close toast message
            ExpensePagePOM.clickCloseToastMessage();
            
            // Verify toast message appears
            Assert.assertTrue(ExpensePagePOM.getToastMessageText().length() > 0, "Toast message should appear after export");
            
            // Close toast message
            ExpensePagePOM.clickCloseToastMessage();
            
            // Report success
            reportPass("TC045_Export_History_Success", "Successfully viewed export history success message");
        } catch (Exception e) {
            reportFail("TC045_Export_History_Success", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
