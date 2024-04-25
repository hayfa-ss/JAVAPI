package ControllerTransport;

import ControllerTransport.AfficherTransportController;
import entities.Transports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceTransports;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AjouterTransportController {



    @FXML
    private TextField typeTF;

    @FXML
    private TextField capaciteTF;
    @FXML
    private TextField statutTF;

    public AjouterTransportController() {
    }
    private boolean validerSaisie(String saisie) {
        return !saisie.isEmpty() && Pattern.matches("[a-zA-Z ]+", saisie);
    }


    @FXML
    void InsererTransport(ActionEvent event) {
        String type = typeTF.getText();
        String statut = statutTF.getText();
        String capaciteText = capaciteTF.getText();

        if (type.isEmpty() || statut.isEmpty() || capaciteText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capaciteText);
        } catch (NumberFormatException e) {
            afficherMessageErreur("La capacité doit être un nombre entier.");
            return;
        }

        if (!validerSaisie(type)) {
            afficherMessageErreur("Le type est invalide.");
            return;
        }

        if (!validerSaisie(statut)) {
            afficherMessageErreur("La statut est invalide.");
            return;
        }

        Transports nouveauTransport = new Transports(type, statut, capacite);

        ServiceTransports serviceTransports = new ServiceTransports();
        try {
            serviceTransports.ajouter(nouveauTransport);
            afficherMessageInformation("Le transport a été ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherMessageErreur("Une erreur est survenue lors de l'ajout du transport. Veuillez réessayer.");
        }
    }

    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/afficherTransport.fxml"));
            Parent root = loader.load();

            AfficherTransportController controller = loader.getController();



            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void afficherMessageInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}