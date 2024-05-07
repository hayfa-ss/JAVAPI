package models;

public class Notification {

    private int idn;
    private int IdR;
    private String message;
    private String notificationPreference;
    private String invitationStatus;
    private String email;

    // Constructeurs
    public Notification(int idn, int IdR, String message, String notificationPreference, String invitationStatus, String email) {
        this.idn = idn;
        this.IdR = IdR;
        this.message = message;
        this.notificationPreference = notificationPreference;
        this.invitationStatus = invitationStatus;
        this.email = email;
    }

    public Notification(int idR, String message, String notificationPreference, String invitationStatus, String email) {
        IdR = idR;
        this.message = message;
        this.notificationPreference = notificationPreference;
        this.invitationStatus = invitationStatus;
        this.email = email;
    }

    public Notification(int idR, int i, String text, String value, String value1) {
    }

    public int getIdR() {
        return IdR;
    }

    public void setIdR(int idR) {
        IdR = idR;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idn=" + idn +
                ", IdR=" + IdR +
                ", message='" + message + '\'' +
                ", notificationPreference='" + notificationPreference + '\'' +
                ", invitationStatus='" + invitationStatus + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getIdn() {
        return idn;
    }

    public void setIdn(int idn) {
        this.idn = idn;
    }

    public void setReservation(Reservation reservation) {
    }
}
