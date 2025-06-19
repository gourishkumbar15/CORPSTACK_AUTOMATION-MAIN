package test_scripts.HDFC.Employee;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.CorpBaseTest;
import pom_scripts.Employee.ExpensePagePOM;
import property.Corp_Test_DataByPropertyFILE;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Employee Expense functionality
 */
public class ExpenseTest extends CorpBaseTest {
    
    /**
     * Test case for exporting employee expenses as a local download
     */
    @Test(priority = 1, description = "Export employee Expenses download", testName = "TC001_Emp_Expense_Download", enabled = true)
    @Description("Export employee Expenses download")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export employee Expenses download")
    @Severity(SeverityLevel.NORMAL)
    public void testExpenseDownload() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.clickDownloadIcon();
            expensePage.clickDownloadText();
            expensePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC001_Emp_Expense_Download", "Successfully downloaded employee expenses");
        } catch (Exception e) {
            reportFail("TC001_Emp_Expense_Download", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting employee expenses to email
     */
    @Test(priority = 2, description = "Export employee Expenses to Email", testName = "TC002_Emp_Expense_Download_to_Email", enabled = true)
    @Description("Export employee Expenses to Email")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export employee Expenses to Email")
    @Severity(SeverityLevel.NORMAL)
    public void testExpenseDownloadToEmail() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.clickDownloadIcon();
            expensePage.clickDownloadText();
            
            // Enter email address - using test email from properties
            Corp_Test_DataByPropertyFILE testData = new Corp_Test_DataByPropertyFILE();
            String testEmail = testData.getTestData("test_email");
            expensePage.enterEmail(testEmail);
            
            expensePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC002_Emp_Expense_Download_to_Email", "Successfully sent employee expenses to email");
        } catch (Exception e) {
            reportFail("TC002_Emp_Expense_Download_to_Email", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for viewing export history success message
     */
    @Test(priority = 3, description = "Export history view and success message", testName = "TC003_Export_History_Success", enabled = true)
    @Description("Export history view and success message")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export history view and success message")
    @Severity(SeverityLevel.NORMAL)
    public void testExportHistorySuccess() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.clickDownloadIcon();
            expensePage.clickViewExportHistory();
            Thread.sleep(2000);
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC003_Export_History_Success", "Successfully viewed export history success message");
        } catch (Exception e) {
            reportFail("TC003_Export_History_Success", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting employee expenses with 30 day filter
     */
    @Test(priority = 4, description = "Export - Employee Expenses with 30 day filter", testName = "TC004_Export_30day_Emp_Expense", enabled = true)
    @Description("Export - Employee Expenses with 30 day filter")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export - Employee Expenses with 30 day filter")
    @Severity(SeverityLevel.NORMAL)
    public void testExport30DayEmpExpense() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.clickLast30DaysFilter();
            expensePage.clickDownloadIcon();
            expensePage.clickDownloadText();
            expensePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC004_Export_30day_Emp_Expense", "Successfully downloaded employee expenses with 30 day filter");
        } catch (Exception e) {
            reportFail("TC004_Export_30day_Emp_Expense", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting with card transaction type filter
     */
    @Test(priority = 5,description = "Export - With Card transaction type filter", testName = "TC005_Export_with_Txn-type_filter", enabled = true)
    @Description("Export - With Card transaction type filter")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export - With Card transaction type filter")
    @Severity(SeverityLevel.NORMAL)
    public void testExportWithTxnTypeFilter() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.selectCardTransactionFilter();
            expensePage.clickApplyFilters();
            expensePage.clickDownloadIcon();
            expensePage.clickDownloadText();
            expensePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC005_Export_with_Txn-type_filter", "Successfully downloaded expenses with transaction type filter");
        } catch (Exception e) {
            reportFail("TC005_Export_with_Txn-type_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for exporting with wallet type filter
     */
    @Test(priority = 6,description = "Export - With Wallet type filter", testName = "TC006_Export_with_walletType_filter", enabled = true)
    @Description("Export - With Wallet type filter")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export - With Wallet type filter")
    @Severity(SeverityLevel.NORMAL)
    public void testExportWithWalletTypeFilter() {
        try {
            // Create page object
            ExpensePagePOM expensePage = new ExpensePagePOM(driver);
            
            // Test steps
            expensePage.clickExpensesLink();
            expensePage.selectImprestFilter();
            expensePage.clickApplyFilters();
            expensePage.clickDownloadIcon();
            expensePage.clickDownloadText();
            expensePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(expensePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            expensePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC006_Export_with_walletType_filter", "Successfully downloaded expenses with wallet type filter");
        } catch (Exception e) {
            reportFail("TC006_Export_with_walletType_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
