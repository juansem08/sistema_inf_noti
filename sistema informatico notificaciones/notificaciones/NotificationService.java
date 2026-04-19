package notificaciones;
public class NotificationService {
    public void sendNotification(Notification n) {
        NotificationChannel channel;
        if (n instanceof EmailNotification) channel = new EmailChannel();
        else if (n instanceof SMSNotification) channel = new SMSChannel();
        else if (n instanceof PushNotification) channel = new PushChannel();
        else throw new IllegalArgumentException("Unknown type");
        channel.deliver(n);
    }
}
