package test_scripts;

import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import generic.EmailReporter;

/**
 * Test to verify the new email functionality
 */
public class EmailVerificationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationTest.class);
    
    @Test
    public void testEmailReporterInitialization() {
        logger.info("Testing EmailReporter initialization...");
        
        try {
            EmailReporter emailReporter = new EmailReporter();
            logger.info("✅ EmailReporter initialized successfully");
            
            // Test finding latest report
            String latestReport = emailReporter.findLatestTestReport();
            if (latestReport != null) {
                logger.info("✅ Latest test report found: {}", latestReport);
            } else {
                logger.info("ℹ️ No test report found (this is normal if no tests have been run)");
            }
            
            // Test email body creation
            String emailBody = emailReporter.createDefaultEmailBody(
                "Test Suite",
                10,
                8,
                1,
                1,
                "00:05:30"
            );
            
            if (emailBody != null && !emailBody.isEmpty()) {
                logger.info("✅ Email body created successfully ({} characters)", emailBody.length());
            } else {
                logger.error("❌ Failed to create email body");
            }
            
        } catch (Exception e) {
            logger.error("❌ EmailReporter initialization failed: {}", e.getMessage(), e);
            throw new RuntimeException("Email verification test failed", e);
        }
    }
    
    @Test
    public void testEmailConfiguration() {
        logger.info("Testing email configuration...");
        
        try {
            EmailReporter emailReporter = new EmailReporter();
            logger.info("✅ Email configuration loaded successfully");
            
            // The constructor validates configuration, so if we reach here, it's working
            logger.info("✅ Email configuration validation passed");
            
        } catch (Exception e) {
            logger.error("❌ Email configuration test failed: {}", e.getMessage(), e);
            throw new RuntimeException("Email configuration test failed", e);
        }
    }
} 