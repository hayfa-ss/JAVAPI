package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evenement {







        private int id ;

        public static LocalDate date_depart;
    public LocalDate date_fin;
        public LocalTime heure ;
        public String lieu,type ;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDate getDate_depart() {
            return date_depart;
        }

        public void setDate_depart(LocalDate date_depart) {
            Evenement.date_depart = date_depart;
        }

        public LocalDate getDate_fin() {
            return date_fin;
        }

        public void setDate_fin(LocalDate date_fin) {
            this.date_fin = date_fin;
        }

        public LocalTime getHeure() {
            return heure;
        }

        public void setHeure(LocalTime heure) {
            this.heure = heure;
        }

        public String getLieu() {
            return lieu;
        }

        public void setLieu(String lieu) {
            this.lieu = lieu;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Evenement(int id, LocalDate date_depart, LocalDate date_fin, LocalTime heure, String lieu, String type) {
            this.id = id;
            this.date_depart = date_depart;
            this.date_fin = date_fin;
            this.heure = heure;
            this.lieu = lieu;
            this.type = type;
        }

        public Evenement() {

        }

        public Evenement(LocalDate date_depart, LocalDate date_fin, LocalTime heure, String lieu, String type) {
            Evenement.date_depart = date_depart;
            this.date_fin = date_fin;
            this.heure = heure;
            this.lieu = lieu;
            this.type = type;
        }

        @Override
        public String toString() {
            return "evenement{" +
                    "id=" + id +
                    ", date_depart=" + date_depart +
                    ", date_fin=" + date_fin +
                    ", heure=" + heure +
                    ", lieu='" + lieu + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }




