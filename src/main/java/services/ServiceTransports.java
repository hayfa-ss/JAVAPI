package services;

import entities.Transports;
import entities.guide;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTransports implements IService<Transports> {

    Connection connection;
    public ServiceTransports(){
        connection= MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Transports transports) throws SQLException {
        String req = "insert into transports ( type,  statut,  capacite) " +
                "values ('" + transports.getType() + "', '" + transports.getStatut() + "', '" +
                transports.getCapacite() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("transports ajouté avec succès");

    }

    @Override
    public void modifier(Transports transports) throws SQLException {

        String req = "update transports set type = ?, capacite = ?, statut = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, transports.getType());
            preparedStatement.setInt(2, transports.getCapacite());
            preparedStatement.setString(3, transports.getStatut());
            preparedStatement.setInt(4, transports.getId());

            preparedStatement.executeUpdate();
            System.out.println("transports modifié avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du transports : " + e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req ="delete from transports where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);



    }

    @Override
    public List<Transports> afficher() throws SQLException {
        List<Transports> transports = new ArrayList<>();
        String req = "select * from transports";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Transports transport = new Transports();
                transport.setId(rs.getInt("id"));
                transport.setStatut(rs.getString("statut"));
                transport.setCapacite(rs.getInt("capacite"));
                transport.setType(rs.getString("Type"));
                transports.add(transport);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des transports : " + e.getMessage());
        }
        return transports;
    }
    public void modifierStatut(int id, String nouveauStatut) throws SQLException {
        String req = "UPDATE transports SET statut = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, nouveauStatut);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Statut du transport mis à jour avec succès dans la base de données.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut du transport dans la base de données : " + e.getMessage());
        }
    }

}
