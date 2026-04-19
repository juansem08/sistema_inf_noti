package notificaciones;
public class SMSNotification extends Notification {
    private String phoneNumber;
    public SMSNotification(String code, String recipient, String message, String phoneNumber) {
        super(code, recipient, message);
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() { return phoneNumber; }
}
