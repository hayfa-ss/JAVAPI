package controllers ;

import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceEvenement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AjouterEvenementController {

    @FXML
    private TextField date_departTF;

    @FXML
    private TextField date_finTF;

    @FXML
    private TextField heureTF;

    @FXML
    private TextField lieuTF;

    @FXML
    private TextField typeTF;
    private boolean update;

    @FXML
    void InsererEvenement(ActionEvent event) {
        String dateDepartText = date_departTF.getText();
        String dateFinText = date_finTF.getText();
        String heureText = heureTF.getText();
        LocalDate date_depart = LocalDate.parse(dateDepartText);
        LocalDate date_fin = LocalDate.parse(dateFinText);
        LocalTime heure = LocalTime.parse(heureText);

        if (date_depart.getYear() < 2024 || date_fin.getYear() < 2024)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Les dates doivent commencer par '2024'");
            alert.show();
            return; // Sortie de la méthode pour éviter d'exécuter le reste du code
        }
        if (date_fin.isBefore(date_depart)) {
//                throw new DateTimeParseException("La date de fin doit être la même que la date de début ou postérieure", dateFinText, 0);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date de fin doit être la même que la date de début ou postérieure");
            alert.show();
            return;
        }
        try {


            String lieu = lieuTF.getText();
            String type = typeTF.getText();

            Evenement evenement = new Evenement(date_depart, date_fin, heure, lieu, type);

            ServiceEvenement serviceEvenement = new ServiceEvenement();
            serviceEvenement.ajouter(evenement);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement inséré avec succès");
            alert.show();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Format de date invalide. Assurez-vous que les dates sont au format 'yyyy-MM-dd' et commencent par '2024'.");
            alert.show();
        } catch (DateTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date de fin doit être la même que la date de début ou postérieure.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de l'insertion de l'événement : " + e.getMessage());
            alert.show();
        }
    }



    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root = loader.load();
            AfficherEvenementController afficherEvenementController = loader.getController();

            afficherEvenementController.setData(lieuTF.getText());

            lieuTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setFields(int id, LocalDate dateDepart, LocalDate dateFin, LocalTime heure, String lieu, String type) {

    }
}
