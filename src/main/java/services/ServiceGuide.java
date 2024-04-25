package services;

import entities.guide;
import utils.MyDatabase;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class ServiceGuide implements IService <guide> {

    Connection connection;
    public ServiceGuide(){
        connection= MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(guide guidee) throws SQLException {
        String req = "insert into guidee (nom, prenom, langue_parlee, experience) " +
                "values ('" + guidee.getNom() + "', '" + guidee.getPrenom() + "', '" +
                guidee.getLangue_parlee() + "', '" + guidee.getExperience() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("Guide ajouté avec succès");
    }


    @Override
    public void modifier(guide guidee) throws SQLException {
        String req = "update guidee set nom = ?, prenom = ?, langue_parlee = ?, experience = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, guidee.getNom());
            preparedStatement.setString(2, guidee.getPrenom());
            preparedStatement.setString(3, guidee.getLangue_parlee());
            preparedStatement.setString(4, guidee.getExperience());
            preparedStatement.setInt(5, guidee.getId());

            preparedStatement.executeUpdate();
            System.out.println("Guide modifié avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du guide : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req ="delete from guidee where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);


    }

    @Override
    public List<guide> afficher() throws SQLException {
        List<guide> guides = new ArrayList<>();
        String req = "select * from guidee";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                guide guidee = new guide();
                guidee.setId(rs.getInt("id"));
                guidee.setNom(rs.getString("nom"));
                guidee.setPrenom(rs.getString("prenom"));
                guidee.setLangue_parlee(rs.getString("langue_parlee"));
                guidee.setExperience(rs.getString("experience"));
                guides.add(guidee);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des guides : " + e.getMessage());
        }
        return guides;
    }
    public void updateStatut(guide guide) throws SQLException {
        String req = "UPDATE guidee SET statut = 'Choisi' WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, guide.getId());
            preparedStatement.executeUpdate();
            System.out.println("Statut mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

}
