package test_generator;

import generic.TestGenerator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Program to generate test cases from Excel data
 */
public class GenerateTests {
    
    public static void main(String[] args) {
        try {
            // Create a log file
            FileWriter logWriter = new FileWriter("test_generator.log");
            
            logWriter.write("Starting test case generation...\n");
            
            // Get test data from Excel file
            List<Map<String, String>> testData = TestGenerator.getTestData();
            
            if (testData.isEmpty()) {
                logWriter.write("No test data found in Excel file.\n");
                logWriter.close();
                return;
            }
            
            logWriter.write("Found " + testData.size() + " test cases in Excel file.\n");
            
            // Group test cases by folder and class
            Map<String, Map<String, List<Map<String, String>>>> groupedTests = groupTestCases(testData);
            
            // Generate test cases
            generateTestCases(groupedTests);
            
            logWriter.write("Test case generation completed.\n");
            logWriter.close();
        } catch (Exception e) {
            try {
                FileWriter errorWriter = new FileWriter("test_generator_error.log");
                errorWriter.write("Error: " + e.getMessage() + "\n");
                e.printStackTrace(new java.io.PrintWriter(errorWriter));
                errorWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    /**
     * Group test cases by folder and class
     * 
     * @param testData List of test data
     * @return Map of folder -> class -> test cases
     */
    private static Map<String, Map<String, List<Map<String, String>>>> groupTestCases(List<Map<String, String>> testData) {
        Map<String, Map<String, List<Map<String, String>>>> groupedTests = new java.util.HashMap<>();
        
        for (Map<String, String> test : testData) {
            String folder = test.getOrDefault("Folder", "").trim();
            String className = test.getOrDefault("Class", "").trim();
            
            if (folder.isEmpty() || className.isEmpty()) {
                System.err.println("Skipping test case with missing folder or class: " + test);
                continue;
            }
            
            // Add folder if not exists
            if (!groupedTests.containsKey(folder)) {
                groupedTests.put(folder, new java.util.HashMap<>());
            }
            
            // Add class if not exists
            Map<String, List<Map<String, String>>> folderMap = groupedTests.get(folder);
            if (!folderMap.containsKey(className)) {
                folderMap.put(className, new java.util.ArrayList<>());
            }
            
            // Add test case
            folderMap.get(className).add(test);
        }
        
        return groupedTests;
    }
    
    /**
     * Generate test cases
     * 
     * @param groupedTests Map of folder -> class -> test cases
     */
    private static void generateTestCases(Map<String, Map<String, List<Map<String, String>>>> groupedTests) {
        for (Map.Entry<String, Map<String, List<Map<String, String>>>> folderEntry : groupedTests.entrySet()) {
            String folder = folderEntry.getKey();
            Map<String, List<Map<String, String>>> classMap = folderEntry.getValue();
            
            for (Map.Entry<String, List<Map<String, String>>> classEntry : classMap.entrySet()) {
                String className = classEntry.getKey();
                List<Map<String, String>> tests = classEntry.getValue();
                
                // Generate POM class
                generatePOMClass(folder, className, tests);
                
                // Generate test class
                generateTestClass(folder, className, tests);
            }
        }
    }
    
    /**
     * Generate POM class
     * 
     * @param folder Folder name
     * @param className Class name
     * @param tests List of test cases
     */
    private static void generatePOMClass(String folder, String className, List<Map<String, String>> tests) {
        String packageName = "pom_scripts." + folder;
        String pomClassName = className + "Page";
        String filePath = "src/main/java/pom_scripts/" + folder + "/" + pomClassName + ".java";
        
        // Create directory if not exists
        new File("src/main/java/pom_scripts/" + folder).mkdirs();
        
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import org.openqa.selenium.WebDriver;\n");
        sb.append("import org.openqa.selenium.WebElement;\n");
        sb.append("import org.openqa.selenium.support.FindBy;\n");
        sb.append("import org.openqa.selenium.support.PageFactory;\n");
        sb.append("import generic.BasePage;\n\n");
        sb.append("/**\n");
        sb.append(" * Page Object Model class for ").append(className).append("\n");
        sb.append(" */\n");
        sb.append("public class ").append(pomClassName).append(" extends BasePage {\n\n");
        
        // Add WebElements
        for (Map<String, String> test : tests) {
            String testCaseName = test.getOrDefault("Automated Test Case Name", "").trim();
            String actionAndXpaths = test.getOrDefault("action and Xpaths", "").trim();
            
            if (testCaseName.isEmpty() || actionAndXpaths.isEmpty()) {
                continue;
            }
            
            // Parse action and XPaths - split by comma followed by space to avoid splitting within actions
            String[] actions = actionAndXpaths.split(", ");
            for (int i = 0; i < actions.length; i++) {
                String action = actions[i].trim();
                
                // Extract element name from action
                String elementName = extractElementName(action);
                if (elementName.isEmpty()) {
                    continue;
                }
                
                // Generate XPath (placeholder)
                String xpath = "//placeholder-xpath-for-" + elementName.toLowerCase();
                
                sb.append("    @FindBy(xpath = \"").append(xpath).append("\") // ").append(testCaseName).append("\n");
                sb.append("    private WebElement ").append(elementName).append(";\n\n");
            }
        }
        
        // Add constructor
        sb.append("    /**\n");
        sb.append("     * Constructor to initialize the ").append(pomClassName).append(" with WebDriver instance\n");
        sb.append("     * \n");
        sb.append("     * @param driver WebDriver instance\n");
        sb.append("     */\n");
        sb.append("    public ").append(pomClassName).append("(WebDriver driver) {\n");
        sb.append("        super(driver);\n");
        sb.append("        PageFactory.initElements(driver, this);\n");
        sb.append("    }\n\n");
        
        // Add methods
        for (Map<String, String> test : tests) {
            String testCaseName = test.getOrDefault("Automated Test Case Name", "").trim();
            String actionAndXpaths = test.getOrDefault("action and Xpaths", "").trim();
            
            if (testCaseName.isEmpty() || actionAndXpaths.isEmpty()) {
                continue;
            }
            
            // Parse action and XPaths - split by comma followed by space to avoid splitting within actions
            String[] actions = actionAndXpaths.split(", ");
            for (int i = 0; i < actions.length; i++) {
                String action = actions[i].trim();
                
                // Extract element name and action type
                String elementName = extractElementName(action);
                String actionType = extractActionType(action);
                
                if (elementName.isEmpty() || actionType.isEmpty()) {
                    continue;
                }
                
                // Generate method name
                String methodName = actionType + capitalizeFirstLetter(elementName);
                
                // Set return type based on action type
                String returnType = "void";
                if (actionType.toLowerCase().equals("verify") || actionType.toLowerCase().equals("check")) {
                    returnType = "boolean";
                }
                
                sb.append("    /**\n");
                sb.append("     * ").append(capitalizeFirstLetter(actionType)).append(" ").append(elementName).append("\n");
                sb.append("     */\n");
                sb.append("    public ").append(returnType).append(" ").append(methodName).append("() {\n");
                
                // Method implementation based on action type
                switch (actionType.toLowerCase()) {
                    case "click":
                        sb.append("        waitForElementToBeClickable(").append(elementName).append(");\n");
                        sb.append("        ").append(elementName).append(".click();\n");
                        break;
                    case "enter":
                    case "type":
                        sb.append("        waitForElementVisibility(").append(elementName).append(");\n");
                        sb.append("        ").append(elementName).append(".clear();\n");
                        sb.append("        ").append(elementName).append(".sendKeys(\"test data\");\n");
                        break;
                    case "verify":
                    case "check":
                        sb.append("        waitForElementVisibility(").append(elementName).append(");\n");
                        sb.append("        return ").append(elementName).append(".isDisplayed();\n");
                        break;
                    default:
                        sb.append("        // TODO: Implement action for ").append(actionType).append("\n");
                }
                
                sb.append("    }\n\n");
            }
        }
        
        sb.append("}\n");
        
        // Write to file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
            System.out.println("Generated POM class: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing POM class: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Generate test class
     * 
     * @param folder Folder name
     * @param className Class name
     * @param tests List of test cases
     */
    private static void generateTestClass(String folder, String className, List<Map<String, String>> tests) {
        String packageName = "test_scripts." + folder;
        String testClassName = className + "Test";
        String pomClassName = className + "Page";
        String filePath = "src/test/java/test_scripts/" + folder + "/" + testClassName + ".java";
        
        // Create directory if not exists
        new File("src/test/java/test_scripts/" + folder).mkdirs();
        
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import org.testng.Assert;\n");
        sb.append("import org.testng.annotations.Test;\n");
        sb.append("import org.testng.Reporter;\n");
        sb.append("import org.testng.annotations.BeforeMethod;\n");
        sb.append("import generic.BaseTest;\n");
        sb.append("import pom_scripts.").append(folder).append(".").append(pomClassName).append(";\n");
        sb.append("import io.qameta.allure.Description;\n");
        sb.append("import io.qameta.allure.Epic;\n");
        sb.append("import io.qameta.allure.Feature;\n");
        sb.append("import io.qameta.allure.Severity;\n");
        sb.append("import io.qameta.allure.SeverityLevel;\n");
        sb.append("import io.qameta.allure.Story;\n");
        sb.append("import property.Test_DataByPropertyFILE;\n\n");
        sb.append("/**\n");
        sb.append(" * Test class for ").append(className).append("\n");
        sb.append(" */\n");
        sb.append("public class ").append(testClassName).append(" extends BaseTest {\n\n");
        sb.append("    private ").append(pomClassName).append(" page;\n\n");
        
        // Add setUp method
        sb.append("    @BeforeMethod\n");
        sb.append("    public void setUp() throws InterruptedException {\n");
        sb.append("        login();\n");
        sb.append("        page = new ").append(pomClassName).append("(driver);\n");
        sb.append("    }\n\n");
        
        // Add test methods
        for (Map<String, String> test : tests) {
            String testCaseName = test.getOrDefault("Automated Test Case Name", "").trim();
            String testScenario = test.getOrDefault("Test Scenario", "").trim();
            String category1 = test.getOrDefault("Categories 1", "").trim();
            String category2 = test.getOrDefault("Categories 2", "").trim();
            String actionAndXpaths = test.getOrDefault("action and Xpaths", "").trim();
            
            if (testCaseName.isEmpty() || testScenario.isEmpty() || actionAndXpaths.isEmpty()) {
                continue;
            }
            
            // Generate test method name
            String methodName = TestGenerator.generateTestMethodName(testCaseName);
            
            sb.append("    @Test(description = \"").append(testScenario).append("\", testName = \"").append(testCaseName).append("\")\n");
            sb.append("    @Description(\"").append(testScenario).append("\")\n");
            sb.append("    @Epic(\"").append(folder).append("\")\n");
            sb.append("    @Feature(\"").append(className).append("\")\n");
            sb.append("    @Story(\"").append(testScenario).append("\")\n");
            sb.append("    @Severity(SeverityLevel.NORMAL)\n");
            sb.append("    public void ").append(methodName).append("() {\n");
            sb.append("        try {\n");
            sb.append("            Reporter.log(\"🔍 Starting ").append(testScenario).append(" Test...\");\n\n");
            
            // Parse action and XPaths - split by comma followed by space to avoid splitting within actions
            String[] actions = actionAndXpaths.split(", ");
            for (int i = 0; i < actions.length; i++) {
                String action = actions[i].trim();
                
                // Extract element name and action type
                String elementName = extractElementName(action);
                String actionType = extractActionType(action);
                
                if (elementName.isEmpty() || actionType.isEmpty()) {
                    continue;
                }
                
                // Generate method name
                String pomMethodName = actionType + capitalizeFirstLetter(elementName);
                
                sb.append("            // ").append(capitalizeFirstLetter(actionType)).append(" ").append(elementName).append("\n");
                
                // Handle verify/check methods differently
                if (actionType.toLowerCase().equals("verify") || actionType.toLowerCase().equals("check")) {
                    sb.append("            boolean isVerified = page.").append(pomMethodName).append("();\n");
                    sb.append("            Assert.assertTrue(isVerified, \"Failed to verify ").append(elementName).append("\");\n");
                    sb.append("            Reporter.log(\"✅ ").append(capitalizeFirstLetter(actionType)).append(" ").append(elementName).append(" - Passed\");\n\n");
                } else {
                    sb.append("            page.").append(pomMethodName).append("();\n");
                    sb.append("            Reporter.log(\"✅ ").append(capitalizeFirstLetter(actionType)).append(" ").append(elementName).append("\");\n\n");
                }
            }
            
            sb.append("            Reporter.log(\"✅ ").append(testScenario).append(" Test Completed Successfully\");\n");
            sb.append("        } catch (Exception e) {\n");
            sb.append("            Reporter.log(\"❌ ").append(testScenario).append(" Test Failed: \" + e.getMessage());\n");
            sb.append("            Assert.fail(\"Test failed: \" + e.getMessage());\n");
            sb.append("        }\n");
            sb.append("    }\n\n");
        }
        
        sb.append("}\n");
        
        // Write to file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
            System.out.println("Generated test class: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing test class: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Extract element name from action
     * 
     * @param action Action string (e.g., "Click on export button")
     * @return Element name (e.g., "exportButton")
     */
    private static String extractElementName(String action) {
        // Extract element name from action
        String[] words = action.split(" ");
        if (words.length < 2) {
            return "";
        }
        
        // Get the last word as element name
        String elementName = words[words.length - 1].toLowerCase();
        
        // Remove any non-alphanumeric characters
        elementName = elementName.replaceAll("[^a-zA-Z0-9]", "");
        
        return elementName;
    }
    
    /**
     * Extract action type from action
     * 
     * @param action Action string (e.g., "Click on export button")
     * @return Action type (e.g., "click")
     */
    private static String extractActionType(String action) {
        // Extract action type from action
        String[] words = action.split(" ");
        if (words.length < 1) {
            return "";
        }
        
        // Get the first word as action type
        String actionType = words[0].toLowerCase();
        
        // Remove any non-alphanumeric characters
        actionType = actionType.replaceAll("[^a-zA-Z0-9]", "");
        
        return actionType;
    }
    
    /**
     * Capitalize first letter of a string
     * 
     * @param str String to capitalize
     * @return Capitalized string
     */
    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
