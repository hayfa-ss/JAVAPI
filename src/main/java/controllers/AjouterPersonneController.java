package controllers;

import entities.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import services.ServicePersonne;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class AjouterPersonneController {

    @FXML
    private TextField ageTF;

    @FXML
    private CheckBox check21;

    @FXML
    private CheckBox check2;

    @FXML
    private TextField emailFt;

    @FXML
    private Label iamount;

    @FXML
    private Label idate;

    @FXML
    private Label iemail;

    @FXML
    private Label ifirstname;

    @FXML
    private Label i2firstname;

    @FXML
    private Label ilastname;

    @FXML
    private Pane invoicePan;

    @FXML
    private Label ipayment;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private Pane paneForm;

    @FXML
    private Pane panePaymeNT;

    @FXML
    private Pane paneCard;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    void initialize() {
        paneForm.setVisible(true);
        panePaymeNT.setVisible(false);

        check2.setOnAction(event -> {
            if (check2.isSelected()) {
                check21.setSelected(false);
            }
        });

        check21.setOnAction(event -> {
            if (check21.isSelected()) {
                check2.setSelected(false);
            }
        });
    }

    @FXML
    void visibility(ActionEvent event) {
        paneForm.setVisible(false);
        panePaymeNT.setVisible(true);
    }

    @FXML
    void backOnAction(ActionEvent event) {
        panePaymeNT.setVisible(false);
        paneForm.setVisible(true);
    }

    @FXML
    private Button backButt;

    @FXML
    void show(ActionEvent event) {
        paneCard.setVisible(true);
    }

    @FXML
    void show1(ActionEvent event) {
        paneCard.setVisible(false);
    }

    @FXML
    void InsererPersonne(ActionEvent event) {
        // Valider les champs
        if (!validateFields()) {
            showErrorAlert("Error", "Please fill in all the fields correctly.");
            return;
        }

        // Continuer avec le traitement si les champs sont valides
        String firstName = nomTF.getText();
        String lastName = prenomTF.getText();
        String email = emailFt.getText();
        String paymentOptions = "";
        int amount = Integer.parseInt(ageTF.getText());
        String amountText = ageTF.getText();
        boolean amountValid = isNumeric(amountText) && Integer.parseInt(amountText) > 0;

        if (amountValid) {
            Date aDate = new Date();
            Payment p = new Payment(firstName, lastName, email, paymentOptions, aDate, amount);
            ServicePersonne sp = new ServicePersonne();
            try {
                sp.ajouter2(p);
                iamount.setText(String.valueOf(amount));
                idate.setText(String.valueOf(aDate));
                iemail.setText(email);
                ifirstname.setText(firstName);
                i2firstname.setText(firstName);
                ilastname.setText(lastName);
                ipayment.setText(paymentOptions);
                invoicePan.setVisible(true);

                // Configuration des propriétés pour l'envoi d'e-mails
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                // Définir le nom d'utilisateur et le mot de passe
                String username = "hayfasadkaoui989@gmail.com";
                String password = "hayfa2hamadi";

                // Création d'une session avec authentification SMTP
                Session session = Session.getInstance(props, new SMTPAuthenticator(username, password));

                // Envoi de l'e-mail avec le contenu de la facture
                sendMail(session, email, "Invoice for Your Payment", generateInvoiceContent(amount, aDate, firstName, lastName, paymentOptions));

                // Affichage d'une confirmation de succès
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Congratulations! Your payment has been successfully processed. This is your Invoice.");
                alert.show();

                // Lecture d'un son de confirmation
                String soundFilePath = "C:/Users/hayfa/Downloads/ta-da_yrvBrlS.mp3";
                Media sound = new Media(new File(soundFilePath).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();

                // Retourner la pane invoicePan
                ((Node) event.getSource()).getScene().setRoot(invoicePan);
            } catch (SQLException e) {
                showErrorAlert("Error", e.getMessage());
            }
        } else {
            showErrorAlert("Error", "Please ensure that the amount is a positive number.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateFields() {
        boolean allFieldsFilled = true;

        if (nomTF.getText().isEmpty()) {
            allFieldsFilled = false;
            nomTF.setStyle("-fx-border-color: red;");
        } else {
            nomTF.setStyle("");
        }

        if (prenomTF.getText().isEmpty()) {
            allFieldsFilled = false;
            prenomTF.setStyle("-fx-border-color: red;");
        } else {
            prenomTF.setStyle("");
        }

        if (emailFt.getText().isEmpty()) {
            allFieldsFilled = false;
            emailFt.setStyle("-fx-border-color: red;");
        } else {
            emailFt.setStyle("");
        }

        if (!(check2.isSelected() || check21.isSelected())) {
            allFieldsFilled = false;
            check2.setStyle("-fx-border-color: red;");
            check21.setStyle("-fx-border-color: red;");
        } else {
            check2.setStyle("");
            check21.setStyle("");
        }

        if (!isNumeric(ageTF.getText()) || Integer.parseInt(ageTF.getText()) <= 0) {
            allFieldsFilled = false;
            ageTF.setStyle("-fx-border-color: red;");
        } else {
            ageTF.setStyle("");
        }

        return allFieldsFilled;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonnes.fxml"));
            Parent root = loader.load();
            AfficherPersonnesController apc = loader.getController();
            apc.setData(nomTF.getText());
            ageTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void sendMail(Session session, String to, String subject, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour générer le contenu de la facture
    private String generateInvoiceContent(int amount, Date date, String firstName, String lastName, String paymentOptions) {
        // Logique pour générer le contenu de la facture
        // Vous devez implémenter cette méthode selon vos besoins
        return "Invoice Content";
    }

    public class SMTPAuthenticator extends Authenticator {
        private final String username;
        private final String password;

        public SMTPAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(username, password);
        }
    }
}
