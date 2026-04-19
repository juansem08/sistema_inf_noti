package notificaciones;
import java.util.Date;
public abstract class Notification {
    private String code, recipient, message, status;
    private Date sendDate;
    public Notification(String code, String recipient, String message) {
        this.code = code; this.recipient = recipient; this.message = message;
        this.sendDate = new Date(); this.status = "PENDING";
    }
    public String getCode() { return code; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public Date getSendDate() { return sendDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
