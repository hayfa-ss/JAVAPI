package services;

import models.Notification;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationServices implements NService<Notification> {

    private final Connection connection;

    public NotificationServices() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Notification notification) throws SQLException {
        String sql = "INSERT INTO notification (IdR, message, notificationPreference, invitationStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, notification.getIdR());
            preparedStatement.setString(2, notification.getMessage());
            preparedStatement.setString(3, notification.getNotificationPreference());
            preparedStatement.setString(4, notification.getInvitationStatus());
            preparedStatement.executeUpdate();

            // Récupérer l'ID généré automatiquement (s'il y en a un)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                notification.setIdn(generatedId);
            }
        }
    }

    @Override
    public void modifier(int idn, Notification notification) throws SQLException {
        String sql = "UPDATE notification SET IdR = ?, message = ?, notificationPreference = ?, invitationStatus = ? WHERE idn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, notification.getIdR());
            preparedStatement.setString(2, notification.getMessage());
            preparedStatement.setString(3, notification.getNotificationPreference());
            preparedStatement.setString(4, notification.getInvitationStatus());
            preparedStatement.setInt(5, idn);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int idn) throws SQLException {
        String sql = "DELETE FROM notification WHERE idn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idn);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Notification> recuperer() throws SQLException {
        String sql = "SELECT * FROM notification";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification n = new Notification(
                        rs.getInt("idn"),
                        rs.getInt("IdR"),
                        rs.getString("message"),
                        rs.getString("notificationPreference"),
                        rs.getString("invitationStatus")
                );
                notifications.add(n);
            }
            return notifications;
        }
    }

    @Override
    public void readById(int idn) {
        // À implémenter si nécessaire
    }
}
