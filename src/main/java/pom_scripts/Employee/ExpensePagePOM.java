package pom_scripts.Employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;
import org.openqa.selenium.By;

/**
 * Page Object Model class for Employee Expense page
 */
public class ExpensePagePOM extends BasePage {
    
    // Locators using @FindBy annotation
    @FindBy(xpath = "//div[normalize-space()='Expenses']")
    private WebElement expensesLink; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//span[@name='download']//i[@id='icon-undefined']")
    private WebElement downloadIcon; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//div[contains(text(),'Download')]")
    private WebElement downloadText; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//button[normalize-space()='Download']")
    private WebElement downloadButton; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//div[@class='customToastContent']")
    private WebElement toastMessage; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']")
    private WebElement closeToastMessage; // TC001_Emp_Expense_Download
    
    @FindBy(xpath = "//input[@placeholder='Add Mail Id & Press Enter']")
    private WebElement emailInput; // TC002_Emp_Expense_Download_to_Email
    
    @FindBy(xpath = "//div[contains(text(),'View Export History')]")
    private WebElement viewExportHistoryLink; // TC003_Export_History_Success
    
    @FindBy(xpath = "//input[@id='dd-date-range-metaid-value']")
    private WebElement last30DaysFilter; // TC004_Export_30day_Emp_Expense
    
    // Locators for transaction type filters
    @FindBy(xpath = "//label[@aria-label='Card Transaction']//span[@class='height-16 width-16']")
    private WebElement cardTransactionFilter; // TC005_Export_with_Txn-type_filter
    
    @FindBy(xpath = "//label[@aria-label='Wallet Load Credit']//span[@class='height-16 width-16']")
    private WebElement walletLoadCreditFilter; // TC005_Export_with_Txn-type_filter
    
    @FindBy(xpath = "//button[normalize-space()='Apply Filters']")
    private WebElement applyFiltersButton; // TC005_Export_with_Txn-type_filter
    
    @FindBy(xpath = "//label[@aria-label='Imprest']//span[@class='height-16 width-16']")
    private WebElement imprestFilter; // TC006_Export_with_walletType_filter
    
    @FindBy(xpath = "//div[@class='SearchBarWrapper searchBar wrapperDiv']")
    private WebElement sideFilter;
    
    /**
     * Constructor to initialize the ExpensePage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public ExpensePagePOM(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on Expenses link
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickExpensesLink() {
        waitForPageLoad();
        clickWithFallback(expensesLink);
        waitForPageLoad();
        waitForAjaxComplete();
        return this;
    }
    
    /**
     * Click on download icon
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickDownloadIcon() {
        waitForElementVisibility(downloadIcon);
        safeClick(downloadIcon);
        return this;
    }
    
    /**
     * Click on Download text
     * 
     * @return ExpensePage instance for method chaining
          * @throws InterruptedException 
          */
         public ExpensePagePOM clickDownloadText() throws InterruptedException {
        waitForElementVisibility(downloadText);
        safeClick(downloadText);
        Thread.sleep(2000);
        return this;
    }
    
    /**
     * Click on Download button
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickDownloadButton() {
        waitForElementToBeClickable(downloadButton);
        safeClick(downloadButton);
        waitForElementVisibility(toastMessage);
        return this;
    }
    
    /**
     * Click on Close toast message
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickCloseToastMessage() {
        if (isElementDisplayed(closeToastMessage)) {
            safeClick(closeToastMessage);
        }
        return this;
    }
    
    /**
     * Enter email address
     * 
     * @param email Email address to enter
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM enterEmail(String email) {
        waitForElementVisibility(emailInput);
        enterText(emailInput, email);
        emailInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        return this;
    }
    
    /**
     * Click on View Export History link
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickViewExportHistory() {
        waitForElementVisibility(viewExportHistoryLink);
        safeClick(viewExportHistoryLink);
        waitForPageLoad();
        waitForAjaxComplete();
        return this;
    }
 /**
     * Click on Last 30 days filter
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickLast30DaysFilter() {
        waitForElementVisibility(last30DaysFilter);
        safeClick(last30DaysFilter);
        waitForPageLoad();
        waitForAjaxComplete();
        return this;
    }
    
    /**
     * Select Card Transaction filter
     * 
     * @return ExpensePage instance for method chaining
          * @throws InterruptedException 
          */
         public ExpensePagePOM selectCardTransactionFilter() throws InterruptedException {
        scrollToElement(sideFilter);
        safeClick(cardTransactionFilter);
        safeClick(walletLoadCreditFilter);
        Thread.sleep(2000);
        return this;
    }
    
  
    /**
     * Click on Apply Filters button
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM clickApplyFilters() {
        safeClick(applyFiltersButton);
        waitForPageLoad();
        waitForAjaxComplete();
        return this;
    }
    
    /**
     * Select Imprest filter
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM selectImprestFilter() {
        waitForElementVisibility(imprestFilter);
        safeClick(imprestFilter);
        return this;
    }
    
    /**
     * Export with transaction type filter
     * TC005_Export_with_Txn-type_filter
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM exportWithTransactionTypeFilter() {
        // Click on Card Transaction filter
        scrollToElement(cardTransactionFilter);
        safeClick(cardTransactionFilter);
        
        // Click on Wallet Load Credit filter
        scrollToElement(walletLoadCreditFilter);
        safeClick(walletLoadCreditFilter);
        
        // Click on Apply Filters button
        safeClick(applyFiltersButton);
        
        // Wait for page to load after applying filters
        waitForPageLoad();
        waitForAjaxComplete();
        
        // Click on download icon
        safeClick(downloadIcon);
        
        // Click on Download text
        waitForElementVisibility(downloadText);
        safeClick(downloadText);
        
        // Click on Download button
        waitForElementToBeClickable(downloadButton);
        safeClick(downloadButton);
        
        // Wait for toast message
        waitForElementVisibility(toastMessage);
        
        // Close toast message
        if (isElementDisplayed(closeToastMessage)) {
            safeClick(closeToastMessage);
        }
        
        return this;
    }
    
    /**
     * Export with wallet type filter
     * TC006_Export_with_walletType_filter
     * 
     * @return ExpensePage instance for method chaining
     */
    public ExpensePagePOM exportWithWalletTypeFilter() {
        // Click on Imprest filter
        waitForElementVisibility(imprestFilter);
        safeClick(imprestFilter);
        
        // Click on Apply Filters button
        safeClick(applyFiltersButton);
        
        // Wait for page to load after applying filters
        waitForPageLoad();
        waitForAjaxComplete();
        
        // Click on download icon
        safeClick(downloadIcon);
        
        // Click on Download text
        waitForElementVisibility(downloadText);
        safeClick(downloadText);
        
        // Click on Download button
        waitForElementToBeClickable(downloadButton);
        safeClick(downloadButton);
        
        // Wait for toast message
        waitForElementVisibility(toastMessage);
        
        // Close toast message
        if (isElementDisplayed(closeToastMessage)) {
            safeClick(closeToastMessage);
        }
        
        return this;
    }
    
    /**
     * Check if toast message is displayed
     * 
     * @return true if toast message is displayed
     */
    public boolean isToastMessageDisplayed() {
        return isElementDisplayed(toastMessage);
    }
    
    /**
     * Get toast message text
     * 
     * @return Toast message text
     */
    public String getToastMessageText() {
        return getElementText(toastMessage);
    }
}
