package generic;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * TestNG Suite Listener for Automatic Email Reporting
 * Sends test reports via email after suite completion
 */
public class TestEmailListener implements ISuiteListener {
    private static final Logger logger = LoggerFactory.getLogger(TestEmailListener.class);
    
    private EmailReporter emailReporter;
    private LocalDateTime suiteStartTime;
    
    @Override
    public void onStart(ISuite suite) {
        logger.info("=== Test Suite Started: {} ===", suite.getName());
        suiteStartTime = LocalDateTime.now();
        
        try {
            // Initialize email reporter
            emailReporter = new EmailReporter();
            logger.info("Email reporter initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize email reporter: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public void onFinish(ISuite suite) {
        logger.info("=== Test Suite Finished: {} ===", suite.getName());
        
        if (emailReporter == null) {
            logger.error("Email reporter not initialized, skipping email report");
            return;
        }
        
        try {
            // Calculate test statistics
            TestStatistics stats = calculateTestStatistics(suite);
            
            // Calculate duration
            Duration duration = Duration.between(suiteStartTime, LocalDateTime.now());
            String formattedDuration = formatDuration(duration);
            
            logger.info("Test Statistics - Total: {}, Passed: {}, Failed: {}, Skipped: {}, Duration: {}", 
                stats.totalTests, stats.passedTests, stats.failedTests, stats.skippedTests, formattedDuration);
            
            // Find latest test report
            String latestReport = emailReporter.findLatestTestReport();
            if (latestReport == null) {
                logger.warn("No test report found, sending email without attachment");
            }
            
            // Create email content
            String subject = createEmailSubject(suite.getName());
            String htmlBody = emailReporter.createDefaultEmailBody(
                suite.getName(),
                stats.totalTests,
                stats.passedTests,
                stats.failedTests,
                stats.skippedTests,
                formattedDuration
            );
            
            // Send email
            emailReporter.sendTestReport(subject, htmlBody, latestReport);
            
            logger.info("Test report email sent successfully for suite: {}", suite.getName());
            
        } catch (Exception e) {
            logger.error("Failed to send test report email: {}", e.getMessage(), e);
            // Don't throw exception to avoid breaking the test execution
        }
    }
    
    private TestStatistics calculateTestStatistics(ISuite suite) {
        TestStatistics stats = new TestStatistics();
        
        for (ISuiteResult result : suite.getResults().values()) {
            ITestContext context = result.getTestContext();
            
            stats.totalTests += context.getAllTestMethods().length;
            stats.passedTests += context.getPassedTests().size();
            stats.failedTests += context.getFailedTests().size();
            stats.skippedTests += context.getSkippedTests().size();
        }
        
        return stats;
    }
    
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
    
    private String createEmailSubject(String suiteName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return String.format("[Test Report] %s - %s", suiteName, timestamp);
    }
    
    /**
     * Helper class to hold test statistics
     */
    private static class TestStatistics {
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;
        int skippedTests = 0;
    }
} 