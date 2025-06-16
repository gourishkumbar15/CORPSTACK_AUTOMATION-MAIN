package pom_scripts.Privileged;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;

/**
 * Page Object class for Privileged Users page
 */
public class UsersPagePOM extends BasePage {
    
    @FindBy(xpath = "//span[@class='white-color']") // TC026_Export_wallet_limi_History_email
    private WebElement privilegedLink;
    
    @FindBy(xpath = "//div[normalize-space()='Users']") // TC026_Export_wallet_limi_History_email
    private WebElement usersLink;
    
    @FindBy(xpath = "//button[@id='card-btn-popup']") // TC026_Export_wallet_limi_History_email
    private WebElement moreButton;
    
    @FindBy(xpath = "//div[contains(text(),'Export Wallet Limit History')]") // TC026_Export_wallet_limi_History_email
    private WebElement exportWalletLimitHistoryOption;
    
    @FindBy(xpath = "//button[contains(text(),'Download')]") // TC026_Export_wallet_limi_History_email
    private WebElement downloadButton;
    
    @FindBy(xpath = "//div[contains(text(),'Load Wallet using Excel')]") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement loadWalletUsingExcelOption;
    
    @FindBy(xpath = "//div[@class='file-p']//div[1]") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement downloadUserInformationButton;
    
    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement toastMessage;
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC027_Bulk_Load_Wallet_S3_Download
    private WebElement closeToastMessage;
    
    @FindBy(xpath = "//div[contains(text(),'Withdraw Wallet using Excel')]") // TC029_Bulk_Withdraw_Wallet
    private WebElement withdrawWalletUsingExcelOption;
    
    @FindBy(xpath = "//span[@name='download']//i[@id='icon-undefined']") // TC030_Export_Users_Local
    private WebElement downloadIcon;
    
    @FindBy(xpath = "//input[@placeholder='Add Mail Id & Press Enter']") // TC031_Export_Users_Email
    private WebElement emailInput;
    
    @FindBy(xpath = "//div[contains(text(),'Self Wallet Load')]") // TC032_Self_User_Wallet_Load
    private WebElement selfWalletLoadOption;
    
    @FindBy(xpath = "//input[@id='Password']") // TC032_Self_User_Wallet_Load
    private WebElement passwordField;
    
    @FindBy(xpath = "//input[@id='comment']") // TC032_Self_User_Wallet_Load
    private WebElement commentField;
    
    @FindBy(xpath = "//button[normalize-space()='Submit']") // TC032_Self_User_Wallet_Load
    private WebElement submitButton;
    
    @FindBy(xpath = "//div[contains(text(),'Self Wallet Withdraw')]") // TC033_Self_User_Wallet_Withdraw
    private WebElement selfWalletWithdrawOption;
    
    @FindBy(xpath = "//input[@id='amount']") // TC033_Self_User_Wallet_Withdraw
    private WebElement amountField;
    
    @FindBy(xpath = "//div[contains(text(),'Update Wallet Limits using Excel')]") // TC034_Update_limit_download__attachment
    private WebElement updateWalletLimitsUsingExcelOption;
    
    @FindBy(xpath = "//i[@id='icon-icon-popup-0']") // TC036_Set_user_wallet_limit
    private WebElement userPopupIcon;
    
    @FindBy(xpath = "//div[contains(text(),'Set Limit')]") // TC036_Set_user_wallet_limit
    private WebElement setLimitOption;
    
    @FindBy(xpath="//button[normalize-space()='Edit Limit']")
    private WebElement editLimitButton;

    @FindBy(xpath = "//div[1]//div[1]//div[3]//div[1]//div[2]//div[3]//div[1]//div[1]//div[1]//div[1]//div[1]//div[1]//input[1]") // TC036_Set_user_wallet_limit
    private WebElement enterCountField;
    
    @FindBy(xpath = "//input[@value='1']") // TC036_Set_user_wallet_limit
    private WebElement enterAmountField;
    
    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]") // TC036_Set_user_wallet_limit
    private WebElement enterCountField2;
    
    @FindBy(xpath = "//button[contains(@type,'button')]") // TC036_Set_user_wallet_limit
    private WebElement saveButton;
    
    @FindBy(xpath = "//label[@aria-label='Active']//span[@class='height-16 width-16']") // TC037_Export_user_with_userStatus_filter
    private WebElement activeCheckbox;
    
    @FindBy(xpath = "//button[normalize-space()='Apply Filters']") // TC037_Export_user_with_userStatus_filter
    private WebElement applyFiltersButton;
    
    @FindBy(xpath = "//div[normalize-space()='Expenses']") // TC043_Emp_Expense_Download_Local
    private WebElement expensesLink;
    
    @FindBy(xpath = "//div[contains(text(),'Download Expense Report')]") // TC043_Emp_Expense_Download_Local
    private WebElement downloadExpenseReportText;
    
    @FindBy(xpath = "//div[@class='customToastContent']") // TC043_Emp_Expense_Download_Local
    private WebElement fileGeneratedSuccessfullyMessage;
    
    @FindBy(xpath = "//div[normalize-space()='Finances']") // TC045_Export_History_Success
    private WebElement financesLink;
    
    @FindBy(xpath = "//div[@class='HPGrid HPGrid-container-row HPGrid-container-flex-start HPGrid-container-align-center']//div[contains(text(),'Passbook')]") // TC045_Export_History_Success
    private WebElement passbookLink;
    
    @FindBy(xpath = "//button[@type='button']") // TC045_Export_History_Success
    private WebElement exportButton;
    
    /**
     * Constructor to initialize the UsersPage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public UsersPagePOM(WebDriver driver) {
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
     * Click on Users link
          * @throws InterruptedException 
          */
         public void clickUsersLink() throws InterruptedException {
        waitForElementToBeClickable(usersLink);
        usersLink.click();
        Thread.sleep(3000);
    }
    
    /**
     * Click on More button
     */
    public void clickMoreButton() {
        waitForElementToBeClickable(moreButton);
        waitForPageLoad();
        moreButton.click();
    }
    
    /**
     * Click on Export Wallet Limit History option
     */
    public void clickExportWalletLimitHistoryOption() {
        waitForElementToBeClickable(exportWalletLimitHistoryOption);
        exportWalletLimitHistoryOption.click();
    }
    
    /**
     * Click on Download button
     */
    public void clickDownloadButton() {
        waitForElementToBeClickable(downloadButton);
        downloadButton.click();
    }
    
    /**
     * Click on Load Wallet using Excel option
          * @throws InterruptedException 
          */
         public void clickLoadWalletUsingExcelOption() throws InterruptedException {
        waitForElementToBeClickable(loadWalletUsingExcelOption);
        loadWalletUsingExcelOption.click();
        Thread.sleep(2000);
    }
    
    /**
     * Click on Download User Information button
     */
    public void clickDownloadUserInformationButton() {
        waitForElementToBeClickable(downloadUserInformationButton);
        downloadUserInformationButton.click();
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
     * Click on Withdraw Wallet using Excel option
     */
    public void clickWithdrawWalletUsingExcelOption() {
        waitForElementToBeClickable(withdrawWalletUsingExcelOption);
        withdrawWalletUsingExcelOption.click();
    }
    
    /**
     * Click on Download icon
     */
    public void clickDownloadIcon() {
        waitForPageLoad();
        waitForElementToBeClickable(downloadIcon);
        downloadIcon.click();
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
     * Click on Self Wallet Load option
          * @throws InterruptedException 
          */
         public void clickSelfWalletLoadOption() throws InterruptedException {
        waitForElementToBeClickable(selfWalletLoadOption);
        selfWalletLoadOption.click();
        Thread.sleep(2000);
    }
    
    /**
     * Enter password
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        waitForElementVisibility(passwordField);
        enterText(passwordField, password);
    }
    
    /**
     * Enter comment
     * 
     * @param comment Comment to enter
     */
    public void enterComment(String comment) {
        waitForElementVisibility(commentField);
        enterText(commentField, comment);
    }
    
    /**
     * Click on Submit button
     */
    public void clickSubmitButton() {
        waitForElementToBeClickable(submitButton);
        submitButton.click();
    }
    
    /**
     * Click on Self Wallet Withdraw option
     */
    public void clickSelfWalletWithdrawOption() {
        waitForElementToBeClickable(selfWalletWithdrawOption);
        selfWalletWithdrawOption.click();
    }
    
    /**
     * Enter amount
     * 
     * @param amount Amount to enter
     */
    public void enterAmount(String amount) {
        waitForElementVisibility(amountField);
        enterText(amountField, amount);
    }
    
    /**
     * Click on Update Wallet Limits using Excel option
     */
    public void clickUpdateWalletLimitsUsingExcelOption() {
        waitForElementToBeClickable(updateWalletLimitsUsingExcelOption);
        updateWalletLimitsUsingExcelOption.click();
    }
    
    /**
     * Click on User Popup icon
     */
    public void clickUserPopupIcon() {
        waitForElementToBeClickable(userPopupIcon);
        userPopupIcon.click();
    }
    
    /**
     * Click on Set Limit option
          * @throws InterruptedException 
          */
         public void clickSetLimitOption() throws InterruptedException {
        waitForElementToBeClickable(setLimitOption);
        setLimitOption.click();
        Thread.sleep(2000);
    }
    
        /**
     * Click on Set Limit option
          * @throws InterruptedException 
          */
          public void clickeditLimitButton() throws InterruptedException {
            waitForElementToBeClickable(editLimitButton);
            editLimitButton.click();
            Thread.sleep(2000);
        }

    /**
     * Enter count
     * 
     * @param count Count to enter
     */
    public void enterCount(String count) {
        waitForElementVisibility(enterCountField);
        enterText(enterCountField, count);
    }
    
    /**
     * Click on Save button
     */
    public void clickSaveButton() {
        waitForElementToBeClickable(saveButton);
        saveButton.click();
    }
    
    /**
     * Click on Active checkbox
     */
    public void clickActiveCheckbox() {
        waitForElementToBeClickable(activeCheckbox);
        activeCheckbox.click();
    }
    
    /**
     * Click on Apply Filters button
          * @throws InterruptedException 
          */
         public void clickApplyFiltersButton() throws InterruptedException {
        waitForElementToBeClickable(applyFiltersButton);
        applyFiltersButton.click();
        Thread.sleep(2000);
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
     */
    public void clickDownloadExpenseReportText() {
        waitForElementToBeClickable(downloadExpenseReportText);
        downloadExpenseReportText.click();
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
     */
    public void clickPassbookLink() {
        waitForElementToBeClickable(passbookLink);
        passbookLink.click();
    }
    
    /**
     * Click on Export button
     */
    public void clickExportButton() {
        waitForElementToBeClickable(exportButton);
        exportButton.click();
    }
    
    /**
     * Export wallet limit history
          * @throws InterruptedException 
          */
         public void exportWalletLimitHistory() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickExportWalletLimitHistoryOption();
        clickDownloadButton();
    }
    
    /**
     * Load wallet using Excel
          * @throws InterruptedException 
          */
         public void loadWalletUsingExcel() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickLoadWalletUsingExcelOption();
        clickDownloadUserInformationButton();
    }
    
    /**
     * Withdraw wallet using Excel
          * @throws InterruptedException 
          */
         public void withdrawWalletUsingExcel() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickWithdrawWalletUsingExcelOption();
        clickDownloadUserInformationButton();
    }
    
    /**
     * Export users
          * @throws InterruptedException 
          */
         public void exportUsers() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickDownloadIcon();
        clickDownloadButton();
    }
    
    /**
     * Export users to email
     * 
     * @param email Email address to send report to
          * @throws InterruptedException 
          */
         public void exportUsersToEmail(String email) throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickDownloadIcon();
        enterEmail(email);
        clickDownloadButton();
    }
    
    /**
     * Self user wallet load
     * 
     * @param password Password to enter
     * @param comment Comment to enter
          * @throws InterruptedException 
          */
         public void selfUserWalletLoad(String password, String comment) throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickSelfWalletLoadOption();
        enterPassword(password);
        enterComment(comment);
        clickSubmitButton();
    }
    
    /**
     * Self user wallet withdraw
     * 
     * @param amount Amount to withdraw
     * @param password Password to enter
     * @param comment Comment to enter
          * @throws InterruptedException 
          */
         public void selfUserWalletWithdraw(String amount, String password, String comment) throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickSelfWalletWithdrawOption();
        enterAmount(amount);
        enterPassword(password);
        enterComment(comment);
        clickSubmitButton();
    }
    
    /**
     * Update wallet limits using Excel
          * @throws InterruptedException 
          */
         public void updateWalletLimitsUsingExcel() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickMoreButton();
        clickUpdateWalletLimitsUsingExcelOption();
        clickDownloadUserInformationButton();
    }
    
    /**
     * Set user wallet limit
     * 
     * @param count Count to enter
     * @param amount Amount to enter
          * @throws InterruptedException 
          */
         public void setUserWalletLimit(String count, String amount) throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickUserPopupIcon();
        clickSetLimitOption();
        enterCount(count);
        clickSaveButton();
    }
    
    /**
     * Export user with user status filter
          * @throws InterruptedException 
          */
        public void exportUserWithUserStatusFilter() throws InterruptedException {
        clickPrivilegedLink();
        clickUsersLink();
        clickActiveCheckbox();
        clickApplyFiltersButton();
        clickDownloadIcon();
        clickDownloadButton();
    }
    
    /**
     * Export expenses
     */
    public void exportExpenses() {
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
     */
    public void exportExpensesToEmail(String email) {
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
        clickFinancesLink();
        clickPassbookLink();
        clickDownloadIcon();
        clickExportButton();
    }
}
