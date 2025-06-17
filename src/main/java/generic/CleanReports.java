package generic;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanReports {
    private static final Logger logger = LoggerFactory.getLogger(CleanReports.class);

    public static void main(String[] args) {
        try {
            // Clean test-output/reports directory
            String reportsDir = "test-output/reports";
            cleanDirectory(reportsDir);
            
            // Clean test-output/screenshots directory
            String screenshotsDir = "test-output/screenshots";
            cleanDirectory(screenshotsDir);
            
            // Clean allure-results directory
            String allureResultsDir = "target/allure-results";
            cleanDirectory(allureResultsDir);
            
            // Clean allure-report directory
            String allureReportDir = "target/allure-report";
            cleanDirectory(allureReportDir);
            
            logger.info("Successfully cleaned all test reports and related directories");
        } catch (Exception e) {
            logger.error("Failed to clean reports: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }
    
    private static void cleanDirectory(String dirPath) {
        File directory = new File(dirPath);
        if (directory.exists()) {
            logger.info("Cleaning directory: {}", dirPath);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.delete()) {
                        logger.info("Deleted file: {}", file.getName());
                    } else {
                        logger.warn("Failed to delete file: {}", file.getName());
                    }
                }
            }
        } else {
            logger.info("Directory does not exist: {}", dirPath);
        }
    }
} 