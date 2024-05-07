package Controller;

import entities.guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceGuide;
import java.sql.SQLException;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
public class AfficherGuideClientController {
    ServiceGuide sp = new ServiceGuide();

    @FXML
    private TableView<guide> tableView;

    @FXML
    private TableColumn<guide, String> nomColumn;

    @FXML
    private TableColumn<guide, String> prenomColumn;

    @FXML
    private TableColumn<guide, String> langueParleeColumn;

    @FXML
    private TableColumn<guide, String> experienceColumn;

    @FXML
    private TableColumn<guide, Button> actionColumn;


    ObservableList<guide> observableList;


    @FXML
    void initialize() {

        try {
            List<guide> personneList = sp.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);


            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            langueParleeColumn.setCellValueFactory(new PropertyValueFactory<>("langue_parlee"));
            experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        actionColumn.setCellFactory(column -> {
            return new TableCell<guide, Button>() {
                final Button button = new Button("Choisir");

                {
                    button.setOnAction(event -> {
                        guide rowData = getTableView().getItems().get(getIndex());
                        showAlert("Guide choisi", "Vous avez choisi : " + rowData.getNom() + " " + rowData.getPrenom());
                        try {
                            EmailSender.sendEmail("mohamedaziz.melki@esprit.tn", "Guide choisi", "Vous avez choisi : " + rowData.getNom() + " " + rowData.getPrenom());
                        } catch (Exception e) {
                            showAlert("Erreur", "Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
                        }
                    });
                }



                private void showAlert(String title, String content) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(content);
                    alert.showAndWait();
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };



        });
}


    public class EmailSender {

        public static void sendEmail(String recipient, String subject, String body) {
            final String username = "mohamedaziz.melki@esprit.tn"; // Votre adresse e-mail
            final String password = "uivqsqqnybmxldsc"; // Votre mot de passe

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("mohamedaziz.melki@esprit.tn", "uivqsqqnybmxldsc");
                        }
                    });


            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);

                System.out.println("E-mail envoyé avec succès.");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
