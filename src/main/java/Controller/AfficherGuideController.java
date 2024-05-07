package Controller;

import entities.guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceGuide;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import java.sql.SQLException;
import java.util.List;

import javafx.scene.Node;


public class AfficherGuideController {
    ServiceGuide sp = new ServiceGuide();
    @FXML
    private TableColumn<guide, String> noms;
    @FXML
    private TableColumn<guide, String> prenoms;
    @FXML
    private TableColumn<guide, String> langue_parlees;
    @FXML
    private TableColumn<guide, String> experiences;
    @FXML
    private TableView<guide> tableView;
    @FXML
    private TableColumn<guide, Void> modifyColumn;
    @FXML
    private TableColumn<guide, Integer> idColumn;
    @FXML
    private Label welcomeLBL;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<guide, String> statutColumn;

    ObservableList<guide> observableList;




    @FXML
    void initialize() {
        try {
            List<guide> personneList = sp.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            noms.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenoms.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            langue_parlees.setCellValueFactory(new PropertyValueFactory<>("langue_parlee"));
            experiences.setCellValueFactory(new PropertyValueFactory<>("experience"));
            statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut")); // Configuration de la colonne "Statut"
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        setupModifyColumn();

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleFilterAction(newValue);
        });
    }
    private void handleFilterAction(String filterValue) {
        // Créer une liste temporaire pour stocker les guides filtrés
        ObservableList<guide> filteredList = FXCollections.observableArrayList();

        // Parcourir la liste observableList existante et ajouter les guides qui correspondent au critère de filtrage dans la liste temporaire
        for (guide guide : observableList) {
            if (guide.getNom().toLowerCase().contains(filterValue.toLowerCase())
                    ||guide.getLangue_parlee().toLowerCase().contains(filterValue.toLowerCase())
                    ||guide.getExperience().toLowerCase().contains(filterValue.toLowerCase())
                    || guide.getPrenom().toLowerCase().contains(filterValue.toLowerCase())) {
                filteredList.add(guide);
            }
        }

        // Mettre à jour les données de la TableView avec la liste filtrée
        tableView.setItems(filteredList);
    }

    private void setupModifyColumn() {
        modifyColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modifier");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        guide selectedGuide = getTableView().getItems().get(getIndex());
                        handleModifyAction(selectedGuide);
                    });
                }
            }
        });
    }

    @FXML
    void retournerPageAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guide/ajouterguide.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void handleModifyAction(guide selectedGuide) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guide/modifguide.fxml"));
            Parent root = loader.load();

            ModifierGuideController modifGuideController = loader.getController();
            modifGuideController.setGuide(selectedGuide);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Guide");
            stage.show();

            // Mettre à jour le statut du guide
            selectedGuide.setStatut("Choisi");
            sp.updateStatut(selectedGuide); // Mettre à jour le statut dans la base de données
            tableView.refresh(); // Rafraîchir la TableView pour refléter les changements
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void delete(ActionEvent event) {
        guide p = tableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            try {
                sp.supprimer(p.getId());
                observableList.remove(p);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un guide à supprimer.");
            alert.showAndWait();
        }
    }






    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }
}
