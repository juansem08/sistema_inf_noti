package notificaciones;
public class PushChannel implements NotificationChannel {
    public void deliver(Notification n) { n.setStatus("SENT (Push)"); }
}
