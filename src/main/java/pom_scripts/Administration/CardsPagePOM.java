package pom_scripts.Administration;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;
import java.util.List;

/**
 * Page Object class for Administration Cards page
 */
public class CardsPagePOM extends BasePage {
    
    @FindBy(xpath = "//span[@class='white-color']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement privilegedLink;
    
    @FindBy(xpath = "//div[@class='hw-avatar-container default user-avatar-on-hamburger medium']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement paLink;
    
    @FindBy(xpath = "//div[contains(text(),'Administration')]") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement administrationLink;
    
    @FindBy(xpath = "//body/div[@id='app']/div[@class='demo-big-content']/div[@class='dashboardLayout']/div[@class='dashboardLayoutInnerContent']/div[@class='dashbaordContentBlock full-width false']/div[@class='adminPageConatiner ']/div[@class='adminPageLeftConatainer']/div[@class='adminNaveListExpense']/div[1]/ul[1]/li[1]/div[1]/div[1]/div[1]/div[1]") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement cardsLink;
    
    @FindBy(xpath = "//div[normalize-space()='Assigned Cards']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement assignedCardsLink;
    
    @FindBy(xpath = "//div[@class='Select-placeholder']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement clickOnSelectUserDropdown;
    
    @FindBy(xpath = "//button[normalize-space()='Download']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement downloadButton;
    
    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement toastMessage;
    
    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC053_Bulk_Card_AssignDownload_atta
    private WebElement closeToastMessage;
    
    @FindBy(xpath = "//span[contains(@name,'export')]//i[@id='icon-undefined']") // TC054_Export_Assigned_Cards
    private WebElement exportIcon;
    
    @FindBy(xpath = "//div[@class='Select-placeholder']") // TC055_Select_User_Dropdown
    private WebElement userDropdown;
    
    /**
     * Constructor to initialize the CardsPage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public CardsPagePOM(WebDriver driver) {
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
     * Click on PA link
     */
    public void clickPALink() {
        waitForElementToBeClickable(paLink);
        paLink.click();
    }
    
    /**
     * Click on Administration link
     */
    public void clickAdministrationLink() {
        waitForElementToBeClickable(administrationLink);
        administrationLink.click();
    }
    
    /**
     * Click on Cards link
     */
    public void clickCardsLink() {
        waitForElementToBeClickable(cardsLink);
        cardsLink.click();
    }
    
    /**
     * Click on Assigned Cards link
     */
    public void clickAssignedCardsLink() {
        waitForElementToBeClickable(assignedCardsLink);
        assignedCardsLink.click();
    }
    

    /**
     * Click on Select Happay User dropdown and select a user
     * 
     * @param userName User name to select (e.g., "Gourish")
     * @throws InterruptedException 
     */
    public void clickSelectHappayUser(String userName) throws InterruptedException {
        waitForElementVisibility(clickOnSelectUserDropdown);
        Thread.sleep(1000);
        
        // For non-standard dropdowns that don't work with the Select class,
        // we can still use the click and sendKeys approach
        clickOnSelectUserDropdown.click();
        Thread.sleep(1000);
        clickOnSelectUserDropdown.sendKeys(userName, Keys.ENTER);
        
        // Note: If this was a standard HTML select element, we could use:
        // selectByVisibleText(clickOnSelectUserDropdown, userName);
    }
    
    /**
     * Click on Select Happay User dropdown and select default user "Gourish"
     * This method is kept for backward compatibility with existing tests
     * 
     * @throws InterruptedException 
     */
    public void clickSelectHappayUser() throws InterruptedException {
        clickSelectHappayUser("Gourish");
    }
    
    /**
     * Select user from dropdown by visible text
     * 
     * @param userName User name to select
     * @param dropdownElement Dropdown WebElement
     */
    public void selectUserFromDropdown(String userName, WebElement dropdownElement) {
        waitForElementVisibility(dropdownElement);
        selectByVisibleText(dropdownElement, userName);
    }
    
    /**
     * Click on Download button
     */
    public void clickDownloadButton() {
        waitForElementToBeClickable(downloadButton);
        downloadButton.click();
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
     * Click on Export icon
     */
    public void clickExportIcon() {
        waitForElementToBeClickable(exportIcon);
        exportIcon.click();
    }
    
    /**
     * Bulk card assign download attachment
          * @throws InterruptedException 
          */
         public void bulkCardAssignDownloadAttachment() throws InterruptedException {
        clickPrivilegedLink();
        clickPALink();
        clickAdministrationLink();
        clickCardsLink();
        clickAssignedCardsLink();
        clickSelectHappayUser();
        clickDownloadButton();
    }
    
    /**
     * Export assigned cards
     */
    public void exportAssignedCards() {
        clickPrivilegedLink();
        clickPALink();
        clickAdministrationLink();
        clickCardsLink();
        clickAssignedCardsLink();
        clickExportIcon();
        clickDownloadButton();
    }
    
    /**
     * Select user from user dropdown using Select class
     * 
     * @param userName User name to select
     */
    public void selectUserUsingSelectClass(String userName) {
        waitForElementVisibility(userDropdown);
        // Using the Select class method from BasePage
        Select userSelect = getSelect(userDropdown);
        userSelect.selectByVisibleText(userName);
    }
    
    /**
     * Get all available users from dropdown
     * 
     * @return List of WebElements representing available users
     */
    public List<WebElement> getAllAvailableUsers() {
        waitForElementVisibility(userDropdown);
        // Using the getOptions method from BasePage
        return getOptions(userDropdown);
    }
    
    /**
     * Get currently selected user
     * 
     * @return Currently selected user name
     */
    public String getSelectedUser() {
        waitForElementVisibility(userDropdown);
        // Using the getSelectedOption method from BasePage
        return getSelectedOption(userDropdown).getText();
    }
}
