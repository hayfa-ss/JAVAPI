package controllers;

import entities.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServicePersonne;

import java.sql.SQLException;
import java.util.List;

public class AfficherPersonnesController {
    ServicePersonne sp = new ServicePersonne();
    @FXML
    private TableColumn<Personne, Integer> ageCol;
    @FXML
    private TableColumn<Personne, String> nomCol;
    @FXML
    private TableColumn<Personne, String> prenomCol;
    @FXML
    private TableView<Personne> tableView;
    @FXML
    private Label welcomeLBL;
    ObservableList<Personne> observableList;
    @FXML
    void initialize() {

        try {
            List<Personne> personneList = sp.afficher();
             observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        try {
            Personne p = tableView.getSelectionModel().getSelectedItem();
            sp.supprimer(p.getId());
            observableList.remove(p);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }
}
