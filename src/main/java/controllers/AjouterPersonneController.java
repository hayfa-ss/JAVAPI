package controllers;

import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonneController {

    @FXML
    private TextField ageTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    void InsererPersonne(ActionEvent event) {
        String nom = nomTF.getText();
        String prenom = prenomTF.getText();
        int age = Integer.parseInt(ageTF.getText());
        Personne p = new Personne(age, nom, prenom);
        ServicePersonne sp = new ServicePersonne();
        try {
            sp.ajouter(p);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Personne insérée avec succéss");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guide/AfficherPersonnes.fxml"));
            Parent root = loader.load();
            AfficherPersonnesController apc = loader.getController();
            apc.setData(nomTF.getText());
            ageTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
