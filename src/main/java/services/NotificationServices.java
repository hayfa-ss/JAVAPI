package services;

import models.Notification;
import models.Reservation;
import utils.MyDatabase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NotificationServices implements NService<Notification> {

    private final Connection connection;
    public EmailSender emailSender;
    public NotificationServices()

    {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Notification notification) throws SQLException {
        // Vérifier si la réservation associée existe
        if (reservationExists(notification.getIdR())) {
            // Ajouter la notification uniquement si la réservation existe
            String sql = "INSERT INTO notification (IdR, message, notificationPreference, invitationStatus, email) VALUES (?,?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, notification.getIdR());
                preparedStatement.setString(2, notification.getMessage());
                preparedStatement.setString(3, notification.getNotificationPreference());
                preparedStatement.setString(4, notification.getInvitationStatus());
                preparedStatement.setString(5, notification.getEmail());


// Envoyer l'e-mail en fonction du statut de la réservation
                if (notification.getInvitationStatus().equals("Confirmée")) {
                    String confirmMessage = "<h3> Cher client, <br/> Merci pour votre réservation. Votre réservation est <span style='color:green'> <b>confirmée</b> </span>. <br/> Nous espérons que vous apprécierez votre événement. </h3>";
                    sendEmail(notification.getEmail(),confirmMessage);
                    System.out.println("Reservation Confirmée! E-mail envoyé.");
                } else if (notification.getInvitationStatus().equals("Annulée")) {
                    String cancelMessage = "<h3> Cher client, <br/> Merci pour votre réservation, <span style='color:red'> mais votre réservation est <b>PAS ENCORE</b> Annulée </span>. <br/> Nous espérons que vous apprécierez votre événement. </h3>";
                    sendEmail(notification.getEmail(), cancelMessage);
                    System.out.println("Reservation Annulée ! E-mail envoyé.");
                }


                preparedStatement.executeUpdate();

                // Récupérer l'ID généré automatiquement (s'il y en a un)
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedid = generatedKeys.getInt(1);
                        notification.setIdn(generatedid);
                    }
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Gérer le cas où la réservation n'existe pas
            throw new SQLException("La réservation avec l'ID " + notification.getIdR() + " n'existe pas.");
        }
    }

    private boolean reservationExists(int reservationId) throws SQLException {
        // Vérifier si la réservation existe dans la table reservation
        String checkSql = "SELECT id FROM reservation WHERE id = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setInt(1, reservationId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                return resultSet.next(); // Si le résultat a au moins une ligne, la réservation existe
            }
        }
    }

    @Override
    public void modifier(int idn, Notification notification) throws SQLException {
        String sql = "UPDATE notification SET message = ?, notificationPreference = ?, invitationStatus = ?  WHERE idn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, notification.getMessage());
            preparedStatement.setString(2, notification.getNotificationPreference());
            preparedStatement.setString(3, notification.getInvitationStatus());
            preparedStatement.setInt(4, idn);
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
                Notification n = mapResultSetToNotification(rs);
                notifications.add(n);
            }
            return notifications;
        }
    }

    public List<Notification> recupererAvecJointure() throws SQLException {
        String sql = "SELECT n.*, r.* FROM notification n JOIN reservation r ON n.IdR = r.id";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification n = mapResultSetToNotification(rs);
                Reservation reservation = mapResultSetToReservation(rs);
                n.setReservation(reservation);
                notifications.add(n);
            }
            return notifications;
        }
    }

    @Override
    public int readById(int idn) {
        // À implémenter si nécessaire
        return idn;
    }

    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("idn"),
                rs.getInt("IdR"),
                rs.getString("message"),
                rs.getString("notificationPreference"),
                rs.getString("invitationStatus"),
                rs.getString("email")  // Ajout de la colonne email
        );
    }


    // Méthode utilitaire pour mapper un ResultSet à un objet Reservation
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("r.id"),
                rs.getInt("r.nbrplace"),
                rs.getInt("r.id_user"),
                rs.getInt("r.id_event"),
                rs.getInt("r.telephone"),
                rs.getString("r.adresse"),
                rs.getString("r.email")
        );
    }
        public void sendEmail (String to, String descri) throws MessagingException {
            // Setup mail server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com"); // Change to your SMTP server
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", "587");
            // Get Session
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("inesgharbi366@gmail.com", "ysfw uspa gmsf pplm"); // Change to your email and password
                }
            });

            // Create message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("inesgharbi366@gmail.com")); // Change to your email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            String subject = "CITYVIBE RESERVATION TEAM ";

            String htmlMsg = descri
                    + "<img src='../images/A.png'>";

            message.setSubject(subject);
            message.setContent(htmlMsg, "text/html");
            //message.setText("Dear Client, \n Thank you for you reservation , We hope you would enjoy your event ");

            // Send message
            Transport.send(message);
        }


    }