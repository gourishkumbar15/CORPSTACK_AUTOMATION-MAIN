package generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;

/**
 * BasePage class that provides common functionality for all Page Object Model classes.
 * This class contains reusable methods for interacting with web elements.
 */
public class BasePage {
    private static final Logger logger = LoggerUtil.getLogger(BasePage.class);
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    /**
     * Constructor to initialize the BasePage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    /**
     * Wait for an element to be clickable
     * 
     * @param element WebElement to wait for
     * @return The WebElement once it is clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for an element to be visible
     * 
     * @param element WebElement to wait for
     * @return The WebElement once it is visible
     */
    protected WebElement waitForElementVisibility(WebElement element) {
        logger.debug("Waiting for element visibility: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Click on an element using JavaScript
     * 
     * @param element WebElement to click
     */
    protected void jsClick(WebElement element) {
        logger.debug("Performing JavaScript click on element: {}", element);
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Scroll to an element using JavaScript
     * 
     * @param element WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        logger.debug("Scrolling to element: {}", element);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Enter text into an input field
     * 
     * @param element WebElement to enter text into
     * @param text Text to enter
     */
    protected void enterText(WebElement element, String text) {
        waitForElementVisibility(element);
        element.clear();
        element.sendKeys(text);
        logger.debug("Entered text '{}' into element: {}", text, element);
    }

    /**
     * Check if an element is displayed
     * 
     * @param element WebElement to check
     * @return true if the element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Element {} is displayed: {}", element, isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Element {} is not displayed: {}", element, e.getMessage());
            return false;
        }
    }

    /**
     * Get text from an element
     * 
     * @param element WebElement to get text from
     * @return The text of the element
     */
    protected String getElementText(WebElement element) {
        waitForElementVisibility(element);
        String text = element.getText();
        logger.debug("Got text '{}' from element: {}", text, element);
        return text;
    }

    /**
     * Hover over an element
     * 
     * @param element WebElement to hover over
     */
    protected void hoverOverElement(WebElement element) {
        logger.debug("Hovering over element: {}", element);
        actions.moveToElement(element).perform();
    }

    /**
     * Wait for a specific duration
     * 
     * @param milliseconds Time to wait in milliseconds
     */
    protected void waitFor(long milliseconds) {
        try {
            logger.debug("Waiting for {} milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Wait interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        logger.debug("Waiting for page to load completely");
        ExpectedCondition<Boolean> pageLoadCondition = driver -> {
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        };
        wait.until(pageLoadCondition);
        logger.debug("Page loaded completely");
    }
    
    /**
     * Wait for AJAX calls to complete
     */
    protected void waitForAjaxComplete() {
        logger.debug("Waiting for AJAX calls to complete");
        try {
            ExpectedCondition<Boolean> ajaxComplete = driver -> {
                return (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return jQuery.active == 0");
            };
            wait.until(ajaxComplete);
            logger.debug("AJAX calls completed");
        } catch (Exception e) {
            // jQuery might not be present on the page
            logger.debug("jQuery is not available on the page");
        }
    }
    
    /**
     * Wait for element to be clickable by locator
     * 
     * @param locator By locator to find the element
     * @return The WebElement once it is clickable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        logger.debug("Waiting for element to be clickable by locator: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be visible by locator
     * 
     * @param locator By locator to find the element
     * @return The WebElement once it is visible
     */
    protected WebElement waitForElementVisibility(By locator) {
        logger.debug("Waiting for element visibility by locator: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be invisible
     * 
     * @param element WebElement to wait for
     * @return true if the element is invisible
     */
    protected boolean waitForElementInvisibility(WebElement element) {
        logger.debug("Waiting for element invisibility: {}", element);
        boolean result = wait.until(ExpectedConditions.invisibilityOf(element));
        logger.debug("Element invisibility result: {}", result);
        return result;
    }
    
    /**
     * Wait for element to be invisible by locator
     * 
     * @param locator By locator to find the element
     * @return true if the element is invisible
     */
    protected boolean waitForElementInvisibility(By locator) {
        logger.debug("Waiting for element invisibility by locator: {}", locator);
        boolean result = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        logger.debug("Element invisibility result: {}", result);
        return result;
    }
    
    /**
     * Wait for element to be present in DOM
     * 
     * @param loginAuditLogLink By locator to find the element
     * @return The WebElement once it is present
     */
    protected WebElement waitForElementPresence(WebElement loginAuditLogLink) {
        return wait.until(ExpectedConditions.presenceOfElementLocated((By) loginAuditLogLink));
    }
    
    /**
     * Wait for multiple elements to be visible
     * 
     * @param loginAuditLogLink By locator to find the elements
     * @return List of WebElements once they are visible
     */
    protected List<WebElement> waitForElementsVisibility(WebElement loginAuditLogLink) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By) loginAuditLogLink));
    }
    
    /**
     * Wait for multiple elements to be present in DOM
     * 
     * @param locator By locator to find the elements
     * @return List of WebElements once they are present
     */
    protected List<WebElement> waitForElementsPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    /**
     * Safe click method with retries
     * 
     * @param element WebElement to click
     */
    protected void safeClick(WebElement element) {
        logger.debug("Attempting safe click on element: {}", element);
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForElementToBeClickable(element).click();
                logger.debug("Successfully clicked element on attempt {}", attempts + 1);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                logger.debug("StaleElementReferenceException on attempt {}: {}", attempts, e.getMessage());
                if (attempts == 3) {
                    logger.error("Failed to click element after 3 attempts: {}", element);
                    throw e;
                }
            }
        }
    }
    
    /**
     * Safe click method with retries using locator
     * 
     * @param locator By locator to find the element
     */
    protected void safeClick(By locator) {
        logger.debug("Attempting safe click on element by locator: {}", locator);
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForElementToBeClickable(locator).click();
                logger.debug("Successfully clicked element by locator on attempt {}", attempts + 1);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                logger.debug("StaleElementReferenceException on attempt {}: {}", attempts, e.getMessage());
                if (attempts == 3) {
                    logger.error("Failed to click element by locator after 3 attempts: {}", locator);
                    throw e;
                }
            }
        }
    }
    
    /**
     * Click with fallback to JavaScript if regular click fails
     * 
     * @param element WebElement to click
     */
    protected void clickWithFallback(WebElement element) {
        logger.debug("Attempting click with fallback on element: {}", element);
        try {
            waitForElementToBeClickable(element).click();
            logger.debug("Successfully clicked element with regular click");
        } catch (Exception e) {
            logger.debug("Regular click failed, using JavaScript click: {}", e.getMessage());
            jsClick(element);
        }
    }
    
    /**
     * Check if element exists without waiting
     * 
     * @param locator By locator to find the element
     * @return true if the element exists, false otherwise
     */
    protected boolean isElementExists(By locator) {
        try {
            driver.findElement(locator);
            logger.debug("Element exists by locator: {}", locator);
            return true;
        } catch (NoSuchElementException e) {
            logger.debug("Element does not exist by locator: {}", locator);
            return false;
        }
    }
    
    /**
     * Wait for element with custom timeout
     * 
     * @param locator By locator to find the element
     * @param timeoutInSeconds Timeout in seconds
     * @return The WebElement once it is visible
     */
    protected WebElement waitForElementWithTimeout(By locator, long timeoutInSeconds) {
        logger.debug("Waiting for element by locator with timeout of {} seconds: {}", timeoutInSeconds, locator);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element found within timeout");
        return element;
    }
    
    /**
     * Wait for element with custom condition
     * 
     * @param condition ExpectedCondition to wait for
     * @param <T> Type of the expected condition
     * @return The result of the condition
     */
    protected <T> T waitForCustomCondition(ExpectedCondition<T> condition) {
        return wait.until(condition);
    }
    
    /**
     * Wait for alert to be present
     * 
     * @return true if alert is present
     */
    protected boolean waitForAlertPresent() {
        logger.debug("Waiting for alert to be present");
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            logger.debug("Alert is present");
            return true;
        } catch (TimeoutException e) {
            logger.debug("Alert is not present: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Wait for text to be present in element
     * 
     * @param element WebElement to check
     * @param text Text to wait for
     * @return true if text is present
     */
    protected boolean waitForTextPresent(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
    /**
     * Wait for attribute to contain value
     * 
     * @param element WebElement to check
     * @param attribute Attribute name
     * @param value Value to wait for
     * @return true if attribute contains value
     */
    protected boolean waitForAttributeContains(WebElement element, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }
    
    /**
     * Wait for element to have specific attribute value
     * 
     * @param element WebElement to check
     * @param attribute Attribute name
     * @param value Value to wait for
     * @return true if attribute equals value
     */
    protected boolean waitForAttributeToBe(WebElement element, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }
    
    /**
     * Create a Select object for dropdown operations
     * 
     * @param element WebElement representing the dropdown
     * @return Select object for the dropdown
     */
    protected Select getSelect(WebElement element) {
        waitForElementVisibility(element);
        return new Select(element);
    }
    
    /**
     * Select option by visible text
     * 
     * @param element WebElement representing the dropdown
     * @param text Visible text to select
     */
    protected void selectByVisibleText(WebElement element, String text) {
        getSelect(element).selectByVisibleText(text);
    }
    
    /**
     * Select option by value
     * 
     * @param element WebElement representing the dropdown
     * @param value Value to select
     */
    protected void selectByValue(WebElement element, String value) {
        getSelect(element).selectByValue(value);
    }
    
    /**
     * Select option by index
     * 
     * @param element WebElement representing the dropdown
     * @param index Index to select
     */
    protected void selectByIndex(WebElement element, int index) {
        getSelect(element).selectByIndex(index);
    }
    
    /**
     * Get all options from dropdown
     * 
     * @param element WebElement representing the dropdown
     * @return List of WebElements representing the options
     */
    protected List<WebElement> getOptions(WebElement element) {
        return getSelect(element).getOptions();
    }
    
    /**
     * Get selected option from dropdown
     * 
     * @param element WebElement representing the dropdown
     * @return WebElement representing the selected option
     */
    protected WebElement getSelectedOption(WebElement element) {
        return getSelect(element).getFirstSelectedOption();
    }
    
    /**
     * Check if dropdown supports multiple selections
     * 
     * @param element WebElement representing the dropdown
     * @return true if multiple selections are supported
     */
    protected boolean isMultiple(WebElement element) {
        return getSelect(element).isMultiple();
    }
}
