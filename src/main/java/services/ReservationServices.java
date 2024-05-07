package services;

import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservationServices implements IService<Reservation> {

    private final Connection connection;

    public ReservationServices() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (nbrplace, id_user, id_event, telephone, adresse, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getNbrplace());
            preparedStatement.setInt(2, reservation.getId_user());
            preparedStatement.setInt(3, reservation.getId_event());
            preparedStatement.setInt(4, reservation.getTelephone());
            preparedStatement.setString(5, reservation.getAdresse());
            preparedStatement.setString(6, reservation.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(int id, Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET nbrplace = ?, id_user = ?, id_event = ?, telephone = ?, adresse = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getNbrplace());
            preparedStatement.setInt(2, reservation.getId_user());
            preparedStatement.setInt(3, reservation.getId_event());
            preparedStatement.setInt(4, reservation.getTelephone());
            preparedStatement.setString(5, reservation.getAdresse());
            preparedStatement.setString(6, reservation.getEmail());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Reservation> recuperer() throws SQLException {
        String sql = "SELECT * FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Reservation> reservations = new ArrayList<>();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setNbrplace(rs.getInt("nbrplace"));
                r.setId_user(rs.getInt("id_user"));
                r.setId_event(rs.getInt("id_event"));
                r.setTelephone(rs.getInt("telephone"));
                r.setAdresse(rs.getString("adresse"));
                r.setEmail(rs.getString("email"));
                reservations.add(r);
            }
            return reservations;
        }
    }

    @Override
    public void readById(int id) {
        // Vous pouvez implémenter cette méthode si nécessaire

    }

    public List<Reservation> getAllReservations() throws SQLException {
        String sql = "SELECT * FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                Reservation reservation = mapResultSetToReservation(resultSet);
                reservations.add(reservation);
            }
            return reservations;
        }
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getInt("id"));
        reservation.setNbrplace(resultSet.getInt("nbrplace"));
        reservation.setId_user(resultSet.getInt("id_user"));
        reservation.setId_event(resultSet.getInt("id_event"));
        reservation.setTelephone(resultSet.getInt("telephone"));
        reservation.setAdresse(resultSet.getString("adresse"));
        reservation.setEmail(resultSet.getString("email"));
        return reservation;
    }


    public List<Integer> getReservationStatsByPlace() {
        String sql = "SELECT nbrplace, COUNT(*) AS count FROM reservation GROUP BY nbrplace";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Integer> stats = new ArrayList<>();
            while (rs.next()) {
                int count = rs.getInt("count");
                stats.add(count);
            }
            return stats;
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer ou journaliser l'exception ici
            return Collections.emptyList(); // Retourner une liste vide en cas d'erreur
        }
    }

    }
