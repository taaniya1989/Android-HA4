package edu.sdsu.tvidhate.registerationapp.Entity;

public class Notifications {

    private Long notificationTime;
    private String notificationMessage;

    public Notifications(Long notificationTime, String notificationMessage) {
        this.notificationTime = notificationTime;
        this.notificationMessage = notificationMessage;
    }

    public Long getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Long notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
}
