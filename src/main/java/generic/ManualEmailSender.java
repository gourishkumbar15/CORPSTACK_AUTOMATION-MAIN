package generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manual Email Sender for Test Reports
 * Can be used to send test reports manually via command line or programmatically
 */
public class ManualEmailSender {
    private static final Logger logger = LoggerFactory.getLogger(ManualEmailSender.class);
    
    public static void main(String[] args) {
        logger.info("=== Manual Email Sender Started ===");
        
        try {
            // Initialize email reporter
            EmailReporter emailReporter = new EmailReporter();
            logger.info("Email reporter initialized successfully");
            
            // Find latest test report
            String latestReport = emailReporter.findLatestTestReport();
            if (latestReport == null) {
                logger.error("No test report found in test-output/reports directory");
                logger.info("Please run tests first to generate a report");
                return;
            }
            
            // Create email content
            String subject = "[Manual Test Report] " + 
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            String htmlBody = emailReporter.createDefaultEmailBody(
                "Manual Test Report",
                0, // We don't have test statistics for manual send
                0,
                0,
                0,
                "N/A"
            );
            
            // Send email
            emailReporter.sendTestReport(subject, htmlBody, latestReport);
            
            logger.info("=== Manual email sent successfully! ===");
            logger.info("Report file: {}", latestReport);
            
        } catch (Exception e) {
            logger.error("Failed to send manual email: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
    
    /**
     * Send test report with custom statistics
     */
    public static void sendTestReportWithStats(String suiteName, int totalTests, int passedTests, 
                                             int failedTests, int skippedTests, String duration) {
        logger.info("=== Sending Test Report with Statistics ===");
        
        try {
            EmailReporter emailReporter = new EmailReporter();
            
            // Find latest test report
            String latestReport = emailReporter.findLatestTestReport();
            
            // Create email content
            String subject = "[Test Report] " + suiteName + " - " + 
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            String htmlBody = emailReporter.createDefaultEmailBody(
                suiteName,
                totalTests,
                passedTests,
                failedTests,
                skippedTests,
                duration
            );
            
            // Send email
            emailReporter.sendTestReport(subject, htmlBody, latestReport);
            
            logger.info("=== Test report with statistics sent successfully! ===");
            
        } catch (Exception e) {
            logger.error("Failed to send test report with statistics: {}", e.getMessage(), e);
            throw new RuntimeException("Email sending failed", e);
        }
    }
} 