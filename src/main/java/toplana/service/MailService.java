package toplana.service;

import toplana.domain.User;

import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import toplana.web.rest.dto.MailWithAttachment;
import java.io.File;
/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

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
    @Async
    private void sendMailWithAttachment(MailWithAttachment mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        String toAddress = mail.getTo();

        if (toAddress == null || toAddress.trim().isEmpty()) {
            throw new MessagingException("Email address is null or empty");
        }

        // Inspect raw input
        System.out.println(">>> RAW to address: [" + toAddress + "]");
        System.out.println(">>> Char-by-char analysis:");
        for (int i = 0; i < toAddress.length(); i++) {
            char c = toAddress.charAt(i);
            System.out.printf("Char[%d] = '%c' (ASCII: %d)\n", i, c, (int) c);
        }

        // Stronger sanitization
        String sanitized = toAddress.replaceAll("[^\\p{ASCII}]", "")   // remove non-ASCII
                                    .replaceAll("[\\r\\n\\t]", "")     // remove newlines/tabs
                                    .trim();

        System.out.println(">>> Sanitized to address: [" + sanitized + "]");

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
        helper.setSubject(mail.getSubject());
        String html = "<h4>Poštovani,</h4><p>"+mail.getBody()+"</p>";
        helper.setText(html, true);

        File attachment = new File(mail.getAttachmentPath());
        if (!attachment.exists()) {
            throw new MessagingException("Attachment file not found: " + mail.getAttachmentPath());
        }

        helper.addAttachment(attachment.getName(), new FileSystemResource(attachment));

        javaMailSender.send(message);
        System.out.println("✅ Email sent to: " + sanitized);
    }
    
    
    
}
