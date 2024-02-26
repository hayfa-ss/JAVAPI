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
import java.util.List;

public class Notificationcontroller {

    private final NotificationServices notificationServices = new NotificationServices();

    @FXML
    private TextField IdRField;

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

    @FXML
    void ajouterNotification(ActionEvent event) {
        try {
            // Valider les champs de saisie
            if (validateInput()) {
                Notification nouvelleNotification = new Notification(
                        Integer.parseInt(IdRField.getText()),
                        Integer.parseInt(IdRField.getText()), // Assurez-vous de convertir IdR en entier si nécessaire
                        messageField.getText(),
                        notificationPreferenceField.getValue(),
                        invitationStatusField.getValue()
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
        // Valider que les champs nécessaires sont remplis
        boolean isUserIdValid = !IdRField.getText().isEmpty();
        boolean isMessageValid = !messageField.getText().isEmpty();
        boolean isNotificationPreferenceValid = notificationPreferenceField.getValue() != null;
        boolean isInvitationStatusValid = invitationStatusField.getValue() != null;

        // Afficher une alerte pour chaque champ invalide
        if (!isUserIdValid) {
            showErrorAlert("Erreur de saisie", "Veuillez entrer l'identifiant de l'utilisateur.");
        } else if (!isMessageValid) {
            showErrorAlert("Erreur de saisie", "Veuillez entrer un message.");
        } else if (!isNotificationPreferenceValid) {
            showErrorAlert("Erreur de saisie", "Veuillez sélectionner une préférence de notification.");
        } else if (!isInvitationStatusValid) {
            showErrorAlert("Erreur de saisie", "Veuillez sélectionner un statut d'invitation.");
        }

        return isUserIdValid && isMessageValid && isNotificationPreferenceValid && isInvitationStatusValid;
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
            tableView.setItems(observableList);
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
    }
}