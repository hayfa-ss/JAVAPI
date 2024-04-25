/*
    ServiceTransports sp = new ServiceTransports();

    @FXML
    private TableView<Transports> tableView;

    @FXML
    private TableColumn<Transports, String> types;
    @FXML
    private TableColumn<Transports, String> statuts;
    @FXML
    private TableColumn<Transports, Integer> capaciteé;

    @FXML
    private TableColumn<Transports, Button> actionColumn;


    ObservableList<Transports> observableList;


    @FXML
    void initialize() {

        try {
            List<Transports> personneList = sp.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);


            types.setCellValueFactory(new PropertyValueFactory<>("types"));
            capaciteé.setCellValueFactory(new PropertyValueFactory<>("capaciteé"));
            statuts.setCellValueFactory(new PropertyValueFactory<>("statuts"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        actionColumn.setCellFactory(column -> {
            return new TableCell<Transports, Button>() {
                final Button button = new Button("Choisir");

                {
                    button.setOnAction(event -> {
                        Transports rowData = getTableView().getItems().get(getIndex());
                        showAlert("Guide choisi", "Vous avez choisi : " + rowData.getType() + " " + rowData.getStatut());
                    });
                }
                private void showAlert(String title, String content) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(content);
                    alert.showAndWait();
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });
    }*/

package ControllerTransport;

import ControllerTransport.modifierTransportController;
import entities.Transports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceTransports;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherTransportController {

    ServiceTransports sp = new ServiceTransports();
    @FXML
    private TableColumn<Transports, String> types;
    @FXML
    private TableColumn<Transports, String> statuts;
    @FXML
    private TableColumn<Transports, Integer> capaciteé;

    @FXML
    private TableView<Transports> tableView;
    @FXML
    private TableView<Transports> tableVieww;
    @FXML
    private TableColumn<Transports, Void> modifyColumn;
    @FXML
    private TableColumn<Transports, Integer> idColumn;
    @FXML
    private Label welcomeLBL;
    ObservableList<Transports> observableList;



    @FXML
    void initialize() {

        try {
            List<Transports> Transport_List = sp.afficher();
            observableList = FXCollections.observableList(Transport_List);

            tableView.setItems(observableList);

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            types.setCellValueFactory(new PropertyValueFactory<>("type"));
            statuts.setCellValueFactory(new PropertyValueFactory<>("statut"));
            capaciteé.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        setupModifyColumn();
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
                        Transports selectedTransport = getTableView().getItems().get(getIndex());

                        handleModifyAction(selectedTransport);
                    });
                }
            }
        });
    }
    @FXML
    void retournerPageAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/ajouterTransport.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void handleModifyAction(Transports selectedTransport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transport/modifierTransport.fxml"));
            Parent root = loader.load();

            modifierTransportController modiftransportController = loader.getController();

            modiftransportController.setTransport(selectedTransport);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Guide");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @FXML
    void delete(ActionEvent event) {
        Transports p = tableView.getSelectionModel().getSelectedItem();
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
            alert.setContentText("Veuillez sélectionner un transport à supprimer.");
            alert.showAndWait();
        }
    }



    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }
}
