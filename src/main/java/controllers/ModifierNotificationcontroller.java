package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Notification;
import services.NotificationServices;

import javafx.event.ActionEvent;

public class ModifierNotificationcontroller {

    @FXML
    private TextField nvIdRTF;

    @FXML
    private TextArea nvmessageTF;

    @FXML
    private ComboBox<String> nvnotificationPreferenceTF;

    @FXML
    private ComboBox<String> nvinvitationStatusTF;

    private final NotificationServices notificationServices = new NotificationServices();
    private Notification notificationToModify;

    public void initData(Notification notification) {
        this.notificationToModify = notification;
        // Afficher les détails de la notification à modifier dans les champs de texte

        nvIdRTF.setText(String.valueOf(notification.getIdR()));
        nvmessageTF.setText(notification.getMessage());
        nvnotificationPreferenceTF.setValue(notification.getNotificationPreference());
        nvinvitationStatusTF.setValue(notification.getInvitationStatus());
    }

    @FXML
    void modifierNotification(ActionEvent event) {
        try {
            // Valider les champs de saisie
            if (validateInput()) {
                // Mettre à jour les données de la notification avec les nouvelles valeurs

                notificationToModify.setIdR(Integer.parseInt(nvIdRTF.getText()));
                notificationToModify.setMessage(nvmessageTF.getText());
                notificationToModify.setNotificationPreference(nvnotificationPreferenceTF.getValue());
                notificationToModify.setInvitationStatus(nvinvitationStatusTF.getValue());

                // Appeler la méthode de mise à jour dans le service de notification
                notificationServices.modifier(notificationToModify.getIdn(), notificationToModify);

                // Fermer la fenêtre de modification
                Stage stage = (Stage) nvIdRTF.getScene().getWindow();
                stage.close();

                // Afficher une alerte pour confirmer la modification
                showAlert("Modification réussie", "La notification a été modifiée avec succès.");
            } else {
                showAlert("Erreur de saisie", "Veuillez vérifier les champs de saisie.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de la notification.");
        }
    }

    private boolean validateInput() {
        // Valider que le champ message est rempli
        boolean isMessageValid = !nvmessageTF.getText().isEmpty();

        // Afficher une alerte si le champ message n'est pas valide
        if (!isMessageValid) {
            showAlert("Erreur de saisie", "Veuillez entrer un message.");
        }

        return isMessageValid;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
