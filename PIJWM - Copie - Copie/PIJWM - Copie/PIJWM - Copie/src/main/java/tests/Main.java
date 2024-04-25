package tests;

import entities.Evenement;
import services.ServiceEvenement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        //  MyDatabase.getInstance().getConnection();
        LocalDate datedep = LocalDate.of(2024, 2, 1);
        LocalDate datefin = LocalDate.of(2024, 5, 30);
        LocalTime Heure=LocalTime.of(16,45,30);

        Evenement evenement1 = new Evenement (1, datedep, datefin,Heure,"Tunis ", "festival");



        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            serviceEvenement.ajouter(evenement1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
//        try {
//            serviceEvenement.modifier(evenement1);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//
//        }
//        try{
//            serviceEvenement.supprimer(1);
//        }
//        catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println(serviceEvenement.afficher());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }}
