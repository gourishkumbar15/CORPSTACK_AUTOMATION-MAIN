// NOTE: Requires JavaMail API dependency. Add the following to your pom.xml:
// <dependency>
//   <groupId>com.sun.mail</groupId>
//   <artifactId>jakarta.mail</artifactId>
//   <version>2.0.1</version>
// </dependency>

package generic;

import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.FlagTerm;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Transport;
import jakarta.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for connecting to Gmail and fetching emails (e.g., OTPs)
 */
public class GmailUtility {
    private static final Logger logger = LoggerFactory.getLogger(GmailUtility.class);
    private String username;
    private String password;
    private Store store;
    private Folder inbox;
    private Session session;

    public GmailUtility() {
        loadCredentials();
    }

    private void loadCredentials() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("property/test_data.properties"));
            this.username = props.getProperty("gmail.username");
            this.password = props.getProperty("gmail.password");
            logger.info("Loaded Gmail credentials for user: {}", username);
        } catch (IOException e) {
            logger.error("Failed to load Gmail credentials from test_data.properties", e);
            throw new RuntimeException("Failed to load Gmail credentials from test_data.properties", e);
        }
    }

    /**
     * Connects to Gmail inbox using IMAP
     */
    public void connect() throws Exception {
        logger.info("Connecting to Gmail IMAP server...");
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        session = Session.getInstance(props);
        store = session.getStore();
        store.connect("imap.gmail.com", username, password);
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        logger.info("Successfully connected to Gmail IMAP server");
    }

    /**
     * Sends an email with test report to team members
     * @param recipients List of email addresses to send the report to
     * @param subject Subject of the email
     * @param body Email body content (HTML supported)
     * @param attachments List of file paths to attach (optional)
     */
    public void sendTestReport(List<String> recipients, String subject, String body, List<String> attachments) throws MessagingException {
        logger.info("Preparing to send test report email...");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session emailSession = Session.getInstance(props);
        Message message = new MimeMessage(emailSession);
        
        message.setFrom(new InternetAddress(username));
        logger.info("Setting sender email: {}", username);
        
        // Add all recipients
        for (String recipient : recipients) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            logger.info("Added recipient: {}", recipient);
        }
        
        message.setSubject(subject);
        logger.info("Set email subject: {}", subject);
        
        // Create multipart message for HTML content and attachments
        jakarta.mail.Multipart multipart = new jakarta.mail.internet.MimeMultipart();
        
        // Add HTML body
        jakarta.mail.BodyPart messageBodyPart = new jakarta.mail.internet.MimeBodyPart();
        messageBodyPart.setContent(body, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);
        logger.info("Added HTML body to email");
        
        // Add attachments if any
        if (attachments != null && !attachments.isEmpty()) {
            for (String filePath : attachments) {
                jakarta.mail.BodyPart attachmentPart = new jakarta.mail.internet.MimeBodyPart();
                try {
                    jakarta.activation.DataSource source = new jakarta.activation.FileDataSource(filePath);
                    attachmentPart.setDataHandler(new jakarta.activation.DataHandler(source));
                    attachmentPart.setFileName(new java.io.File(filePath).getName());
                    multipart.addBodyPart(attachmentPart);
                    logger.info("Added attachment: {}", filePath);
                } catch (Exception e) {
                    logger.error("Failed to attach file: {}", filePath, e);
                }
            }
        }
        
        message.setContent(multipart);
        
        logger.info("Connecting to SMTP server...");
        Transport transport = emailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", username, password);
        logger.info("Connected to SMTP server successfully");
        
        logger.info("Sending email...");
        transport.sendMessage(message, message.getAllRecipients());
        logger.info("Email sent successfully");
        
        transport.close();
        logger.info("SMTP connection closed");
    }

    /**
     * Disconnects from Gmail
     */
    public void disconnect() throws Exception {
        logger.info("Disconnecting from Gmail...");
        if (inbox != null && inbox.isOpen()) {
            inbox.close(false);
            logger.info("Closed inbox");
        }
        if (store != null && store.isConnected()) {
            store.close();
            logger.info("Closed store connection");
        }
    }

    /**
     * Fetches the latest unread email matching the subject keyword
     */
    public Message getLatestUnreadEmail(String subjectKeyword) throws Exception {
        logger.info("Searching for unread email with subject containing: {}", subjectKeyword);
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        for (int i = messages.length - 1; i >= 0; i--) {
            String subject = messages[i].getSubject();
            if (subject != null && subject.contains(subjectKeyword)) {
                logger.info("Found matching unread email with subject: {}", subject);
                return messages[i];
            }
        }
        logger.info("No matching unread email found");
        return null;
    }

    /**
     * Extracts OTP from the email body using a regex pattern
     */
    public String extractOtpFromEmail(Message message, String otpRegex) throws Exception {
        if (message == null) {
            logger.info("No message provided for OTP extraction");
            return null;
        }
        String content = message.getContent().toString();
        Pattern pattern = Pattern.compile(otpRegex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String otp = matcher.group(0);
            logger.info("Extracted OTP: {}", otp);
            return otp;
        }
        logger.info("No OTP found in message content");
        return null;
    }
} 