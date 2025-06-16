package pom_scripts.Employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;
import generic.LoggerUtil;
import org.slf4j.Logger;

/**
 * Page Object class for Employee Finance page
 */
public class FinancePagePOM extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(FinancePagePOM.class);
    
    @FindBy(xpath = "//div[normalize-space()='Finances']") // TC007_Passbook_download_pdf
    private WebElement financesLink;
    
    @FindBy(xpath = "//div[@class='HPGrid HPGrid-container-row HPGrid-container-flex-start HPGrid-container-align-center']//div[contains(text(),'Passbook')]") // TC007_Passbook_download_pdf
    private WebElement passbookLink;
    
    @FindBy(xpath = "//span[@name='download']//i[@id='icon-undefined']") // TC007_Passbook_download_pdf
    private WebElement downloadIcon;
    
    @FindBy(xpath = "//button[normalize-space()='Export']") // TC007_Passbook_download_pdf
    private WebElement exportButton;
    
    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC007_Passbook_download_pdf
    private WebElement toastMessage;
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC007_Passbook_download_pdf
    private WebElement closeToastMessage;
    
    @FindBy(xpath = "//i[@id='icon-popup-icon']") // TC008_Passbook_Export_Excel
    private WebElement popupIcon;
    
    @FindBy(xpath = "//div[contains(text(),'Export')]") // TC008_Passbook_Export_Excel
    private WebElement exportOption;
    
    @FindBy(xpath = "//div[contains(text(),'Download BCC')]") // TC009_Passbook_balConfirmation_certi
    private WebElement downloadBCCOption;
    
    @FindBy(xpath = "//input[@placeholder='Wallets']") // TC009_Passbook_balConfirmation_certi
    private WebElement walletsDropdown;
    
    @FindBy(xpath = "//div[@aria-label='Select All']") // TC009_Passbook_balConfirmation_certi
    private WebElement selectAllOption;
    
    @FindBy(xpath = "//button[normalize-space()='Download']") // TC009_Passbook_balConfirmation_certi
    private WebElement downloadButton;
    
    @FindBy(xpath = "//body/div[@id='app']/div[@class='demo-big-content']/div[@class='dashboardLayout']/div[@class='dashboardLayoutInnerContent']/div[@class='dashbaordContentBlock full-width false']/div[@class='userDashboard']/div[@class='HPGrid HPGrid-container-row HPGrid-container-flex-start HPGrid-container-align-flex-start']/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/span[1]/i[1]") // TC010_Passbook_download_pdf_1Year_filter
    private WebElement calendarIcon;
    
    @FindBy(xpath = "//span[@name='backward']//i[@id='icon-undefined']") // TC010_Passbook_download_pdf_1Year_filter
    private WebElement backwardIcon;
    
    @FindBy(xpath = "//span[contains(text(),'19')]") // TC010_Passbook_download_pdf_1Year_filter
    private WebElement day19;
    
    @FindBy(xpath = "//button[@id='export-button']") // TC010_Passbook_download_pdf_1Year_filter
    private WebElement modifyButton;
    
    /**
     * Constructor to initialize the FinancePage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public FinancePagePOM(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on Finances link
     * @throws InterruptedException 
     */
    public void clickFinancesLink() throws InterruptedException {
        logger.info("Clicking on Finances link");
        try {
            // Wait for page load
            waitForPageLoad();
            
            // Wait for element to be visible
            waitForElementVisibility(financesLink);
            
            // Scroll element into view
            scrollToElement(financesLink);
            
            // Try regular click first
            try {
                waitForElementToBeClickable(financesLink);
                financesLink.click();
            } catch (Exception e) {
                logger.debug("Regular click failed, trying JavaScript click");
                // If regular click fails, try JavaScript click
                jsClick(financesLink);
            }
            
            // Wait for navigation
            logger.debug("Waiting for 3 seconds after clicking Finances link");
            Thread.sleep(3000);
            
            // Wait for page load after click
            waitForPageLoad();
            
        } catch (Exception e) {
            logger.error("Failed to click Finances link: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Passbook link
     * @throws InterruptedException 
     */
    public void clickPassbookLink() throws InterruptedException {
        logger.info("Clicking on Passbook link");
        try {
            // Wait for page load
            waitForPageLoad();
            
            // Wait for element to be visible
            waitForElementVisibility(passbookLink);
            
            // Scroll element into view
            scrollToElement(passbookLink);
            
            // Try regular click first
            try {
                waitForElementToBeClickable(passbookLink);
                passbookLink.click();
            } catch (Exception e) {
                logger.debug("Regular click failed, trying JavaScript click");
                // If regular click fails, try JavaScript click
                jsClick(passbookLink);
            }
            
            // Wait for navigation
            logger.debug("Waiting for 2 seconds after clicking Passbook link");
            Thread.sleep(2000);
            
            // Wait for page load after click
            waitForPageLoad();
            
        } catch (Exception e) {
            logger.error("Failed to click Passbook link: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Download icon
     */
    public void clickDownloadIcon() {
        logger.info("Clicking on Download icon");
        waitForPageLoad();
        waitForElementToBeClickable(downloadIcon);
        downloadIcon.click();
        logger.debug("Clicked on Download icon");
    }
    
    /**
     * Click on Export button
     */
    public void clickExportButton() {
        logger.info("Clicking on Export button");
        waitForPageLoad();
        waitForElementToBeClickable(exportButton);
        exportButton.click();
        logger.debug("Clicked on Export button");
    }
    
    /**
     * Get toast message text
     * 
     * @return Toast message text
     */
    public String getToastMessageText() {
        logger.info("Getting toast message text");
        waitForElementVisibility(toastMessage);
        String text = toastMessage.getText();
        logger.debug("Toast message text: {}", text);
        return text;
    }
    
    /**
     * Click on Close toast message
     */
    public void clickCloseToastMessage() {
        logger.info("Clicking on Close toast message");
        waitForElementToBeClickable(closeToastMessage);
        closeToastMessage.click();
        logger.debug("Clicked on Close toast message");
    }
    
    /**
     * Click on Popup icon
     */
    public void clickPopupIcon() {
        logger.info("Clicking on Popup icon");
        waitForPageLoad();
        waitForElementToBeClickable(popupIcon);
        popupIcon.click();
        logger.debug("Clicked on Popup icon");
    }
    
    /**
     * Click on Export option
     */
    public void clickExportOption() {
        logger.info("Clicking on Export option");
        waitForPageLoad();
        waitForElementToBeClickable(exportOption);
        exportOption.click();
        logger.debug("Clicked on Export option");
    }
    
    /**
     * Click on Download BCC option
          * @throws InterruptedException 
          */
         public void clickDownloadBCCOption() throws InterruptedException {   
        logger.info("Clicking on Download BCC option");
        waitForPageLoad();
        waitForElementToBeClickable(downloadBCCOption);
        downloadBCCOption.click();
        logger.debug("Waiting for 2 seconds after clicking Download BCC option");
        Thread.sleep(2000);
    }
    
    /**
     * Click on Wallets dropdown
          * @throws InterruptedException 
          */
         public void clickWalletsDropdown() throws InterruptedException {
        logger.info("Clicking on Wallets dropdown");
        waitForPageLoad();
        waitForElementToBeClickable(walletsDropdown);
        walletsDropdown.click();
        logger.debug("Waiting for 1 second after clicking Wallets dropdown");
        Thread.sleep(1000);
    }
    
    /**
     * Click on Select All option
          * @throws InterruptedException 
          */
         public void clickSelectAllOption() throws InterruptedException {
        logger.info("Clicking on Select All option");
        waitForPageLoad();
        waitForElementToBeClickable(selectAllOption);
        selectAllOption.click();
        logger.debug("Waiting for 1 second after clicking Select All option");
        Thread.sleep(1000);
    }
    
    /**
     * Click on Download button
          * @throws InterruptedException 
          */
         public void clickDownloadButton() throws InterruptedException {
        logger.info("Clicking on Download button");
        logger.debug("Waiting for 2 seconds before clicking Download button");
        Thread.sleep(2000);
        waitForElementToBeClickable(downloadButton);
        downloadButton.click();
        logger.debug("Clicked on Download button");
    }
    
    /**
     * Click on Calendar icon
     */
    public void clickCalendarIcon() {
        logger.info("Clicking on Calendar icon");
        waitForPageLoad();
        waitForElementToBeClickable(calendarIcon);
        calendarIcon.click();
        logger.debug("Clicked on Calendar icon");
    }
    
    /**
     * Click on Backward icon
     */
    public void clickBackwardIcon() {
        logger.info("Clicking on Backward icon");
        waitForPageLoad();
        waitForElementToBeClickable(backwardIcon);
        backwardIcon.click();
        logger.debug("Clicked on Backward icon");
    }

    /**
     * Click on day 19
     */
    public void clickDay19() {
        logger.info("Clicking on day 19");
        waitForElementToBeClickable(day19);
        day19.click();
        logger.debug("Clicked on day 19");
    }
    
    /**
     * Click on Modify button
     */
    public void clickModifyButton() {
        logger.info("Clicking on Modify button");
        waitForPageLoad();
        waitForElementToBeClickable(modifyButton);
        modifyButton.click();
        logger.debug("Clicked on Modify button");
    }
    
    /**
     * Download Passbook PDF
          * @throws InterruptedException 
          */
         public void downloadPassbookPDF() throws InterruptedException {
        logger.info("Starting Passbook PDF download process");
        clickFinancesLink();
        clickPassbookLink();
        clickDownloadIcon();
        clickExportButton();
        logger.info("Completed Passbook PDF download process");
    }
    
    /**
     * Download Passbook Excel
          * @throws InterruptedException 
          */
         public void downloadPassbookExcel() throws InterruptedException {
        logger.info("Starting Passbook Excel download process");
        clickFinancesLink();
        clickPassbookLink();
        clickPopupIcon();
        clickExportOption();
        clickExportButton();
        logger.info("Completed Passbook Excel download process");
    }
    
    /**
     * Download Balance Confirmation Certificate
          * @throws InterruptedException 
          */
         public void downloadBalanceConfirmationCertificate() throws InterruptedException {
        logger.info("Starting Balance Confirmation Certificate download process");
        clickFinancesLink();
        clickPassbookLink();
        clickPopupIcon();
        clickDownloadBCCOption();
        clickWalletsDropdown();
        clickSelectAllOption();
        clickDownloadButton();
        logger.info("Completed Balance Confirmation Certificate download process");
    }
    
    /**
     * Download Passbook PDF with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadPassbookPDFWith1YearFilter() throws InterruptedException {
        logger.info("Starting Passbook PDF download with 1 Year filter process");
        clickFinancesLink();
        clickPassbookLink();
        clickCalendarIcon();
        clickBackwardIcon();
        clickDay19();
        clickModifyButton();
        clickDownloadIcon();
        clickExportButton();
        logger.info("Completed Passbook PDF download with 1 Year filter process");
    }
    
    /**
     * Download Passbook Excel with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadPassbookExcelWith1YearFilter() throws InterruptedException {
        logger.info("Starting Passbook Excel download with 1 Year filter process");
        clickFinancesLink();
        clickPassbookLink();
        clickCalendarIcon();
        clickBackwardIcon();
        clickDay19();
        clickModifyButton();
        clickPopupIcon();
        clickExportOption();
        clickExportButton();
        logger.info("Completed Passbook Excel download with 1 Year filter process");
    }
    
    /**
     * Download Balance Confirmation Certificate with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadBalanceConfirmationCertificateWith1YearFilter() throws InterruptedException {
        logger.info("Starting Balance Confirmation Certificate download with 1 Year filter process");
        clickFinancesLink();
        clickPassbookLink();
        clickCalendarIcon();
        clickBackwardIcon();
        clickDay19();
        clickModifyButton();
        clickPopupIcon();
        clickDownloadBCCOption();
        clickWalletsDropdown();
        clickSelectAllOption();
        clickDownloadButton();
        logger.info("Completed Balance Confirmation Certificate download with 1 Year filter process");
    }
}
