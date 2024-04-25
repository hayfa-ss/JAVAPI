package Controller;

import entities.guide;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceGuide;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class ModifierGuideController {
    @FXML
    private TableView<guide> tableView;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField langueParleeTF;

    @FXML
    private TextField experienceTF;

    private guide selectedGuide;
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
    public void setGuide(guide guide) {
        selectedGuide = guide;
        afficherDetailsGuide();
    }

    private void afficherDetailsGuide() {
        nomTF.setText(selectedGuide.getNom());
        prenomTF.setText(selectedGuide.getPrenom());
        langueParleeTF.setText(selectedGuide.getLangue_parlee());
        experienceTF.setText(selectedGuide.getExperience());
    }

    @FXML
    void sauvegarderModification() {
        String nom = nomTF.getText();
        String prenom = prenomTF.getText();
        String langueParlee = langueParleeTF.getText();
        String experience = experienceTF.getText();
        if (!validateInput(nom)) {
            showAlert("Nom");
            return;
        }

        if (!validateInput(prenom)) {
            showAlert("Prénom");
            return;
        }

        if (!validateInput(langueParlee)) {
            showAlert("Langue parlée");
            return;
        }
        selectedGuide.setNom(nom);
        selectedGuide.setPrenom(prenom);
        selectedGuide.setLangue_parlee(langueParlee);
        selectedGuide.setExperience(experience);

        ServiceGuide serviceGuide = new ServiceGuide();
        try {
            serviceGuide.modifier(selectedGuide);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les modifications ont été sauvegardées avec succès.");
            alert.showAndWait();

            int index = tableView.getItems().indexOf(selectedGuide);
            tableView.getItems().set(index, selectedGuide);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guide/afficherguide.fxml"));
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
