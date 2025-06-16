# Test Case Format Knowledge Base

## Overview

This document provides a comprehensive guide to the test case format used in the Corpstack Automation framework. It explains how to structure test cases, the required fields, and how to use the test generator to create automated test cases.

## Test Case Format

Test cases are defined in a CSV or Excel file with the following columns:

| Column | Description | Example |
|--------|-------------|---------|
| Folder | The folder name where the test case will be stored | Employee |
| Class | The class name for the test case | Expense |
| Test Scenario | A brief description of the test scenario | Export employee Expenses Default download |
| Automated Test Case Name | A unique identifier for the test case | TC001_Emp_Expense_Download_Local |
| Categories 1 | Primary category for the test case | Local Download |
| Categories 2 | Secondary category for the test case (optional) | |
| action and Xpaths | Comma-separated list of actions to perform | Click on export button, verify download success |

### Example Test Case

```
Folder,Class,Test Scenario,Automated Test Case Name,Categories 1,Categories 2,action and Xpaths
Employee,Expense,Export employee Expenses Default download,TC001_Emp_Expense_Download_Local,Local Download,,Click on export button, verify download success
```

## Project Structure

The test automation framework follows the Page Object Model (POM) design pattern. The project structure is as follows:

```
src/
├── main/
│   ├── java/
│   │   ├── generic/
│   │   │   ├── BasePage.java
│   │   │   ├── BaseTest.java
│   │   │   ├── ExcelUtility.java
│   │   │   └── TestGenerator.java
│   │   ├── pom_scripts/
│   │   │   ├── Employee/
│   │   │   │   └── ...
│   │   │   ├──Privileged
│   │   │   |   └── ...
│   │   └── test_generator/
│   │       └── GenerateTests.java
├── test/
│   ├── java/
│   │   ├── test_scripts/
│   │   │   ├── Employee/
│   │   │   │   └── ...
│   │   │   └── Privileged
|   |   |       └── ...   
└── ...
```

## Test Generation Process

The test generation process involves the following steps:

1. Define test cases in a CSV or Excel file
2. Run the `GenerateTests` class to generate the test cases
3. Update the generated test cases with actual XPaths and implementation details

### Running the Test Generator

To run the test generator, execute the following command:

```bash
mvn compile exec:java -Dexec.mainClass="test_generator.GenerateTests"
```

## Generated Files

The test generator creates two types of files:

1. **Page Object Model (POM) Classes**: These classes contain the WebElements and methods for interacting with the application.
2. **Test Classes**: These classes contain the test methods that use the POM classes to execute the test scenarios.

### Page Object Model (POM) Class

The POM class is generated in the `src/main/java/pom_scripts/{Folder}/` directory. It contains:

- WebElements for each action in the test case
- Methods for interacting with the WebElements
- Constructor to initialize the page with WebDriver instance

Example POM class:

```java
package pom_scripts.Employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import generic.BasePage;

/**
 * Page Object Model class for Expense
 */
public class ExpensePage extends BasePage {

    @FindBy(xpath = "//placeholder-xpath-for-button") // TC001_Emp_Expense_Download_Local
    private WebElement button;

    @FindBy(xpath = "//placeholder-xpath-for-success") // TC001_Emp_Expense_Download_Local
    private WebElement success;

    /**
     * Constructor to initialize the ExpensePage with WebDriver instance
     * 
     * @param driver WebDriver instance
     */
    public ExpensePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Click button
     */
    public void clickButton() {
        waitForElementToBeClickable(button);
        button.click();
    }

    /**
     * Verify success
     */
    public boolean verifySuccess() {
        waitForElementVisibility(success);
        return success.isDisplayed();
    }
}
```

### Test Class

The test class is generated in the `src/test/java/test_scripts/{Folder}/` directory. It contains:

- Test methods for each test case
- Annotations for TestNG and Allure reporting
- Setup method to initialize the page
- Exception handling and reporting

Example test class:

```java
package test_scripts.Employee;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import generic.BaseTest;
import pom_scripts.Employee.ExpensePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import property.Test_DataByPropertyFILE;

/**
 * Test class for Expense
 */
public class ExpenseTest extends BaseTest {

    private ExpensePage page;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        login();
        page = new ExpensePage(driver);
    }

    @Test(description = "Export employee Expenses Default download", testName = "TC001_Emp_Expense_Download_Local")
    @Description("Export employee Expenses Default download")
    @Epic("Employee")
    @Feature("Expense")
    @Story("Export employee Expenses Default download")
    @Severity(SeverityLevel.NORMAL)
    public void testEmpExpenseDownloadLocal() {
        try {
            Reporter.log("🔍 Starting Export employee Expenses Default download Test...");

            // Click button
            page.clickButton();
            Reporter.log("✅ Click button");

            // Verify success
            boolean isVerified = page.verifySuccess();
            Assert.assertTrue(isVerified, "Failed to verify success");
            Reporter.log("✅ Verify success - Passed");

            Reporter.log("✅ Export employee Expenses Default download Test Completed Successfully");
        } catch (Exception e) {
            Reporter.log("❌ Export employee Expenses Default download Test Failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
```

## Customizing Generated Tests

After generating the test cases, you need to update the following:

1. **XPaths**: Replace the placeholder XPaths with actual XPaths for the WebElements
2. **Test Data**: Update the test data in the methods (e.g., replace `"test data"` with actual test data)
3. **Additional Methods**: Add any additional methods required for the test case

## Best Practices

1. **Naming Convention**: Follow a consistent naming convention for test cases, methods, and WebElements
2. **Test Case ID**: Include the test case ID in the WebElement annotations for traceability
3. **Exception Handling**: Use try-catch blocks to handle exceptions and report failures
4. **Reporting**: Use Reporter.log() to log test steps and results
5. **Assertions**: Use assertions to validate test results
6. **Page Object Model**: Keep the page object model classes focused on page interactions and test classes focused on test logic

## Utility Classes

The framework includes several utility classes to help with test automation:

- **BasePage**: Base class for all page object model classes with common methods for WebElement interactions
- **BaseTest**: Base class for all test classes with common methods for test setup and teardown
- **ExcelUtility**: Utility class for reading data from Excel files
- **TestGenerator**: Utility class for generating test cases from Excel data

## Reporting

The framework uses Allure and TestNG for reporting. The following annotations are used for Allure reporting:

- **@Description**: Description of the test case
- **@Epic**: Epic name (folder name)
- **@Feature**: Feature name (class name)
- **@Story**: Story name (test scenario)
- **@Severity**: Severity level of the test case

## Conclusion

This knowledge base provides a comprehensive guide to the test case format used in the Corpstack Automation framework. By following this guide, you can create and maintain automated test cases efficiently.
