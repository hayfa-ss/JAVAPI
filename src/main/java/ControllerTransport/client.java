package ControllerTransport;

import entities.Transports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceTransports;

import java.sql.SQLException;
import java.util.List;

public class client {

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
    private TableColumn<Transports, Integer> idColumn;

    @FXML
    private TableColumn<Transports, String> choisirColumn;

    @FXML
    private Label welcomeLBL;
    @FXML
    private TableColumn<Transports, Void> voirGuideColumn;


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

            // Ajouter une colonne "Choisir" avec un bouton pour chaque ligne
            choisirColumn.setCellFactory(column -> {
                return new TableCell<>() {
                    final Button button = new Button("Choisir");

                    {
                        button.setOnAction(event -> {
                            Transports rowData = getTableView().getItems().get(getIndex());
                            handleChoisirAction(rowData);
                        });
                    }


                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Action lorsque le bouton "Choisir" est cliqué
    private void handleChoisirAction(Transports transport) {
        System.out.println("Transport choisi : " + transport.getType() + ", Statut : " + transport.getStatut());
        // Mettre à jour le statut du transport
        transport.setStatut("Choisi");
        // Mettre à jour le statut dans la base de données
        try {
            sp.modifierStatut(transport.getId(), "Choisi");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut dans la base de données : " + e.getMessage());
        }
        // Rafraîchir la table pour refléter les changements
        tableView.refresh();
        // Afficher un message de succès
        showAlert("Succès", "Transport choisi avec succès : " + transport.getType());
    }


    // Afficher une boîte de dialogue avec un message donné
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }
}
