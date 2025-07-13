package toplana.web.rest.dto;

public class MailWithAttachment {
    public MailWithAttachment(String to, String subject, String body, String attachmentPath) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.attachmentPath = attachmentPath;
	}
	private String to;
    private String subject;
    private String body;
    private String attachmentPath;
	
    
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

    // Constructors, getters, setters
}
