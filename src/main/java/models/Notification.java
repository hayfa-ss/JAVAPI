package models;

public class Notification {

    private int idn;
    private int IdR;
    private String message;
    private String notificationPreference;
    private String invitationStatus;

    // Constructeurs

    public Notification(int idn, int IdR, String message, String notificationPreference, String invitationStatus) {
        this.idn = idn;
        this.IdR = IdR;
        this.message = message;
        this.notificationPreference = notificationPreference;
        this.invitationStatus = invitationStatus;
    }

    // Getters et Setters

    public int getIdn() {
        return idn;
    }

    public void setIdn(int idn) {
        this.idn = idn;
    }

    public int getIdR() {
        return IdR;
    }

    public void setIdR(int IdR) {
        this.IdR = IdR;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationPreference() {
        return notificationPreference;
    }

    public void setNotificationPreference(String notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idn=" + idn +
                ", IdR=" + IdR +
                ", message='" + message + '\'' +
                ", notificationPreference='" + notificationPreference + '\'' +
                ", invitationStatus='" + invitationStatus + '\'' +
                '}';
    }
}
