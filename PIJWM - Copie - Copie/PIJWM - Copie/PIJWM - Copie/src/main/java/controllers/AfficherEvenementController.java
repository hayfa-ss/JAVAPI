
package controllers;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceEvenement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AfficherEvenementController {
    ServiceEvenement se = new ServiceEvenement();
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
    private Label welcomeLBL;
    ObservableList<Evenement> observableList;

    @FXML
    void initialize() {

        try {
            List<Evenement> personneList = se.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);

            date_departCol.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
            date_finCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            heureCol.setCellValueFactory(new PropertyValueFactory<>("heure"));
            lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
@FXML

void delete(ActionEvent event) {
    Evenement evenement = tableView.getSelectionModel().getSelectedItem();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

    ButtonType buttonTypeOK = new ButtonType("OK");
    ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

    if (result == buttonTypeOK) {
        try {
            se.supprimer(evenement.getId());
            observableList.remove(evenement);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}





//                @FXML
//                void ModifierEvenement(ActionEvent event) {
//                   try {
//                    Evenement E = tableView.getSelectionModel().getSelectedItem();
//                      se.modifier(E);
//                      observableList.remove(E);
//                 } catch (SQLException e) {
//                       System.err.println(e.getMessage());
//                   }
//               }
//

                void setData(String param) {
                    welcomeLBL.setText("Mes Evénements " + param);
                }}







