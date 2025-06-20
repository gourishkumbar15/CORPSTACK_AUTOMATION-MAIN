package generic;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Comprehensive Email Reporter for Test Automation
 * Handles test report emailing with proper error handling and configuration
 */
public class EmailReporter {
    private static final Logger logger = LoggerFactory.getLogger(EmailReporter.class);
    
    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;
    private final List<String> recipients;
    private final boolean enableSSL;
    private final boolean enableTLS;
    
    public EmailReporter() {
        Properties config = loadConfiguration();
        this.smtpHost = config.getProperty("email.smtp.host", "smtp.gmail.com");
        this.smtpPort = Integer.parseInt(config.getProperty("email.smtp.port", "587"));
        this.username = config.getProperty("email.username");
        this.password = config.getProperty("email.password");
        this.enableSSL = Boolean.parseBoolean(config.getProperty("email.smtp.ssl", "false"));
        this.enableTLS = Boolean.parseBoolean(config.getProperty("email.smtp.tls", "true"));
        
        String recipientList = config.getProperty("email.recipients");
        if (recipientList == null || recipientList.trim().isEmpty()) {
            throw new RuntimeException("Email recipients not configured");
        }
        this.recipients = Arrays.asList(recipientList.split(",\\s*"));
        
        validateConfiguration();
    }
    
    private Properties loadConfiguration() {
        Properties props = new Properties();
        try {
            File configFile = new File("property/test_data.properties");
            if (!configFile.exists()) {
                throw new RuntimeException("Configuration file not found: " + configFile.getAbsolutePath());
            }
            
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
            }
            
            // Map old property names to new ones for backward compatibility
            if (props.getProperty("email.username") == null && props.getProperty("gmail.username") != null) {
                props.setProperty("email.username", props.getProperty("gmail.username"));
            }
            if (props.getProperty("email.password") == null && props.getProperty("gmail.password") != null) {
                props.setProperty("email.password", props.getProperty("gmail.password"));
            }
            if (props.getProperty("email.recipients") == null && props.getProperty("team.email") != null) {
                props.setProperty("email.recipients", props.getProperty("team.email"));
            }
            
            logger.info("Email configuration loaded successfully");
            logger.info("SMTP Host: {}", props.getProperty("email.smtp.host", "smtp.gmail.com"));
            logger.info("SMTP Port: {}", props.getProperty("email.smtp.port", "587"));
            logger.info("Username: {}", props.getProperty("email.username"));
            logger.info("Recipients count: {}", props.getProperty("email.recipients", "").split(",\\s*").length);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email configuration", e);
        }
        return props;
    }
    
    private void validateConfiguration() {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Email username not configured");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Email password not configured");
        }
        if (recipients.isEmpty()) {
            throw new RuntimeException("No email recipients configured");
        }
        
        logger.info("Email configuration validation passed");
    }
    
    /**
     * Send test report email with attachment
     */
    public void sendTestReport(String subject, String htmlBody, String attachmentPath) {
        logger.info("Starting to send test report email...");
        logger.info("Subject: {}", subject);
        logger.info("Recipients: {}", recipients);
        logger.info("Attachment: {}", attachmentPath);
        
        try {
            // Create session
            Session session = createMailSession();
            
            // Create message
            Message message = createMessage(session, subject, htmlBody, attachmentPath);
            
            // Send message
            sendMessage(session, message);
            
            logger.info("Test report email sent successfully!");
            
        } catch (Exception e) {
            logger.error("Failed to send test report email: {}", e.getMessage(), e);
            throw new RuntimeException("Email sending failed", e);
        }
    }
    
    private Session createMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", enableTLS);
        props.put("mail.smtp.ssl.enable", enableSSL);
        props.put("mail.smtp.ssl.trust", smtpHost);
        props.put("mail.debug", "false"); // Set to true for debugging
        
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    private Message createMessage(Session session, String subject, String htmlBody, String attachmentPath) 
            throws MessagingException {
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setSubject(subject);
        
        // Add recipients
        for (String recipient : recipients) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.trim()));
        }
        
        // Create multipart message
        Multipart multipart = new MimeMultipart();
        
        // Add HTML body
        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html; charset=utf-8");
        multipart.addBodyPart(htmlPart);
        
        // Add attachment if provided
        if (attachmentPath != null && !attachmentPath.trim().isEmpty()) {
            File attachmentFile = new File(attachmentPath);
            if (attachmentFile.exists() && attachmentFile.canRead()) {
                BodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentFile);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(attachmentFile.getName());
                multipart.addBodyPart(attachmentPart);
                logger.info("Attachment added: {} ({} bytes)", attachmentFile.getName(), attachmentFile.length());
            } else {
                logger.warn("Attachment file not found or not readable: {}", attachmentPath);
            }
        }
        
        message.setContent(multipart);
        return message;
    }
    
    private void sendMessage(Session session, Message message) throws MessagingException {
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            logger.info("Connecting to SMTP server {}:{}", smtpHost, smtpPort);
            transport.connect(smtpHost, username, password);
            logger.info("Connected to SMTP server successfully");
            
            logger.info("Sending email...");
            transport.sendMessage(message, message.getAllRecipients());
            logger.info("Email sent successfully");
            
        } finally {
            if (transport != null && transport.isConnected()) {
                transport.close();
                logger.info("SMTP connection closed");
            }
        }
    }
    
    /**
     * Find the latest test report file
     */
    public String findLatestTestReport() {
        String reportsDir = "test-output/reports";
        File reportsFolder = new File(reportsDir);
        
        if (!reportsFolder.exists() || !reportsFolder.isDirectory()) {
            logger.warn("Reports directory not found: {}", reportsDir);
            return null;
        }
        
        File[] reportFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".html"));
        
        if (reportFiles == null || reportFiles.length == 0) {
            logger.warn("No HTML report files found in {}", reportsDir);
            return null;
        }
        
        // Sort by last modified time (newest first)
        Arrays.sort(reportFiles, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
        
        String latestReport = reportFiles[0].getAbsolutePath();
        logger.info("Latest test report found: {}", latestReport);
        return latestReport;
    }
    
    /**
     * Create a default HTML email body
     */
    public String createDefaultEmailBody(String suiteName, int totalTests, int passedTests, 
                                       int failedTests, int skippedTests, String duration) {
        
        double passRate = totalTests > 0 ? (passedTests * 100.0 / totalTests) : 0;
        double failRate = totalTests > 0 ? (failedTests * 100.0 / totalTests) : 0;
        double skipRate = totalTests > 0 ? (skippedTests * 100.0 / totalTests) : 0;
        
        return """
            <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 20px; }
                        .container { max-width: 800px; margin: 0 auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                        .header { background: #2c3e50; color: white; padding: 20px; border-radius: 8px 8px 0 0; }
                        .content { padding: 20px; }
                        .summary-box { background: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #007bff; }
                        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin: 20px 0; }
                        .stat-card { background: white; padding: 15px; border-radius: 5px; text-align: center; border: 1px solid #dee2e6; }
                        .stat-number { font-size: 24px; font-weight: bold; margin-bottom: 5px; }
                        .passed { color: #28a745; }
                        .failed { color: #dc3545; }
                        .skipped { color: #ffc107; }
                        .total { color: #007bff; }
                        .footer { background: #f8f9fa; padding: 15px; border-radius: 0 0 8px 8px; font-size: 0.9em; color: #6c757d; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🚀 Test Execution Report</h1>
                            <p>Suite: %s | Generated: %s</p>
                        </div>
                        
                        <div class="content">
                            <div class="summary-box">
                                <h3>📊 Test Execution Summary</h3>
                                <p><strong>Duration:</strong> %s</p>
                                <p><strong>Total Tests:</strong> %d</p>
                            </div>
                            
                            <div class="stats-grid">
                                <div class="stat-card">
                                    <div class="stat-number passed">%d</div>
                                    <div>Passed</div>
                                    <small>%.1f%%</small>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-number failed">%d</div>
                                    <div>Failed</div>
                                    <small>%.1f%%</small>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-number skipped">%d</div>
                                    <div>Skipped</div>
                                    <small>%.1f%%</small>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-number total">%d</div>
                                    <div>Total</div>
                                    <small>100%%</small>
                                </div>
                            </div>
                            
                            <div class="summary-box">
                                <h3>📋 Report Details</h3>
                                <p>A detailed HTML report is attached to this email.</p>
                                <p><strong>Note:</strong> This is an automated email. Please do not reply directly to this message.</p>
                            </div>
                        </div>
                        
                        <div class="footer">
                            <p>Best regards,<br>QA Automation Team</p>
                            <p><small>Report generated by Corpstack Automation Framework</small></p>
                        </div>
                    </div>
                </body>
            </html>
            """.formatted(
                suiteName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                duration,
                totalTests,
                passedTests, passRate,
                failedTests, failRate,
                skippedTests, skipRate,
                totalTests
            );
    }
} 