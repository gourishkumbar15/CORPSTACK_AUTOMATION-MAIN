package pom_scripts.Privileged;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import generic.BasePage;

public class ExpensePagePOM extends BasePage{

    @FindBy(xpath = "//span[@class='white-color']") // TC026_Export_wallet_limi_History_email
    private WebElement privilegedLink;

    @FindBy(xpath = "//div[normalize-space()='Expenses']") // TC043_Emp_Expense_Download_Local
    private WebElement expensesLink;
    
    @FindBy(xpath = "//div[contains(text(),'Download Expense Report')]") // TC043_Emp_Expense_Download_Local
    private WebElement downloadExpenseReportText;
    
    @FindBy(xpath = "//div[@class='customToastContent']") // TC043_Emp_Expense_Download_Local
    private WebElement fileGeneratedSuccessfullyMessage;
    
    @FindBy(xpath = "//button[@type='button']") // TC045_Export_History_Success
    private WebElement exportButton;

    @FindBy(xpath = "//button[contains(text(),'Download')]") // TC026_Export_wallet_limi_History_email
    private WebElement downloadButton;

    @FindBy(xpath = "//span[@name='download']//i[@id='icon-undefined']") // TC030_Export_Users_Local
    private WebElement downloadIcon;
    
    @FindBy(xpath = "//input[@placeholder='Add Mail Id & Press Enter']") // TC031_Export_Users_Email
    private WebElement emailInput;

    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement toastMessage;
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement closeToastMessage;
 
    @FindBy(xpath = "//div[contains(text(),'View Export History')]")
    private WebElement viewExportHistoryLink; // TC045_Export_History_Success
    
    
    /**
     * Constructor to initialize the UsersPage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public ExpensePagePOM(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on Privileged link
     */
    public void clickPrivilegedLink() {
        waitForElementToBeClickable(privilegedLink);
        privilegedLink.click();
    }

    /**
     * Click on Expenses link
     */
    public void clickExpensesLink() {
        waitForElementToBeClickable(expensesLink);
        expensesLink.click();
    }
    
    /**
     * Click on Download Expense Report text
          * @throws InterruptedException 
          */
         public void clickDownloadExpenseReportText() throws InterruptedException {
        waitForElementToBeClickable(downloadExpenseReportText);
        downloadExpenseReportText.click();
        Thread.sleep(2000);
    }
    
    /**
     * Click on Export button
     */
    public void clickExportButton() {
        waitForElementToBeClickable(exportButton);
        exportButton.click();
    }
        /**
     * Click on Download button
     */
    public void clickDownloadButton() {
        waitForElementToBeClickable(downloadButton);
        downloadButton.click();
    }
        
    /**
     * Click on Download icon
     */
    public void clickDownloadIcon() {
        waitForPageLoad();
        waitForElementToBeClickable(downloadIcon);
        downloadIcon.click();
    }
    /** view ExportHistoryLink
     * 
     * @param email Email address to enter
     */
    public void clickviewExportHistoryLink() {
        waitForElementVisibility(viewExportHistoryLink);
        viewExportHistoryLink.click();
    }

        /**
     * Enter email address
     * 
     * @param email Email address to enter
     */
    public void enterEmail(String email) {
        waitForElementVisibility(emailInput);
        enterText(emailInput, email);
        emailInput.sendKeys(Keys.ENTER);
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
     * Export expenses
          * @throws InterruptedException 
          */
         public void exportExpenses() throws InterruptedException {
        clickPrivilegedLink();
        clickExpensesLink();
        clickDownloadIcon();
        clickDownloadExpenseReportText();
        clickDownloadButton();
    }
    
    /**
     * Export expenses to email
     * 
     * @param email Email address to send report to
          * @throws InterruptedException 
          */
        public void exportExpensesToEmail(String email) throws InterruptedException {
        clickPrivilegedLink();
        clickExpensesLink();
        clickDownloadIcon();
        clickDownloadExpenseReportText();
        enterEmail(email);
        clickDownloadButton();
    }
    
    /**
     * Export history success
     */
    public void exportHistorySuccess() {
        clickPrivilegedLink();

        clickDownloadIcon();
        clickExportButton();
    }

}
