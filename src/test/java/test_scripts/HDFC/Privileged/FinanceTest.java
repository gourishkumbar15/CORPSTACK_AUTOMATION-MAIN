package test_scripts.HDFC.Privileged;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.CorpBaseTest;
import pom_scripts.Privileged.FinancePagePOM;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Privileged Finance functionality
 */
public class FinanceTest extends CorpBaseTest {
    
    /**
     * Test case for downloading Passbook PDF
     */
    @Test(priority = 1, description = "Download PDF", testName = "TC046_Passbook_download_pdf", enabled = true)
    @Description("Download PDF")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("Download PDF")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadPDF() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickDownloadIcon();
            financePage.clickExportButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC046_Passbook_download_pdf", "Successfully downloaded Passbook PDF");
        } catch (Exception e) {
            reportFail("TC046_Passbook_download_pdf", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook Excel
     */
    @Test(priority = 2, description = "Download Excel", testName = "TC047_Passbook_Download_Excel", enabled = true)
    @Description("Download Excel")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("Download Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadExcel() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickPopupIcon();
            financePage.clickExportOption();
            financePage.clickExportButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC047_Passbook_Download_Excel", "Successfully downloaded Passbook Excel");
        } catch (Exception e) {
            reportFail("TC047_Passbook_Download_Excel", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Balance Confirmation Certificate
     */
    @Test(priority = 3, description = "Balance Confirmation Certificate - export", 
    testName = "TC048_Passbook_balConfirmation_certi", enabled = true)
    @Description("Balance Confirmation Certificate - export")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("Balance Confirmation Certificate - export")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookBalanceConfirmationCertificate() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickPopupIcon();
            financePage.clickDownloadBCCOption();
            financePage.clickWalletsDropdown();
            financePage.clickSelectAllOption();
            financePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC048_Passbook_balConfirmation_certi", "Successfully downloaded Balance Confirmation Certificate");
        } catch (Exception e) {
            reportFail("TC048_Passbook_balConfirmation_certi", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook PDF with 1 Year filter
     */
    @Test(priority = 4,description = "1 Year filter - Download PDF", testName = "TC049_Passbook_download_pdf_1Year_filter", enabled = true)
    @Description("1 Year filter - Download PDF")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("1 Year filter - Download PDF")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadPDF1YearFilter() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickCalendarIcon();
            financePage.clickBackwardIcon();
            financePage.clickDay19();
            financePage.clickModifyButton();
            financePage.clickDownloadIcon();
            financePage.clickExportButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC049_Passbook_download_pdf_1Year_filter", "Successfully downloaded Passbook PDF with 1 Year filter");
        } catch (Exception e) {
            reportFail("TC049_Passbook_download_pdf_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook Excel with 1 Year filter
     */
    @Test(priority = 5,description = "1 Year filter - Download Excel", 
    testName = "TC050_Passbook_Download_Excel_1Year_filter", enabled = true)
    @Description("1 Year filter - Download Excel")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("1 Year filter - Download Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadExcel1YearFilter() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickCalendarIcon();
            financePage.clickBackwardIcon();
            financePage.clickDay19();
            financePage.clickModifyButton();
            financePage.clickPopupIcon();
            financePage.clickExportOption();
            financePage.clickExportButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC050_Passbook_Download_Excel_1Year_filter", "Successfully downloaded Passbook Excel with 1 Year filter");
        } catch (Exception e) {
            reportFail("TC050_Passbook_Download_Excel_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Balance Confirmation Certificate with 1 Year filter
     */
    @Test(priority = 6,description = "1 Year - Balance Confirmation Certificate - export", 
    testName = "TC051_Passbook_balConfirmation_certi_1Year_filter", enabled = true)
    @Description("1 Year - Balance Confirmation Certificate - export")
    @Epic("Privileged")
    @Feature("Finance")
    @Story("1 Year - Balance Confirmation Certificate - export")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookBalanceConfirmationCertificate1YearFilter() {
        try {
            // Create page object
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            financePage.clickPrivilegedLink();
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickCalendarIcon();
            financePage.clickBackwardIcon();
            financePage.clickDay19();
            financePage.clickModifyButton();
            financePage.clickPopupIcon();
            financePage.clickDownloadBCCOption();
            financePage.clickWalletsDropdown();
            financePage.clickSelectAllOption();
            financePage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(financePage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC051_Passbook_balConfirmation_certi_1Year_filter", "Successfully downloaded Balance Confirmation Certificate with 1 Year filter");
        } catch (Exception e) {
            reportFail("TC051_Passbook_balConfirmation_certi_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
