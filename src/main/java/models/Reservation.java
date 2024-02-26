package models;

public class Reservation {

    private int id;
    private int nbrplace;
    private int id_user;
    private int id_event;
    private int telephone;
    private String adresse;
    private String email;

    // Constructors
    public Reservation() {
    }

    public Reservation(int id, int nbrplace, int id_user, int id_event, int telephone, String adresse, String email) {
        this.id = id;
        this.nbrplace = nbrplace;
        this.id_user = id_user;
        this.id_event = id_event;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
    }

    public Reservation(int nbrplace, int id_user, int id_event, int telephone, String adresse, String email) {
        this.nbrplace = nbrplace;
        this.id_user = id_user;
        this.id_event = id_event;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrplace() {
        return nbrplace;
    }

    public void setNbrplace(int nbrplace) {
        this.nbrplace = nbrplace;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nbrplace=" + nbrplace +
                ", id_user=" + id_user +
                ", id_event=" + id_event +
                ", telephone=" + telephone +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
