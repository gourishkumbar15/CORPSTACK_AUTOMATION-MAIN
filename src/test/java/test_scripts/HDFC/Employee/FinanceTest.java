package test_scripts.HDFC.Employee;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.CorpBaseTest;
import generic.LoggerUtil;
import pom_scripts.Employee.FinancePagePOM;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Test class for Employee Finance functionality
 */
public class FinanceTest extends CorpBaseTest {
    private static final Logger logger = LoggerUtil.getLogger(FinanceTest.class);
    
    /**
     * Test case for downloading Passbook PDF
     */
    @Test(priority = 1, description = "Passbook - Download PDF", testName = "TC007_Passbook_download_pdf",enabled = true)
    @Description("Passbook - Download PDF")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Download PDF")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadPDF() throws InterruptedException {
        logger.info("Starting test: testPassbookDownloadPDF");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Passbook PDF download");
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickDownloadIcon();
            financePage.clickExportButton();
            
            // Verify toast message appears
            String toastMessage = financePage.getToastMessageText();
            logger.debug("Toast message: {}", toastMessage);
            Assert.assertTrue(toastMessage.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC007_Passbook_download_pdf", "Successfully downloaded Passbook PDF");
            logger.info("Test completed successfully: testPassbookDownloadPDF");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookDownloadPDF", e);
            reportFail("TC007_Passbook_download_pdf", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook Excel
     */
    @Test(priority = 2, description = "Passbook - Download Excel", testName = "TC008_Passbook_Export_Excel", enabled = true)
    @Description("Passbook - Download Excel")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Download Excel")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookExportExcel() throws InterruptedException {
        logger.info("Starting test: testPassbookExportExcel");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Passbook Excel export");
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickPopupIcon();
            financePage.clickExportOption();
            financePage.clickExportButton();
            
            // Verify toast message appears
            String toastMessage = financePage.getToastMessageText();
            logger.debug("Toast message: {}", toastMessage);
            Assert.assertTrue(toastMessage.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC008_Passbook_Export_Excel", "Successfully downloaded Passbook Excel");
            logger.info("Test completed successfully: testPassbookExportExcel");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookExportExcel", e);
            reportFail("TC008_Passbook_Export_Excel", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Balance Confirmation Certificate
     */
    @Test(priority = 3, description = "Passbook - Balance Confirmation Certificate - export", testName = "TC009_Passbook_balConfirmation_certi", enabled = true)
    @Description("Passbook - Balance Confirmation Certificate - export")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Balance Confirmation Certificate - export")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookBalanceConfirmationCertificate() throws InterruptedException {
        logger.info("Starting test: testPassbookBalanceConfirmationCertificate");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Balance Confirmation Certificate download");
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickPopupIcon();
            financePage.clickDownloadBCCOption();
            financePage.clickWalletsDropdown();
            financePage.clickSelectAllOption();
            financePage.clickDownloadButton();
            
            String message = financePage.getToastMessageText();
            logger.debug("Toast message: {}", message);

            // Verify toast message appears
            Assert.assertTrue(message.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC009_Passbook_balConfirmation_certi", "Successfully downloaded Balance Confirmation Certificate");
            logger.info("Test completed successfully: testPassbookBalanceConfirmationCertificate");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookBalanceConfirmationCertificate", e);
            reportFail("TC009_Passbook_balConfirmation_certi", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook PDF with 1 Year filter
     */
    @Test(priority = 4, description = "Passbook - Download PDF- 1 Year filter", 
    testName = "TC010_Passbook_download_pdf_1Year_filter", enabled = true)
    @Description("Passbook - Download PDF- 1 Year filter")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Download PDF- 1 Year filter")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadPDF1YearFilter() throws InterruptedException {
        logger.info("Starting test: testPassbookDownloadPDF1YearFilter");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Passbook PDF download with 1 Year filter");
            financePage.clickFinancesLink();
            financePage.clickPassbookLink();
            financePage.clickCalendarIcon();
            financePage.clickBackwardIcon();
            financePage.clickDay19();
            financePage.clickModifyButton();
            financePage.clickDownloadIcon();
            financePage.clickExportButton();
            
            // Verify toast message appears
            String toastMessage = financePage.getToastMessageText();
            logger.debug("Toast message: {}", toastMessage);
            Assert.assertTrue(toastMessage.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC010_Passbook_download_pdf_1Year_filter", "Successfully downloaded Passbook PDF with 1 Year filter");
            logger.info("Test completed successfully: testPassbookDownloadPDF1YearFilter");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookDownloadPDF1YearFilter", e);
            reportFail("TC010_Passbook_download_pdf_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Passbook Excel with 1 Year filter
     */
    @Test(priority = 5, description = "Passbook - Download Excel - 1 Year filter", 
    testName = "TC011_Passbook_Download_Excel_1Year_filter", enabled = true)
    @Description("Passbook - Download Excel - 1 Year filter")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Download Excel - 1 Year filter")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookDownloadExcel1YearFilter() throws InterruptedException {
        logger.info("Starting test: testPassbookDownloadExcel1YearFilter");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Passbook Excel download with 1 Year filter");
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
            String toastMessage = financePage.getToastMessageText();
            logger.debug("Toast message: {}", toastMessage);
            Assert.assertTrue(toastMessage.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC011_Passbook_Download_Excel_1Year_filter", "Successfully downloaded Passbook Excel with 1 Year filter");
            logger.info("Test completed successfully: testPassbookDownloadExcel1YearFilter");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookDownloadExcel1YearFilter", e);
            reportFail("TC011_Passbook_Download_Excel_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test case for downloading Balance Confirmation Certificate with 1 Year filter
     */
    @Test(priority = 6, description = "Passbook - Balance Confirmation Certificate export - 1 Year", 
    testName = "TC012_Passbook_balConfirmation_certi_1Year_filter", enabled = true)
    @Description("Passbook - Balance Confirmation Certificate export - 1 Year")
    @Epic("Employee")
    @Feature("Finance")
    @Story("Passbook - Balance Confirmation Certificate export - 1 Year")
    @Severity(SeverityLevel.NORMAL)
    public void testPassbookBalanceConfirmationCertificate1YearFilter() throws InterruptedException {
        logger.info("Starting test: testPassbookBalanceConfirmationCertificate1YearFilter");
        try {
            // Create page object
            logger.debug("Creating FinancePage object");
            FinancePagePOM financePage = new FinancePagePOM(driver);
            
            // Test steps
            logger.info("Executing test steps for Balance Confirmation Certificate download with 1 Year filter");
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
            
            String message = financePage.getToastMessageText();
            logger.debug("Toast message: {}", message);
            
            // Verify toast message appears
            Assert.assertTrue(message.length() > 0, "Toast message should appear after download");
            logger.info("Toast message verification successful");
            
            // Close toast message
            financePage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC012_Passbook_balConfirmation_certi_1Year_filter", "Successfully downloaded Balance Confirmation Certificate with 1 Year filter");
            logger.info("Test completed successfully: testPassbookBalanceConfirmationCertificate1YearFilter");
        } catch (Exception e) {
            logger.error("Test failed: testPassbookBalanceConfirmationCertificate1YearFilter", e);
            reportFail("TC012_Passbook_balConfirmation_certi_1Year_filter", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
