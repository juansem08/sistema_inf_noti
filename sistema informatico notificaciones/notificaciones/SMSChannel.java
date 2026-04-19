package notificaciones;
public class SMSChannel implements NotificationChannel {
    public void deliver(Notification n) { n.setStatus("SENT (SMS)"); }
}
