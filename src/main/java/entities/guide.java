package entities;
import javafx.scene.control.Button;

public class guide {
    private String nom,prenom,langue_parlee,experience;
    private int id ;

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public guide(String statut) {
        this.statut = statut;
    }

    private String statut; // Nouvelle propriété pour le statut

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLangue_parlee() {
        return langue_parlee;
    }

    public void setLangue_parlee(String langue_parlee) {
        this.langue_parlee = langue_parlee;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public guide(String nom, String prenom, String langue_parlee, String experience) {
        this.nom = nom;
        this.prenom = prenom;
        this.langue_parlee = langue_parlee;
        this.experience = experience;
    }
public guide(){

}
    public guide(int id ,String nom, String prenom, String langue_parlee, String experience) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.langue_parlee = langue_parlee;
        this.experience = experience;
    }



    @Override
    public String toString() {
        return "guide{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", langue_parlee='" + langue_parlee + '\'' +
                ", experience='" + experience + '\'' +
                '}';
    }



}
