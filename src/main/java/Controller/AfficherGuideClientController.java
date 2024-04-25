package Controller;

import entities.guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceGuide;
import java.sql.SQLException;
import java.util.List;

public class AfficherGuideClientController {
    ServiceGuide sp = new ServiceGuide();

    @FXML
    private TableView<guide> tableView;

    @FXML
    private TableColumn<guide, String> nomColumn;

    @FXML
    private TableColumn<guide, String> prenomColumn;

    @FXML
    private TableColumn<guide, String> langueParleeColumn;

    @FXML
    private TableColumn<guide, String> experienceColumn;

    @FXML
    private TableColumn<guide, Button> actionColumn;


    ObservableList<guide> observableList;


    @FXML
    void initialize() {

        try {
            List<guide> personneList = sp.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);


            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            langueParleeColumn.setCellValueFactory(new PropertyValueFactory<>("langue_parlee"));
            experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        actionColumn.setCellFactory(column -> {
            return new TableCell<guide, Button>() {
                final Button button = new Button("Choisir");

                {
                    button.setOnAction(event -> {
                        guide rowData = getTableView().getItems().get(getIndex());
                        showAlert("Guide choisi", "Vous avez choisi : " + rowData.getNom() + " " + rowData.getPrenom());
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
}
}
