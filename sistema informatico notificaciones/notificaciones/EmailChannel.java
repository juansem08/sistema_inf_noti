package notificaciones;
public class EmailChannel implements NotificationChannel {
    public void deliver(Notification n) { n.setStatus("SENT (Email)"); }
}
