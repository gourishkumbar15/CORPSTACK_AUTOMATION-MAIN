package pom_scripts.Privileged;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.By;

import generic.BasePage;

/**
 * Page Object class for Privileged Finance page
 */
public class FinancePagePOM extends BasePage {
    
    @FindBy(xpath = "//span[@class='white-color']") // TC046_Passbook_download_pdf
    private WebElement privilegedLink;
    
    @FindBy(xpath = "//div[normalize-space()='Finances']") // TC046_Passbook_download_pdf
    private WebElement financesLink;
    
    @FindBy(xpath = "//div[@class='HPGrid HPGrid-container-row HPGrid-container-flex-start HPGrid-container-align-center']//div[contains(text(),'Passbook')]") // TC046_Passbook_download_pdf
    private WebElement passbookLink;
    
    @FindBy(xpath = "//span[@name='download']//i[@id='icon-undefined']") // TC046_Passbook_download_pdf
    private WebElement downloadIcon;
    
    @FindBy(xpath = "//button[normalize-space()='Export']") // TC046_Passbook_download_pdf
    private WebElement exportButton;
    
    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC046_Passbook_download_pdf
    private WebElement toastMessage;
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC046_Passbook_download_pdf
    private WebElement closeToastMessage;
    
    @FindBy(xpath = "//i[@id='icon-popup-icon']") // TC047_Passbook_Download_Excel
    private WebElement popupIcon;
    
    @FindBy(xpath = "//div[contains(text(),'Export')]") // TC047_Passbook_Download_Excel
    private WebElement exportOption;
    
    @FindBy(xpath = "//div[contains(text(),'Download BCC')]") // TC048_Passbook_balConfirmation_certi
    private WebElement downloadBCCOption;
    
    @FindBy(xpath = "//input[@placeholder='Wallets']") // TC048_Passbook_balConfirmation_certi
    private WebElement walletsDropdown;
    
    @FindBy(xpath = "//div[@aria-label='Select All']") // TC048_Passbook_balConfirmation_certi
    private WebElement selectAllOption;
    
    @FindBy(xpath = "//button[normalize-space()='Download']") // TC048_Passbook_balConfirmation_certi
    private WebElement downloadButton;
    
    @FindBy(xpath = "//div[contains(@class,'HPGrid HPGrid-container-row HPGrid-container-flex-start HPGrid-container-align-flex-start priv-passbook passbook-wrapper margin-l-8')]//div[1]//div[1]//div[1]//div[1]//span[1]//i[1]") // TC049_Passbook_download_pdf_1Year_filter
    private WebElement calendarIcon;
    
    @FindBy(xpath = "//span[@name='backward']//i[@id='icon-undefined']") // TC049_Passbook_download_pdf_1Year_filter
    private WebElement backwardIcon;
    
    @FindBy(xpath = "//span[contains(text(),'19')]") // TC049_Passbook_download_pdf_1Year_filter
    private WebElement day19;
    
    @FindBy(xpath = "//button[@id='export-button']") // TC049_Passbook_download_pdf_1Year_filter
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
     * Click on Privileged link
     */
    public void clickPrivilegedLink() {
        waitForPageLoad();
        waitForElementToBeClickable(privilegedLink);
        privilegedLink.click();
    }
    
    /**
     * Click on Finances link
     */
    public void clickFinancesLink() {
        waitForElementToBeClickable(financesLink);
        financesLink.click();
    }
    
    /**
     * Click on Passbook link
          * @throws InterruptedException 
          */
        public void clickPassbookLink() throws InterruptedException {
        waitForElementToBeClickable(passbookLink);
        passbookLink.click();
        Thread.sleep(3000);
    }
    
    /**
     * Click on Download icon
     * @throws InterruptedException 
     */
    public void clickDownloadIcon() throws InterruptedException {
        // Wait for loader to disappear
        waitForElementInvisibility(By.className("hw-vatech-loader"));
        waitForElementToBeClickable(downloadIcon);
        downloadIcon.click();
    }
    
    /**
     * Click on Export button
          * @throws InterruptedException 
          */
         public void clickExportButton() throws InterruptedException {
        waitForPageLoad();
        waitForElementToBeClickable(exportButton);
        exportButton.click();
        Thread.sleep(2000);
    }
    
    /**
     * Get toast message text
     * 
     * @return Toast message text
     */
    public String getToastMessageText() {
        waitForElementVisibility(toastMessage);
        return toastMessage.getText();
    }
    
    /**
     * Click on Close toast message
     */
    public void clickCloseToastMessage() {
        waitForElementToBeClickable(closeToastMessage);
        closeToastMessage.click();
    }
    
    /**
     * Click on Popup icon
     */
    public void clickPopupIcon() {
        waitForElementToBeClickable(popupIcon);
        popupIcon.click();
    }
    
    /**
     * Click on Export option
     */
    public void clickExportOption() {
        waitForElementToBeClickable(exportOption);
        exportOption.click();
    }
    
    /**
     * Click on Download BCC option
     */
    public void clickDownloadBCCOption() {
        waitForElementToBeClickable(downloadBCCOption);
        downloadBCCOption.click();
    }
    
    /**
     * Click on Wallets dropdown
          * @throws InterruptedException 
          */
         public void clickWalletsDropdown() throws InterruptedException {
        waitForElementToBeClickable(walletsDropdown);
        walletsDropdown.click();
        Thread.sleep(1000);
    }
    
    /**
     * Click on Select All option
          * @throws InterruptedException 
          */
         public void clickSelectAllOption() throws InterruptedException {
        waitForElementToBeClickable(selectAllOption);
        selectAllOption.click();
        Thread.sleep(1000);
    }
    
    /**
     * Click on Download button
     */
    public void clickDownloadButton() {
        waitForElementToBeClickable(downloadButton);
        downloadButton.click();
    }
    
    /**
     * Click on Calendar icon
          * @throws InterruptedException 
          */
         public void clickCalendarIcon() throws InterruptedException {
        waitForElementVisibility(calendarIcon);
        calendarIcon.click();
    }
    
    /**
     * Click on Backward icon
          * @throws InterruptedException 
          */
         public void clickBackwardIcon() throws InterruptedException {
        waitForElementToBeClickable(backwardIcon);
        backwardIcon.click();
    }
    
    /**
     * Click on day 19
          * @throws InterruptedException 
          */
         public void clickDay19() throws InterruptedException {
        waitForElementToBeClickable(day19);
        day19.click();
        Thread.sleep(1000);
    }
    
    /**
     * Click on Modify button
          * @throws InterruptedException 
          */
         public void clickModifyButton() throws InterruptedException {
        waitForElementToBeClickable(modifyButton);
        modifyButton.click();
        Thread.sleep(2000);
    }
    
    /**
     * Download Passbook PDF
          * @throws InterruptedException 
          */
         public void downloadPassbookPDF() throws InterruptedException {
        clickPrivilegedLink();
        clickFinancesLink();
        clickPassbookLink();
        clickDownloadIcon();
        clickExportButton();
    }
    
    /**
     * Download Passbook Excel
          * @throws InterruptedException 
          */
         public void downloadPassbookExcel() throws InterruptedException {
        clickPrivilegedLink();
        clickFinancesLink();
        clickPassbookLink();
        clickPopupIcon();
        clickExportOption();
        clickExportButton();
    }
    
    /**
     * Download Balance Confirmation Certificate
          * @throws InterruptedException 
          */
         public void downloadBalanceConfirmationCertificate() throws InterruptedException {
        clickPrivilegedLink();
        clickFinancesLink();
        clickPassbookLink();
        clickPopupIcon();
        clickDownloadBCCOption();
        clickWalletsDropdown();
        clickSelectAllOption();
        clickDownloadButton();
    }
    
    /**
     * Download Passbook PDF with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadPassbookPDFWith1YearFilter() throws InterruptedException {
        clickPrivilegedLink();
        clickFinancesLink();
        clickPassbookLink();
        clickCalendarIcon();
        clickBackwardIcon();
        clickDay19();
        clickModifyButton();
        clickDownloadIcon();
        clickExportButton();
    }
    
    /**
     * Download Passbook Excel with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadPassbookExcelWith1YearFilter() throws InterruptedException {
        clickPrivilegedLink();
        clickFinancesLink();
        clickPassbookLink();
        clickCalendarIcon();
        clickBackwardIcon();
        clickDay19();
        clickModifyButton();
        clickPopupIcon();
        clickExportOption();
        clickExportButton();
    }
    
    /**
     * Download Balance Confirmation Certificate with 1 Year filter
          * @throws InterruptedException 
          */
         public void downloadBalanceConfirmationCertificateWith1YearFilter() throws InterruptedException {
        clickPrivilegedLink();
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
    }
}
