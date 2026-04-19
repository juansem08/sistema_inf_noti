package notificaciones;
public class PushNotification extends Notification {
    private String deviceToken;
    public PushNotification(String code, String recipient, String message, String deviceToken) {
        super(code, recipient, message);
        this.deviceToken = deviceToken;
    }
    public String getDeviceToken() { return deviceToken; }
}
