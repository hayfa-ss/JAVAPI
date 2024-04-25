package Controller;

import entities.guide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceGuide;


import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AjouterGuideController {



    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;
    @FXML
    private TextField langue_parleeTF;
    @FXML
    private TextField experienceTF;

    public AjouterGuideController() {
    }
    private boolean validerSaisie(String saisie) {
        return !saisie.isEmpty() && Pattern.matches("[a-zA-Z ]+", saisie);
    }


    @FXML
    void InsererPersonne(ActionEvent event) {

            String nom = nomTF.getText();
            String prenom = prenomTF.getText();
            String langueParlee = langue_parleeTF.getText();
            String experience = experienceTF.getText();

        if (!validerSaisie(nom)) {
            afficherMessageErreur("Le nom est invalide.");
            return;
        }

        if (!validerSaisie(prenom)) {
            afficherMessageErreur("Le prénom est invalide.");
            return;
        }

        if (!validerSaisie(langueParlee)) {
            afficherMessageErreur("La langue parlée est invalide.");
            return;
        }
            if (nom.isEmpty() || prenom.isEmpty() || langueParlee.isEmpty() || experienceTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            guide nouveauGuide = new guide(nom, prenom, langueParlee, experience);

            ServiceGuide serviceGuide = new ServiceGuide();
            try {
                serviceGuide.ajouter(nouveauGuide);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le guide a été ajouté avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de l'ajout du guide. Veuillez réessayer.");
                alert.showAndWait();
            }
        }
    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guide/afficherguide.fxml"));
            Parent root = loader.load();

            AfficherGuideController controller = loader.getController();



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







