package notificaciones;
public class NotificationFactory {
    public static Notification createNotification(String medium, String code, String recipient, String message, String... extraInfo) {
        switch (medium.toLowerCase()) {
            case "email": return new EmailNotification(code, recipient, message, extraInfo[0], extraInfo[1]);
            case "sms": return new SMSNotification(code, recipient, message, extraInfo[0]);
            case "push": return new PushNotification(code, recipient, message, extraInfo[0]);
            default: throw new IllegalArgumentException("Invalid medium");
        }
    }
}
