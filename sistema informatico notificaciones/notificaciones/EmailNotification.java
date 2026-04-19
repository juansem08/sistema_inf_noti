package notificaciones;
public class EmailNotification extends Notification {
    private String emailAddress, subject;
    public EmailNotification(String code, String recipient, String message, String emailAddress, String subject) {
        super(code, recipient, message);
        this.emailAddress = emailAddress; this.subject = subject;
    }
    public String getEmailAddress() { return emailAddress; }
    public String getSubject() { return subject; }
}
