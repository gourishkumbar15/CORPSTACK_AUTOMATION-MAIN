package generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for generating test cases from CSV data
 */
public class TestGenerator {
    
    private static final String EXCEL_FILE_PATH = "/Users/gourish/Documents/Corpstack_Automation-main/test cases/Automation Test Cases.xlsx";
    
    /**
     * Get test data from Excel file
     * 
     * @return List of maps containing test data
     */
    public static List<Map<String, String>> getTestData() {
        try {
            return ExcelUtility.readExcelSheet(EXCEL_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Get test data from specific sheet in Excel file
     * 
     * @param sheetName Name of the sheet to read
     * @return List of maps containing test data
     */
    public static List<Map<String, String>> getTestData(String sheetName) {
        try {
            return ExcelUtility.readExcelSheet(EXCEL_FILE_PATH, sheetName);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Get all sheet names from Excel file
     * 
     * @return List of sheet names
     */
    public static List<String> getSheetNames() {
        try {
            return ExcelUtility.getSheetNames(EXCEL_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Generate test method name from test case name
     * 
     * @param testCaseName Test case name (e.g., TC001_Emp_Expense_Download_Local)
     * @return Test method name (e.g., testEmpExpenseDownloadLocal)
     */
    public static String generateTestMethodName(String testCaseName) {
        if (testCaseName == null || testCaseName.isEmpty()) {
            return "testMethod";
        }
        
        // Remove TC number prefix (e.g., TC001_)
        String name = testCaseName.replaceAll("^TC\\d+_", "");
        
        // Split by underscore and capitalize each word except the first
        String[] parts = name.split("_");
        StringBuilder methodName = new StringBuilder("test");
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                methodName.append(part.substring(0, 1).toUpperCase());
                methodName.append(part.substring(1).toLowerCase());
            }
        }
        
        return methodName.toString();
    }
    
    /**
     * Generate package name from folder name
     * 
     * @param folderName Folder name (e.g., Employee)
     * @return Package name (e.g., Employee)
     */
    public static String generatePackageName(String folderName) {
        if (folderName == null || folderName.isEmpty()) {
            return "";
        }
        
        return folderName.trim();
    }
    
    /**
     * Generate class name from class name in Excel
     * 
     * @param className Class name (e.g., Expense)
     * @return Class name (e.g., Expense)
     */
    public static String generateClassName(String className) {
        if (className == null || className.isEmpty()) {
            return "Test";
        }
        
        return className.trim();
    }
}
