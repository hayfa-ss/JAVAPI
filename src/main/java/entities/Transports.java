package entities;

public class Transports {
    private String type ,  statut;
    private int  capacite ;
    
    public  int id ;

    public Transports(String type, String statut, int capacite, int id) {
        this.type = type;
        this.statut = statut;
        this.capacite = capacite;
        this.id = id;
    }
    public Transports(String type, String statut, int capacite) {
        this.type = type;
        this.statut = statut;
        this.capacite = capacite;
    } public Transports() {

    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transports{" +
                "type='" + type + '\'' +
                ", statut='" + statut + '\'' +
                ", capacite=" + capacite +
                ", id=" + id +
                '}';
    }


}
