package generic;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class ReportEmailSuiteListener implements ISuiteListener {
    private static final Logger logger = LoggerFactory.getLogger(ReportEmailSuiteListener.class);

    @Override
    public void onFinish(ISuite suite) {
        try {
            logger.info("Starting to send test report email...");
            GmailUtility gmail = new GmailUtility();
            Properties props = new Properties();
            props.load(new FileInputStream("property/test_data.properties"));
            String teamEmails = props.getProperty("team.email");
            List<String> recipients = Arrays.asList(teamEmails.split(",\\s*"));
            
            logger.info("Email recipients: {}", recipients);

            // Calculate test statistics
            int totalTests = 0;
            int passedTests = 0;
            int failedTests = 0;
            int skippedTests = 0;
            long totalDuration = 0;

            for (ISuiteResult result : suite.getResults().values()) {
                ITestContext context = result.getTestContext();
                totalTests += context.getAllTestMethods().length;
                passedTests += context.getPassedTests().size();
                failedTests += context.getFailedTests().size();
                skippedTests += context.getSkippedTests().size();
                totalDuration += context.getEndDate().getTime() - context.getStartDate().getTime();
            }

            logger.info("Test statistics - Total: {}, Passed: {}, Failed: {}, Skipped: {}", 
                totalTests, passedTests, failedTests, skippedTests);

            // Format duration
            Duration duration = Duration.ofMillis(totalDuration);
            String formattedDuration = String.format("%02d:%02d:%02d", 
                duration.toHours(), 
                duration.toMinutesPart(), 
                duration.toSecondsPart());

            String htmlBody = """
                <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <div style="max-width: 800px; margin: 0 auto; padding: 20px;">
                            <h2 style="color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px;">Test Execution Report</h2>
                            
                            <div style="background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                                <h3 style="color: #2c3e50; margin-top: 0;">Test Suite Summary</h3>
                                <p><strong>Suite Name:</strong> %s</p>
                                <p><strong>Execution Time:</strong> %s</p>
                                <p><strong>Duration:</strong> %s</p>
                            </div>

                            <div style="display: flex; justify-content: space-between; margin: 20px 0;">
                                <div style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 5px; flex: 1; margin-right: 10px; text-align: center;">
                                    <h3 style="margin: 0;">Passed</h3>
                                    <p style="font-size: 24px; margin: 10px 0;">%d</p>
                                </div>
                                <div style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; flex: 1; margin-right: 10px; text-align: center;">
                                    <h3 style="margin: 0;">Failed</h3>
                                    <p style="font-size: 24px; margin: 10px 0;">%d</p>
                                </div>
                                <div style="background-color: #fff3cd; color: #856404; padding: 15px; border-radius: 5px; flex: 1; text-align: center;">
                                    <h3 style="margin: 0;">Skipped</h3>
                                    <p style="font-size: 24px; margin: 10px 0;">%d</p>
                                </div>
                            </div>

                            <div style="background-color: #e9ecef; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                <h3 style="color: #2c3e50; margin-top: 0;">Test Statistics</h3>
                                <p><strong>Total Tests:</strong> %d</p>
                                <p><strong>Pass Rate:</strong> %.1f%%</p>
                                <p><strong>Fail Rate:</strong> %.1f%%</p>
                                <p><strong>Skip Rate:</strong> %.1f%%</p>
                            </div>

                            <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                <p style="margin: 0;"><strong>Note:</strong> This is an automated email. Please do not reply directly to this message.</p>
                                <p style="margin: 10px 0 0 0;">Please find the detailed test report attached to this email.</p>
                            </div>

                            <p style="color: #6c757d; font-size: 0.9em;">Best regards,<br> Team QA</p>
                        </div>
                    </body>
                </html>
                """.formatted(
                    suite.getName(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    formattedDuration,
                    passedTests,
                    failedTests,
                    skippedTests,
                    totalTests,
                    (passedTests * 100.0 / totalTests),
                    (failedTests * 100.0 / totalTests),
                    (skippedTests * 100.0 / totalTests)
                );

            // Find the most recent test report
            String reportsDir = "test-output/reports";
            File reportsFolder = new File(reportsDir);
            File[] reportFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".html"));
            
            if (reportFiles == null || reportFiles.length == 0) {
                logger.error("No test report files found in {}", reportsDir);
                throw new RuntimeException("No test report files found");
            }
            
            // Sort files by last modified time (newest first)
            Arrays.sort(reportFiles, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
            String latestReport = reportFiles[0].getAbsolutePath();
            
            logger.info("Using latest test report: {}", latestReport);
            
            // Attach the latest test report
            List<String> attachments = Arrays.asList(latestReport);
            logger.info("Attaching report file: {}", attachments);

            String subject = "[Test Report] " + suite.getName() + " - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            logger.info("Sending email with subject: {}", subject);
            
            gmail.sendTestReport(
                recipients,
                subject,
                htmlBody,
                attachments
            );
            logger.info("Test report email sent successfully to team.");
        } catch (Exception e) {
            logger.error("Failed to send test report email: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }
} 