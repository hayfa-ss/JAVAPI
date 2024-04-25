package services;

import entities.Evenement;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static entities.Evenement.date_depart;

public class ServiceEvenement implements IService<Evenement>{
    Connection connection;
    public ServiceEvenement(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Evenement evenement) throws SQLException {

        if (evenement.getDate_depart() == null || evenement.getDate_fin() == null || evenement.getHeure() == null || evenement.getType() == null || evenement.getLieu() == null) {
            throw new IllegalArgumentException("Les champs date_depart, date_fin et heure sont obligatoires.");
        }


        if (date_depart.getYear() < 2024) {
            throw new IllegalArgumentException("La date de départ doit être à partir de l'année 2024.");
        }




//        List<String> villesTunisie = Arrays.asList("Tunis", "Sfax", "Sousse", "Bizerte", "Gabès","Nabeul", "Monastir", "Kairouan", "Gafsa", "Tozeur", "Tataouine", "Mahdia", "Kasserine", "Hammamet", "Djerba"); // Ajoutez d'autres villes si nécessaire
//        if (!villesTunisie.contains(evenement.getLieu())) {
//            throw new IllegalArgumentException("Le lieu doit être une ville en Tunisie.");
//        }


        String req;

         req = "insert into evenement (date_depart, date_fin, heure, lieu, type) values ('" +
                evenement.getDate_depart() + "', '" +
                evenement.getDate_fin() + "', '" +
                Time.valueOf(evenement.getHeure()) + "', '" +
                evenement.getLieu() + "', '" +
                evenement.getType() + "')";
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("evenement ajoute");


    }

    @Override
    public void modifier(Evenement evenement) throws SQLException {
        String req;
        req = "update evenement set date_depart=?, date_fin=? , heure=? , lieu=?,type=? where id=?";



        PreparedStatement preparedStatement = connection.prepareStatement(req);

        preparedStatement.setObject(1, evenement.getDate_depart());
        preparedStatement.setObject(2, evenement.getDate_fin());
        preparedStatement.setObject(3, Time.valueOf(evenement.getHeure()));
        preparedStatement.setString(4, evenement.getLieu());
        preparedStatement.setString(5, evenement.getType());
        preparedStatement.setInt(6,evenement.getId());


        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "delete from evenement  where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }


            @Override
            public List<Evenement> afficher() throws SQLException {
                List<Evenement> evenements = new ArrayList<>();
            String req = "select * from evenement ";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(req);
                while (rs.next()) {
                    Evenement event= new Evenement();
                    event.setDate_depart(rs.getObject("date_depart", LocalDate.class));
                    event.setDate_fin(rs.getObject("date_fin", LocalDate.class));
                    event.setHeure(rs.getObject("heure", LocalTime.class));
                    event.setLieu(rs.getString("lieu"));
                    event.setType(rs.getString("type"));
                    event.setId(rs.getInt("id"));

                    evenements.add(event);
                }
                return evenements;
            }

        }
