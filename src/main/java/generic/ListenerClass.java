package generic;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ListenerClass implements ITestListener
{
	private static ExtentReports extent = ExtentReportManager.getInstance();
	
	/**
	 * Check if a test will be retried
	 * 
	 * @param result The test result
	 * @return true if the test will be retried, false otherwise
	 */
	private boolean isTestRetried(ITestResult result) {
		// Check if the test has a retry analyzer
		if (result.getMethod().getRetryAnalyzer(result) != null) {
			RetryAnalyzer retryAnalyzer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
			// Check if the test will be retried
			return retryAnalyzer.retry(result);
		}
		return false;
	}

	@Override
	public void onTestStart(ITestResult result)
	{
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();
		if (description == null || description.isEmpty()) {
			description = "Test execution for " + methodName;
		}
		
		ExtentTest extentTest = ExtentReportManager.getTest(className, methodName, description);
		extentTest.log(Status.INFO, "Test Started: " + methodName);
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
		ExtentTest test = ExtentReportManager.getCurrentTest();
		if (test == null) {
			String className = result.getTestClass().getName();
			String methodName = result.getMethod().getMethodName();
			String description = result.getMethod().getDescription();
			if (description == null || description.isEmpty()) {
				description = "Test execution for " + methodName;
			}
			test = ExtentReportManager.getTest(className, methodName, description);
		}
		
		// Check if the test passed after retries
		Object retryCountObj = result.getAttribute("RETRY_COUNT");
		if (retryCountObj != null) {
			int retryCount = (Integer) retryCountObj;
			test.log(Status.PASS, MarkupHelper.createLabel("Test Passed after " + retryCount + " retry attempts: " + result.getMethod().getMethodName(), ExtentColor.GREEN));
			System.out.println("✅ Test passed after " + retryCount + " retry attempts: " + result.getMethod().getMethodName());
		} else {
			test.log(Status.PASS, MarkupHelper.createLabel("Test Passed: " + result.getMethod().getMethodName(), ExtentColor.GREEN));
		}
	}

	@Override
	public void onTestFailure(ITestResult result) 
	{
		ExtentTest test = ExtentReportManager.getCurrentTest();
		if (test == null) {
			String className = result.getTestClass().getName();
			String methodName = result.getMethod().getMethodName();
			String description = result.getMethod().getDescription();
			if (description == null || description.isEmpty()) {
				description = "Test execution for " + methodName;
			}
			test = ExtentReportManager.getTest(className, methodName, description);
		}
		
		// Check if the test will be retried
		boolean willBeRetried = isTestRetried(result);
		
		if (willBeRetried) {
			// If the test will be retried, log it as a warning instead of a failure
			test.log(Status.WARNING, MarkupHelper.createLabel("Test Failed but will be retried: " + result.getMethod().getMethodName(), ExtentColor.AMBER));
			test.log(Status.WARNING, "Error: " + result.getThrowable());
			System.out.println("⚠️ Test failed but will be retried: " + result.getMethod().getMethodName());
		} else {
			// If the test will not be retried, log it as a failure
			test.log(Status.FAIL, MarkupHelper.createLabel("Test Failed: " + result.getMethod().getMethodName(), ExtentColor.RED));
			test.log(Status.FAIL, "Error: " + result.getThrowable());
		}
		
		// Take screenshot for both ExtentReports and Allure
		if (result.getTestContext().getAttribute("driver") != null) {
			WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
			try {
				// For ExtentReports
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + result.getMethod().getMethodName() + ".png";
				FileUtils.copyFile(screenshot, new File(screenshotPath));
				test.addScreenCaptureFromPath(screenshotPath);
				
				// For Allure
				byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				Allure.getLifecycle().addAttachment("Test Failure Screenshot", "image/png", "png", screenshotBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) 
	{
		ExtentTest test = ExtentReportManager.getCurrentTest();
		if (test == null) {
			String className = result.getTestClass().getName();
			String methodName = result.getMethod().getMethodName();
			String description = result.getMethod().getDescription();
			if (description == null || description.isEmpty()) {
				description = "Test execution for " + methodName;
			}
			test = ExtentReportManager.getTest(className, methodName, description);
		}
		
		test.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped: " + result.getMethod().getMethodName(), ExtentColor.YELLOW));
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		System.out.println("⚠️ TEST PARTIALLY PASSED: " + result.getName());
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) 
	{
		System.out.println("⏰ TEST FAILED DUE TO TIMEOUT: " + result.getName());
		System.out.println("📝 Timeout Reason: " + result.getThrowable().getMessage());
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.flush();
		
		// Generate Allure report
		try {
			// Create a ProcessBuilder for better control over the process
			ProcessBuilder processBuilder = new ProcessBuilder();
			
			// Set the working directory to the project root
			processBuilder.directory(new File(System.getProperty("user.dir")));
			
			// Set up the command based on the OS
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				processBuilder.command("cmd.exe", "/c", "mvn", "allure:report");
			} else {
				processBuilder.command("sh", "-c", "mvn allure:report");
			}
			
			// Redirect error stream to output stream
			processBuilder.redirectErrorStream(true);
			
			// Start the process
			Process process = processBuilder.start();
			
			// Read the output
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}
			
			// Wait for the process to complete
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Allure report generated successfully");
			} else {
				System.err.println("Failed to generate Allure report. Exit code: " + exitCode);
			}
		} catch (Exception e) {
			System.err.println("Error generating Allure report: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
