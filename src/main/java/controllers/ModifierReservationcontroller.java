package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Reservation;
import services.ReservationServices;

import javafx.event.ActionEvent;

public class ModifierReservationcontroller {

    @FXML
    private TextField nvnbrPlaceTF;

    @FXML
    private TextField nvidUserTF;

    @FXML
    private TextField nvidEventTF;

    @FXML
    private ComboBox<String> adresseTF;

    @FXML
    private TextField nvemailTF;

    @FXML
    private TextField nvtelephoneTF;

    private final ReservationServices rs = new ReservationServices();
    private Reservation reservationToModify;

    public void initData(Reservation reservation) {
        this.reservationToModify = reservation;
        // Display details of the reservation to be modified in the text fields
        nvnbrPlaceTF.setText(String.valueOf(reservation.getNbrplace()));
        nvidUserTF.setText(String.valueOf(reservation.getId_user()));
        nvidEventTF.setText(String.valueOf(reservation.getId_event()));
        nvtelephoneTF.setText(String.valueOf(reservation.getTelephone()));
        adresseTF.setValue(reservation.getAdresse());
        nvemailTF.setText(reservation.getEmail());
    }

    @FXML
    void modifierReservation(ActionEvent event) {
        try {
            // Validate input fields
            if (validateInput()) {
                // Update reservation data with new values
                reservationToModify.setNbrplace(Integer.parseInt(nvnbrPlaceTF.getText()));
                reservationToModify.setId_user(Integer.parseInt(nvidUserTF.getText()));
                reservationToModify.setId_event(Integer.parseInt(nvidEventTF.getText()));
                reservationToModify.setTelephone(Integer.parseInt(nvtelephoneTF.getText()));
                reservationToModify.setAdresse(adresseTF.getValue());
                reservationToModify.setEmail(nvemailTF.getText());

                // Call the update method in the reservation service
                rs.modifier(reservationToModify.getId(), reservationToModify);

                // Close the modification window
                Stage stage = (Stage) nvnbrPlaceTF.getScene().getWindow();
                stage.close();

                // Show an alert to confirm the modification
                showAlert("Modification réussie", "La réservation a été modifiée avec succès.");
            } else {
                showAlert("Erreur de saisie", "Veuillez vérifier les champs de saisie.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de la réservation.");
        }
    }

    private boolean validateInput() {
        // Validate that the required fields are filled
        boolean isNbrPlaceValid = !nvnbrPlaceTF.getText().isEmpty();
        boolean isIdUserValid = !nvidUserTF.getText().isEmpty();
        boolean isIdEventValid = !nvidEventTF.getText().isEmpty();
        boolean isTelephoneValid = isValidPhoneNumber(nvtelephoneTF.getText());
        boolean isEmailValid = isValidEmail(nvemailTF.getText());

        if (!isNbrPlaceValid) {
            showAlert("Erreur de saisie", "Veuillez entrer le nombre de places.");
        } else if (!isIdUserValid) {
            showAlert("Erreur de saisie", "Veuillez entrer l'identifiant de l'utilisateur.");
        } else if (!isIdEventValid) {
            showAlert("Erreur de saisie", "Veuillez entrer l'identifiant de l'événement.");
        } else if (!isTelephoneValid) {
            showAlert("Erreur de saisie", "Veuillez entrer un numéro de téléphone valide (8 chiffres).");
        } else if (!isEmailValid) {
            showAlert("Erreur de saisie", "Veuillez entrer une adresse e-mail valide.");
        }

        return isNbrPlaceValid && isIdUserValid && isIdEventValid && isTelephoneValid && isEmailValid;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Validate that the phone number has exactly 8 digits
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        // Validate that the email address has a valid format
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}