package generic;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {
    public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
        // Create screenshots directory if it doesn't exist
        String screenshotDir = "screenshots";
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Generate timestamp for unique filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = screenshotDir + "/" + testName + "_" + timestamp + ".png";

        // Take screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File(fileName);
        FileUtils.copyFile(source, destination);

        return fileName;
    }
} 