package generic;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading data from Excel files
 */
public class ExcelUtility {
    
    /**
     * Read all data from an Excel sheet and return as a list of maps
     * Each map represents a row with column headers as keys
     * 
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to read
     * @return List of maps containing row data
     * @throws IOException If file cannot be read
     */
    public static List<Map<String, String>> readExcelSheet(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in the Excel file");
            }
            
            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Header row not found in sheet '" + sheetName + "'");
            }
            
            // Extract column headers
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                headers.add(cell == null ? "" : cell.toString().trim());
            }
            
            // Extract data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                boolean hasData = false;
                
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = "";
                    
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    value = cell.getDateCellValue().toString();
                                } else {
                                    value = String.valueOf(cell.getNumericCellValue());
                                    // Remove .0 from integer values
                                    if (value.endsWith(".0")) {
                                        value = value.substring(0, value.length() - 2);
                                    }
                                }
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                try {
                                    value = String.valueOf(cell.getNumericCellValue());
                                } catch (Exception e) {
                                    try {
                                        value = cell.getStringCellValue();
                                    } catch (Exception ex) {
                                        value = cell.getCellFormula();
                                    }
                                }
                                break;
                            default:
                                value = "";
                        }
                    }
                    
                    if (!value.isEmpty()) {
                        hasData = true;
                    }
                    
                    rowData.put(headers.get(j), value);
                }
                
                // Only add rows that have data
                if (hasData) {
                    data.add(rowData);
                }
            }
        }
        
        return data;
    }
    
    /**
     * Read all data from the first sheet of an Excel file
     * 
     * @param filePath Path to the Excel file
     * @return List of maps containing row data
     * @throws IOException If file cannot be read
     */
    public static List<Map<String, String>> readExcelSheet(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            String sheetName = workbook.getSheetName(0);
            return readExcelSheet(filePath, sheetName);
        }
    }
    
    /**
     * Get all sheet names from an Excel file
     * 
     * @param filePath Path to the Excel file
     * @return List of sheet names
     * @throws IOException If file cannot be read
     */
    public static List<String> getSheetNames(String filePath) throws IOException {
        List<String> sheetNames = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        }
        
        return sheetNames;
    }
}
