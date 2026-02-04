package toplana.web.rest.dto;

public class MailWithAttachment {

    private String to;
    private String subject;
    private String body;

    // POSTOJEĆE – ostaje netaknuto
    private String attachmentPath;

    // NOVO – opciono (za BLOB varijantu)
    private byte[] attachmentBytes;
    private String attachmentFileName;

    // postojeći konstruktor – NIŠTA SE NE LOMI
    public MailWithAttachment(String to, String subject, String body, String attachmentPath) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachmentPath = attachmentPath;
    }

    // novi konstruktor – za BLOB
    public MailWithAttachment(
            String to,
            String subject,
            String body,
            byte[] attachmentBytes,
            String attachmentFileName
    ) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachmentBytes = attachmentBytes;
        this.attachmentFileName = attachmentFileName;
    }

    // getters / setters (postojeći)
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getAttachmentPath() {
        return attachmentPath;
    }
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    // getters / setters (novo)
    public byte[] getAttachmentBytes() {
        return attachmentBytes;
    }
    public void setAttachmentBytes(byte[] attachmentBytes) {
        this.attachmentBytes = attachmentBytes;
    }
    public String getAttachmentFileName() {
        return attachmentFileName;
    }
    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    // helper – zgodno u MailService
    public boolean hasBlobAttachment() {
        return attachmentBytes != null && attachmentBytes.length > 0;
    }
}
