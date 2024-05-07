package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Notification;
import models.Reservation;
import services.NotificationServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Notificationcontroller {

    private final NotificationServices notificationServices = new NotificationServices();

    @FXML
    private TextField emailField;  // Ajout du champ emailField


    @FXML
    private TextArea messageField;

    @FXML
    private ComboBox<String> notificationPreferenceField;

    @FXML
    private ComboBox<String> invitationStatusField;

    @FXML
    private TableView<Notification> tableView;

    @FXML
    private TableColumn<Notification, Integer> IdRColumn;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, String> notificationPreferenceColumn;

    @FXML
    private TableColumn<Notification, String> invitationStatusColumn;

    @FXML
    private TableColumn<Notification, Void> modifyTC;

    @FXML
    private TableColumn<Notification, Void> deleteTC;

    public int idres;

    @FXML
    void ajouterNotification(ActionEvent event) {

        try {

            // Valider les champs de saisie
            if (validateInput()) {
                // Vérifier l'unicité de l'email
                if (!isEmailUnique(emailField.getText())) {
                    showErrorAlert("Erreur d'unicité", "Cet email est déjà associé à une notification.");
                    return;
                }
                Notification nouvelleNotification;
                nouvelleNotification = new Notification(
                        idres,
                        messageField.getText(),
                        notificationPreferenceField.getValue(),
                        invitationStatusField.getValue(),
                        emailField.getText()  // Récupérer la valeur de l'email

                );

                notificationServices.ajouter(nouvelleNotification);


                loadTableData();
            } else {
                showErrorAlert("Erreur de saisie", "Veuillez vérifier les champs de saisie.");
            }
        } catch (NumberFormatException | SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }
    private boolean validateInput() {
        List<String> errors = new ArrayList<>();

        if (messageField.getText().isEmpty()) {
            errors.add("Veuillez entrer un message.");
        }

        if (notificationPreferenceField.getValue() == null) {
            errors.add("Veuillez sélectionner une préférence de notification.");
        }
        if (!isValidEmail(emailField.getText())) {
            errors.add("Veuillez entrer un email valide.");
        }

        if (invitationStatusField.getValue() == null) {
            errors.add("Veuillez sélectionner un statut d'invitation.");
        }

        if (!errors.isEmpty()) {
            showErrorAlert("Erreur de saisie", String.join("\n", errors));
        }


        return errors.isEmpty();
    }
    private boolean isValidEmail(String email) {
        // Utiliser une expression régulière simple pour valider l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isEmailUnique(String email) {
        try {
            List<Notification> existingNotifications = notificationServices.recuperer();
            for (Notification notification : existingNotifications) {
                if (email.equals(notification.getEmail())) {
                    return false; // L'email n'est pas unique
                }
            }
            return true; // L'email est unique
        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
            return false; // En cas d'erreur, considérez l'email comme non unique
        }
    }

    @FXML
    void initialize() {
        try {
            List<Notification> notifications = notificationServices.recuperer();
            ObservableList<Notification> observableList = FXCollections.observableList(notifications);
            tableView.setItems(observableList);
            IdRColumn.setCellValueFactory(new PropertyValueFactory<>("IdR"));
            messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
            notificationPreferenceColumn.setCellValueFactory(new PropertyValueFactory<>("notificationPreference"));
            invitationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("invitationStatus"));

                 configureDeleteColumn();
            configureModifyColumn();
        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }


    }

    @FXML
    private void modifyNotification(Notification notificationToModify) {
        try {
            // Charger l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierNotification.fxml"));
            Parent root = loader.load();

            // Passer les données de la notification à modifier au contrôleur de l'interface de modification
            ModifierNotificationcontroller modifierController = loader.getController();
            modifierController.initData(notificationToModify);

            // Afficher l'interface de modification
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Notification");
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur", "Une erreur s'est produite lors de l'ouverture de l'interface de modification.");
        }
    }

    private void configureDeleteColumn() {
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Notification notificationToDelete = getTableView().getItems().get(getIndex());
                        try {
                            notificationServices.delete(notificationToDelete.getIdn());
                            tableView.getItems().remove(notificationToDelete);
                        } catch (SQLException e) {
                            showErrorAlert("Error", e.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void configureModifyColumn() {
        modifyTC.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modifier");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        Notification notificationToModify = getTableView().getItems().get(getIndex());
                        modifyNotification(notificationToModify);
                    });
                }
            }
        });
    }

    private void loadTableData() {
        try {
            List<Notification> notifications = notificationServices.recuperer();
            ObservableList<Notification> observableList = FXCollections.observableList(notifications);
            /*tableView.setItems(observableList);*/
        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Notification.fxml"));
            messageField.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void initData(Reservation reservation) {
        // Logique pour initialiser la vue avec les données de la réservation
    }


    public void initDataWithReservationId(int reservationId) {
        // Afficher une alerte avec l'ID de réservation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Initialisation des données avec l'ID de réservation : " + reservationId);
        alert.showAndWait();
        idres=reservationId;
    }


    @FXML
    void naviguezVersAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichernotification.fxml"));
            messageField.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void statistique(ActionEvent event) {
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/ReservationStatistics.fxml"));
                    messageField.getScene().setRoot(root);
        }catch (IOException e){
                    System.err.println(e.getMessage());
                }

                }
    }


    /******************00000000000000000*************************/



