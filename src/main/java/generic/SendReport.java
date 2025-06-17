package generic;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendReport {
    private static final Logger logger = LoggerFactory.getLogger(SendReport.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting to send test report email...");
            
            // Load properties
            logger.info("Loading properties from test_data.properties...");
            Properties props = new Properties();
            props.load(new FileInputStream("property/test_data.properties"));
            
            // Verify Gmail credentials
            String gmailUsername = props.getProperty("gmail.username");
            String gmailPassword = props.getProperty("gmail.password");
            logger.info("Gmail username: {}", gmailUsername);
            logger.info("Gmail password length: {}", gmailPassword != null ? gmailPassword.length() : 0);
            
            // Get team emails
            String teamEmails = props.getProperty("team.email");
            List<String> recipients = Arrays.asList(teamEmails.split(",\\s*"));
            logger.info("Email recipients: {}", recipients);
            
            // Initialize Gmail utility
            logger.info("Initializing Gmail utility...");
            GmailUtility gmail = new GmailUtility();
            
            // Find the most recent test report
            logger.info("Looking for test reports in test-output/reports...");
            String reportsDir = "test-output/reports";
            File reportsFolder = new File(reportsDir);
            if (!reportsFolder.exists()) {
                logger.error("Reports directory does not exist: {}", reportsDir);
                throw new RuntimeException("Reports directory does not exist: " + reportsDir);
            }
            
            File[] reportFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".html"));
            if (reportFiles == null || reportFiles.length == 0) {
                logger.error("No test report files found in {}", reportsDir);
                throw new RuntimeException("No test report files found");
            }
            
            // Sort files by last modified time (newest first)
            Arrays.sort(reportFiles, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
            String latestReport = reportFiles[0].getAbsolutePath();
            logger.info("Using latest test report: {}", latestReport);
            
            // Verify report file exists and is readable
            File reportFile = new File(latestReport);
            if (!reportFile.exists()) {
                logger.error("Report file does not exist: {}", latestReport);
                throw new RuntimeException("Report file does not exist: " + latestReport);
            }
            if (!reportFile.canRead()) {
                logger.error("Cannot read report file: {}", latestReport);
                throw new RuntimeException("Cannot read report file: " + latestReport);
            }
            logger.info("Report file size: {} bytes", reportFile.length());

            String htmlBody = """
                <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <div style="max-width: 800px; margin: 0 auto; padding: 20px;">
                            <h2 style="color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px;">Test Execution Report</h2>
                            
                            <div style="background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                                <h3 style="color: #2c3e50; margin-top: 0;">Test Report Summary</h3>
                                <p><strong>Generated Time:</strong> %s</p>
                                <p><strong>Report File:</strong> %s</p>
                            </div>

                            <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                <p style="margin: 0;"><strong>Note:</strong> This is an automated email. Please do not reply directly to this message.</p>
                                <p style="margin: 10px 0 0 0;">Please find the detailed test report attached to this email.</p>
                            </div>

                            <p style="color: #6c757d; font-size: 0.9em;">Best regards,<br>Automated Test System</p>
                        </div>
                    </body>
                </html>
                """.formatted(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    reportFile.getName()
                );

            // Attach the latest test report
            List<String> attachments = Arrays.asList(latestReport);
            logger.info("Attaching report file: {}", attachments);

            String subject = "[Test Report] Manual Send - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            logger.info("Sending email with subject: {}", subject);
            
            try {
                logger.info("Attempting to send email...");
                gmail.sendTestReport(
                    recipients,
                    subject,
                    htmlBody,
                    attachments
                );
                logger.info("Test report email sent successfully to team.");
            } catch (Exception e) {
                logger.error("Failed to send email: {}", e.getMessage(), e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("Failed to send test report email: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }
} 