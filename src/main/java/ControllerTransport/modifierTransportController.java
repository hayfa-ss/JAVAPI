package ControllerTransport;

import entities.Transports;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceTransports;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class modifierTransportController {
    @FXML
    private TableView<Transports> tableView;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField statutTF;

    @FXML
    private TextField capaciteTF;

    @FXML
    private TextField experienceTF;

    private Transports selectedTransport;

    private boolean validateInput(String input) {
        // Expression régulière pour vérifier si la chaîne contient uniquement des lettres et des espaces
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, input);
    }
    private void showAlert(String fieldName) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le champ " + fieldName + " ne peut contenir que des lettres et des espaces.");
        alert.showAndWait();
    }
    public void setTransport(Transports transport) {
        selectedTransport = transport;
        afficherDetailsTransport();
    }

    private void afficherDetailsTransport() {
        typeTF.setText(selectedTransport.getType());
        capaciteTF.setText(String.valueOf(selectedTransport.getCapacite()));
        statutTF.setText(selectedTransport.getStatut());
    }

    @FXML
    void sauvegarderModification() {
        String type = typeTF.getText();
        String statut = statutTF.getText();
        int capacite = Integer.parseInt(capaciteTF.getText());
        if (!validateInput(type)) {
            showAlert("type");
            return;
        }

        if (!validateInput(statut)) {
            showAlert("statut");
            return;
        }


        selectedTransport.setType(type);
        selectedTransport.setStatut(statut);
        selectedTransport.setCapacite(capacite);

        ServiceTransports serviceTransport = new ServiceTransports();
        try {
            serviceTransport.modifier(selectedTransport);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les modifications ont été sauvegardées avec succès.");
            alert.showAndWait();

            int index = tableView.getItems().indexOf(selectedTransport);
            tableView.getItems().set(index, selectedTransport);
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
            // Charger le fichier FXML de l'affichage du tableau
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/afficherTransport.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher le tableau modifié
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Affichage du Tableau");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs lors du chargement du fichier FXML
            // Vous pouvez afficher une boîte de dialogue d'erreur ou simplement imprimer le message d'erreur
        }
    }



}
