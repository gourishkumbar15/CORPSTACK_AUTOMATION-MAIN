package pom_scripts.Employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import java.util.List;

/**
 * Page Object Model class for Cards
 */
public class CardsPagePOM extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CardsPagePOM.class);

    // Try multiple locators for Cards & Wallets link
    @FindBy(xpath = "//div[contains(text(),'Cards & Wallets')]")
    private WebElement cardsAndWalletsLink;

    @FindBy(xpath = "//span[contains(text(),'Cards & Wallets')]")
    private WebElement cardsAndWalletsSpan;

    @FindBy(xpath = "//a[contains(text(),'Cards & Wallets')]")
    private WebElement cardsAndWalletsAnchor;

    @FindBy(xpath = "//div[contains(@class,'menu-item')]//div[contains(text(),'Cards & Wallets')]")
    private WebElement cardsAndWalletsMenuItem;

    @FindBy(xpath = "//input[@type='checkbox']") // TC013_CardAction_Enable_Disable
    private WebElement toggleButton;

    @FindBy(xpath = "//input[@placeholder='Enter Comment']") // TC013_CardAction_Enable_Disable
    private WebElement commentInput;

    @FindBy(xpath = "//input[@placeholder='Enter Password']") // TC013_CardAction_Enable_Disable
    private WebElement passwordInput;

    @FindBy(xpath = "//button[normalize-space()='Proceed']") // TC013_CardAction_Enable_Disable
    private WebElement proceedButton;

    @FindBy(xpath = "//div[contains(@class,'customToastContent')]") // TC013_CardAction_Enable_Disable
    private WebElement toastMessage;

    @FindBy(xpath = "//img[@src='/assets/images/close-icon.svg']") // TC013_CardAction_Enable_Disable
    private WebElement closeToastButton;

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
     * Click on Cards & Wallets link with enhanced error handling and debugging
     */
    public void clickCardsAndWallets() throws InterruptedException {
        logger.info("Attempting to click Cards & Wallets link...");
        Thread.sleep(3000); // Wait for page load

        try {
            // Try to find the element using multiple strategies
            WebElement elementToClick = null;
            
            // Log the page source for debugging
            logger.debug("Current page source: {}", driver.getPageSource());
            
            // Try different locators
            if (isElementPresent(cardsAndWalletsLink)) {
                elementToClick = cardsAndWalletsLink;
                logger.info("Found Cards & Wallets link using div locator");
            } else if (isElementPresent(cardsAndWalletsSpan)) {
                elementToClick = cardsAndWalletsSpan;
                logger.info("Found Cards & Wallets link using span locator");
            } else if (isElementPresent(cardsAndWalletsAnchor)) {
                elementToClick = cardsAndWalletsAnchor;
                logger.info("Found Cards & Wallets link using anchor locator");
            } else if (isElementPresent(cardsAndWalletsMenuItem)) {
                elementToClick = cardsAndWalletsMenuItem;
                logger.info("Found Cards & Wallets link using menu item locator");
            }

            if (elementToClick != null) {
                waitForElementToBeClickable(elementToClick);
                elementToClick.click();
                logger.info("Successfully clicked Cards & Wallets link");
            } else {
                // Try finding by text content
                List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(), 'Cards & Wallets')]"));
                if (!elements.isEmpty()) {
                    logger.info("Found {} elements containing 'Cards & Wallets' text", elements.size());
                    for (WebElement element : elements) {
                        logger.info("Element tag: {}, text: {}", element.getTagName(), element.getText());
                    }
                    waitForElementToBeClickable(elements.get(0));
                    elements.get(0).click();
                    logger.info("Clicked first found element containing 'Cards & Wallets' text");
                } else {
                    throw new RuntimeException("Could not find Cards & Wallets link using any strategy");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to click Cards & Wallets link: {}", e.getMessage());
            throw new RuntimeException("Failed to click Cards & Wallets link: " + e.getMessage());
        }
    }

    private boolean isElementPresent(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Toggle card status
     */
    public void toggleCardStatus() {
        waitForElementToBeClickable(toggleButton);
        toggleButton.click();
    }

    /**
     * Check if card is enabled
     */
    public boolean isCardEnabled() {
        waitForElementVisibility(toggleButton);
        return toggleButton.isSelected();
    }

    /**
     * Enter comment
     */
    public void enterComment(String comment) {
        waitForElementVisibility(commentInput);
        commentInput.sendKeys(comment);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        waitForElementVisibility(passwordInput);
        passwordInput.sendKeys(password);
    }

    /**
     * Click proceed button
     */
    public void clickProceed() {
        waitForElementToBeClickable(proceedButton);
        proceedButton.click();
    }

    /**
     * Get toast message
     */
    public String getToastMessage() {
        waitForElementVisibility(toastMessage);
        return toastMessage.getText();
    }

    /**
     * Close toast message
     */
    public void closeToast() {
        waitForElementToBeClickable(closeToastButton);
        closeToastButton.click();
    }
} 