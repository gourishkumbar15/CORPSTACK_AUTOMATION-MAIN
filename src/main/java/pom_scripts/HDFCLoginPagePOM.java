package pom_scripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HDFCLoginPagePOM {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[@id='slider-2-signIn-container']")//input[@id='email-id']
    private WebElement emailLoginSwitch;

    @FindBy(xpath = "//input[@id='email-id']")
    private WebElement emailField;

    @FindBy(xpath = "//button[normalize-space()='Next']")
    private WebElement nextButton;

    @FindBy(xpath = "//input[@id='password-field']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[normalize-space()='Sign In']")
    private WebElement signInButton;

    @FindBy(xpath = "//div[contains(@class,'dashboardLayout')]")
    private WebElement dashboardLayout;

    public HDFCLoginPagePOM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void clickEmailLogin() {
        wait.until(ExpectedConditions.visibilityOf(emailLoginSwitch));
        emailLoginSwitch.click();
    }

    public void enterEmailId(String username) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(username);
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    public void waitForDashboard() {
        wait.until(ExpectedConditions.visibilityOf(dashboardLayout));
    }

    /**
     * Complete login process
     */
    public void login(String username, String password) {
        enterEmailId(username);
        clickNext();
        enterPassword(password);
        clickSignIn();
        waitForDashboard();
    }
} 