package controllers;

import entities.Evenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEvenement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class ModifierEvenementController {
    @FXML
    private TableColumn<Evenement, LocalDate> date_departCol;
    @FXML
    private TableColumn<Evenement, LocalDate> date_finCol;
    @FXML
    private TableColumn<Evenement, LocalTime> heureCol;
    @FXML
    private TableColumn<Evenement, String> lieuCol;
    @FXML
    private TableColumn<Evenement, String> typeCol;
    @FXML
    private TableView<Evenement> tableView;

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

    private Evenement selectedEvenement;



    private void showAlert(String fieldName) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le champ " + fieldName + " ne peut contenir que des lettres et des espaces.");
        alert.showAndWait();
    }

    public void setEvenement(Evenement evenement) {
        selectedEvenement = evenement;
        afficherDetailsEvenement();
    }

    private void afficherDetailsEvenement() {
        date_departTF.setText(selectedEvenement.getDate_depart().toString());
        date_finTF.setText(selectedEvenement.getDate_fin().toString());
        heureTF.setText(selectedEvenement.getHeure().toString());
        lieuTF.setText(selectedEvenement.getLieu());
        typeTF.setText(selectedEvenement.getType());
    }

    @FXML
    void sauvegarderModification() {
        String date_depart = date_departTF.getText();
        String date_fin = date_finTF.getText();
        String heure = heureTF.getText();
        String lieu = lieuTF.getText();
        String type = typeTF.getText();

        // Votre validation de saisie ici...

        selectedEvenement.setDate_depart(LocalDate.parse(date_depart));
        selectedEvenement.setDate_fin(LocalDate.parse(date_fin));
        selectedEvenement.setHeure(LocalTime.parse(heure));
        selectedEvenement.setLieu(lieu);
        selectedEvenement.setType(type);

        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            serviceEvenement.modifier(selectedEvenement);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les modifications ont été sauvegardées avec succès.");
            alert.showAndWait();

            int index = tableView.getItems().indexOf(selectedEvenement);
            tableView.getItems().set(index, selectedEvenement);
            tableView.refresh(); // Forcer la mise à jour de la TableView
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la sauvegarde des modifications. Veuillez réessayer.");
            alert.showAndWait();
        }
    }

    @FXML
    void afficherTableau() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/afficherevenement.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Affichage du Tableau");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
