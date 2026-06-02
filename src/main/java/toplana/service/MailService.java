package toplana.service;

import toplana.domain.User;

import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import toplana.web.rest.dto.MailWithAttachment;
import java.io.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;

import com.microsoft.aad.msal4j.*;
import org.springframework.web.bind.annotation.RequestMethod;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {
	
	@Value("${graph.tenant-id}")
	private String graphTenantId;

	@Value("${graph.client-id}")
	private String graphClientId;
   
	@Value("${graph.client-secret}")
	private String graphClientSecret;

	@Value("${graph.sender}")
	private String graphSender;

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
    /*
    @Async
    public void sendMultipleEmails(List<MailWithAttachment> emails) {
        for (MailWithAttachment mail : emails) {
            try {
                sendMailWithAttachment(mail);
            } catch (MessagingException e) {
                // Handle exception per email (e.g., log or retry)
                System.err.println("Failed to send to " + mail.getTo() + ": " + e.getMessage());
            }
        }
    }
    */
    @Async
    public void sendMultipleEmails(List<MailWithAttachment> emails) {
        final int maxAttempts = 5;        // probaj 5 puta po email-u
        final long baseDelayMs = 1000L;   // 1s start
        final long maxDelayMs = 60000L;   // max 60s između pokušaja
        final long throttleMs = 150L;     // blago usporenje između mailova (da ne udariš M365 throttling)

        for (MailWithAttachment mail : emails) {
            int attempt = 0;
            while (true) {
                attempt++;
                try {
                    // (važna napomena dole) ovo je tvoja postojeća metoda
                    sendMailWithAttachment(mail);
                 
                    logSentEmail(mail);
                  
                    log.info("✅ Sent email to {} (attempt {}/{})", mail.getTo(), attempt, maxAttempts);
                    break; // uspeh -> sledeći mail

                } catch (MessagingException | MailException ex) {
                    boolean lastTry = (attempt >= maxAttempts);
                    log.warn("❌ Failed email to {} (attempt {}/{}): {}",
                        mail.getTo(), attempt, maxAttempts, ex.getMessage());

                    if (lastTry) {
                        // ne rušimo batch, samo nastavljamo dalje
                        log.error("🚫 Giving up on {} after {} attempts", mail.getTo(), maxAttempts);
                        break;
                    }

                    // exponential backoff + cap
                    long delay = Math.min(maxDelayMs, baseDelayMs * (1L << (attempt - 1))); // 1s,2s,4s,8s,16s...
                    // mali jitter da ne “udari” sve u isto vreme
                    delay = delay + (long) (Math.random() * 250);

                    sleepQuietly(delay);
                }
            }

            sleepQuietly(throttleMs);
        }
    }

    
    private synchronized void logSentEmail(MailWithAttachment mail) {
        try {
        	Path logPath = Paths.get("C:\\toplana\\maillog\\sent-mails.txt");
        	
            String line =
                    LocalDateTime.now()
                    + " | "
                    + mail.getTo()
                    + " | "
                    + mail.getAttachmentFileName()
                    + System.lineSeparator();

            Files.write(
                    logPath,
                    line.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (Exception e) {
            log.error("Ne mogu da upišem sent mail log", e);
        }
    }
    
    
    private void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void sendMailWithAttachment(MailWithAttachment mail) throws MessagingException {
    	
    	
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        String toAddress = mail.getTo();

        if (toAddress == null || toAddress.trim().isEmpty()) {
            throw new MessagingException("Email address is null or empty");
        }

        // Inspect raw input
    //    //System.out.println(">>> RAW to address: [" + toAddress + "]");
    //    //System.out.println(">>> Char-by-char analysis:");
        for (int i = 0; i < toAddress.length(); i++) {
            char c = toAddress.charAt(i);
          //  //System.out.printf("Char[%d] = '%c' (ASCII: %d)\n", i, c, (int) c);
        }

        // Stronger sanitization
        String sanitized = toAddress.replaceAll("[^\\p{ASCII}]", "")   // remove non-ASCII
                                    .replaceAll("[\\r\\n\\t]", "")     // remove newlines/tabs
                                    .trim();

   //     //System.out.println(">>> Sanitized to address: [" + sanitized + "]");

        // Validate with InternetAddress parser (more robust)
        try {
            InternetAddress[] parsed = InternetAddress.parse(sanitized, true);
            helper.setTo(parsed);
        } catch (Exception e) {
            throw new MessagingException("Invalid email address: " + sanitized, e);
        }

        // Set from address
        String fromAddress = jHipsterProperties.getMail().getFrom();
        if (fromAddress == null || fromAddress.isEmpty()) {
            throw new MessagingException("From address not configured in jhipster.mail.from");
        }
        
        
        helper.setFrom(fromAddress);
       // helper.setFrom("toplanamfn@masfak.ni.ac.rs");
        helper.setSubject(mail.getSubject());
        String html = "<h4>Poštovani,</h4><p>"+mail.getBody()+"</p>";
        helper.setText(html, true);
        
        
        if (mail.hasBlobAttachment()) {

            helper.addAttachment(
                mail.getAttachmentFileName(),
                new ByteArrayResource(mail.getAttachmentBytes())
            );

        } else {

            File attachment = new File(mail.getAttachmentPath());
            if (!attachment.exists()) {
                throw new MessagingException("Attachment file not found: " + mail.getAttachmentPath());
            }

            helper.addAttachment(
                attachment.getName(),
                new FileSystemResource(attachment)
            );
        }
        
       
        javaMailSender.send(message);
    //    //System.out.println("✅ Email sent to: " + sanitized);
    }
    
    private void sendMailWithAttachmentGRAPH(MailWithAttachment mail) throws MessagingException {
        try {
            String toAddress = mail.getTo();

            if (toAddress == null || toAddress.trim().isEmpty()) {
                throw new MessagingException("Email address is null or empty");
            }

            String sanitized = toAddress
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[\\r\\n\\t]", "")
                .trim();

            try {
                InternetAddress.parse(sanitized, true);
            } catch (Exception e) {
                throw new MessagingException("Invalid email address: " + sanitized, e);
            }

            String token = getGraphAccessToken();

            byte[] attachmentBytes;
            String attachmentFileName;

            if (mail.hasBlobAttachment()) {
                attachmentBytes = mail.getAttachmentBytes();
                attachmentFileName = mail.getAttachmentFileName();
            } else {
                File attachmentFile = new File(mail.getAttachmentPath());

                if (!attachmentFile.exists()) {
                    throw new MessagingException("Attachment file not found: " + mail.getAttachmentPath());
                }

                attachmentBytes = Files.readAllBytes(attachmentFile.toPath());
                attachmentFileName = attachmentFile.getName();
            }

            String html =
                "<h4>Poštovani,</h4><p>"
                    + mail.getBody()
                    + "</p>";

            String attachmentBase64 =
                Base64.getEncoder().encodeToString(attachmentBytes);

            String json =
                "{"
                    + "\"message\":{"
                        + "\"subject\":\"" + escapeJson(mail.getSubject()) + "\","
                        + "\"body\":{"
                            + "\"contentType\":\"HTML\","
                            + "\"content\":\"" + escapeJson(html) + "\""
                        + "},"
                        + "\"toRecipients\":["
                            + "{"
                                + "\"emailAddress\":{"
                                    + "\"address\":\"" + escapeJson(sanitized) + "\""
                                + "}"
                            + "}"
                        + "],"
                        + "\"attachments\":["
                            + "{"
                                + "\"@odata.type\":\"#microsoft.graph.fileAttachment\","
                                + "\"name\":\"" + escapeJson(attachmentFileName) + "\","
                                + "\"contentType\":\"application/pdf\","
                                + "\"contentBytes\":\"" + attachmentBase64 + "\""
                            + "}"
                        + "]"
                    + "},"
                    + "\"saveToSentItems\":true"
                + "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            String sender = graphSender;

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.exchange(
                "https://graph.microsoft.com/v1.0/users/" + sender + "/sendMail",
                HttpMethod.POST,
                entity,
                String.class
            );

        } catch (Exception e) {
            throw new MessagingException("Graph sendMail failed: " + e.getMessage(), e);
        }
    }
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\r", "\\r")
            .replace("\n", "\\n");
    }
    private String getGraphAccessToken() throws Exception {
        ConfidentialClientApplication app =
            ConfidentialClientApplication.builder(
                    graphClientId,
                    ClientCredentialFactory.createFromSecret(graphClientSecret)
                )
                .authority("https://login.microsoftonline.com/" + graphTenantId)
                .build();

        ClientCredentialParameters parameters =
            ClientCredentialParameters.builder(
                java.util.Collections.singleton("https://graph.microsoft.com/.default")
            ).build();

        return app.acquireToken(parameters).get().accessToken();
    }
    
}
