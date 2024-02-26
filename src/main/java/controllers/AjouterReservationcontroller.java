package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import models.Reservation;
import services.ReservationServices;



public class AjouterReservationcontroller {
    private Notificationcontroller notificationController;



    private final ReservationServices rs = new ReservationServices();

    @FXML
    private TableColumn<Reservation, Void> notifyCol;


    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> adresseR;
    @FXML
    private TextField nbrplaceR;

    @FXML
    private TextField id_userR;

    @FXML
    private TextField id_eventR;

    @FXML
    private TextField emailR;
    @FXML
    private TextField telephoneR;
    @FXML
    private TableView<Reservation> tableView;

    @FXML
    private TableColumn<Reservation, Integer> nbrplacecol;

    @FXML
    private TableColumn<Reservation, Integer> id_usercol;

    @FXML
    private TableColumn<Reservation, Integer> id_eventcol;

    @FXML
    private TableColumn<Reservation, String> adressecol;
    @FXML
    private TableColumn<Reservation, String> emailcol;
    @FXML
    private TableColumn<Reservation, Integer> telephonecol;

    @FXML
    private TableColumn<Reservation, Void> deleteTC;

    @FXML
    private TableColumn<Reservation, Void> modifyTC;


    @FXML
    void initialize() {
        try {
            List<Reservation> reservations = rs.recuperer();
            ObservableList<Reservation> observableList = FXCollections.observableList(reservations);
            tableView.setItems(observableList);
            nbrplacecol.setCellValueFactory(new PropertyValueFactory<>("nbrplace"));
            id_usercol.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_eventcol.setCellValueFactory(new PropertyValueFactory<>("id_event"));
            telephonecol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            adressecol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
            configureDeleteColumn();
            configureModifyColumn();
            loadTableData();
            configureNotifyColumn();

        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }


        // Assuming tableView.getItems() returns an ObservableList<Reservation>
        FilteredList<Reservation> filteredData = new FilteredList<>(tableView.getItems(), p -> true);

        // Bind the FilteredList predicate to the search text property
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items if the search field is empty
                }

                // Convert the search input to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if the reservation's nbrplace contains the search input
                return String.valueOf(reservation.getNbrplace()).toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Wrap the FilteredList in a SortedList to enable sorting
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Set the TableView items to the sorted and filtered list
        tableView.setItems(sortedData);
    }


    @FXML
    public void ajouterreservation(ActionEvent actionEvent) {

        try {
            // Contrôles de saisie
            if (nbrplaceR.getText().isEmpty() || id_userR.getText().isEmpty() || id_eventR.getText().isEmpty()
                    || telephoneR.getText().isEmpty() || adresseR.getValue() == null || emailR.getText().isEmpty()) {
                showErrorAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
                return;
            }
            // Contrôle de l'email
            if (!isValidEmail(emailR.getText())) {
                showErrorAlert("Erreur de saisie", "Veuillez saisir une adresse e-mail valide.");
                return;
            }

            // Contrôle du numéro de téléphone
            if (!isValidPhoneNumber(telephoneR.getText())) {
                showErrorAlert("Erreur de saisie", "Veuillez saisir un numéro de téléphone valide (8 chiffres).");
                return;
            }
// Contrôle de l'adresse (example: ne doit pas être vide)
            if (adresseR.getValue() == null || ((String) adresseR.getValue()).isEmpty()) {
                showErrorAlert("Erreur de saisie", "Veuillez saisir une adresse.");
                return;
            }

            Reservation nouvelleReservation = new Reservation(
                    Integer.parseInt(nbrplaceR.getText()),
                    Integer.parseInt(id_userR.getText()),
                    Integer.parseInt(id_eventR.getText()),
                    Integer.parseInt(telephoneR.getText()),
                    (String) adresseR.getValue(),
                    emailR.getText()
            );

            rs.ajouter(nouvelleReservation);
            loadTableData();

        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Veuillez saisir des valeurs numériques valides pour les champs numériques.");
        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }

    }

    // Contrôle de saisie pour les champs numériques
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        // Utiliser une expression régulière simple pour valider l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifier si le numéro de téléphone a exactement 8 chiffres
        return phoneNumber.matches("\\d{8}");
    }

    /**************************************************************************************/

    private void configureDeleteColumn() {
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Reservation reservationToDelete = getTableView().getItems().get(getIndex());
                        try {
                            rs.delete(reservationToDelete.getId());
                            tableView.getItems().remove(reservationToDelete);
                        } catch (SQLException e) {
                            showErrorAlert("Error", e.getMessage());
                        }
                    });
                }
            }
        });
    }


    private void configureModifyColumn() {
        modifyTC.setCellFactory(param -> new TableCell<>() {

            private final Button modifyButton = new Button("Modifier");
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        Reservation reservationToModify = getTableView().getItems().get(getIndex());
                        modifyReservation(reservationToModify);

                    });
                }
            }

            /***************************************************************************************************************************************/

            private void modifyReservation(Reservation reservationToModify) {

                try {
                    // Charger l'interface de modification
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservation.fxml"));
                    Parent root = loader.load();

                    // Passer les données de la borne à modifier au contrôleur de l'interface de modification
                    ModifierReservationcontroller modifiercontroller = loader.getController();
                    modifiercontroller.initData(reservationToModify);

                    // Afficher l'interface de modification
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Modifier Reservation");
                    stage.show();
                } catch (IOException e) {
                    showErrorAlert("Erreur", "Une erreur s'est produite lors de l'ouverture de l'interface de modification.");
                }
            }

        });
    }
    // Show an error alert with the given title and message
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*********************************************************************************************************/


    private void loadTableData() throws SQLException {
        // Read all  from the service
        List<Reservation> reservations =rs.recuperer();

        // Add the  to the TableView
        tableView.getItems().addAll(reservations);
    }


    public void naviguezVersAffichage(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterReservation.fxml"));
            adresseR.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguezVersNotification(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Notification.fxml"));
            adresseR.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**************************************************************************************************/
    @FXML
    void imprimer(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            if (job.showPrintDialog(tableView.getScene().getWindow())) {
                print(job, tableView);
                job.endJob();
            }
        }
    }

    private void print(PrinterJob job, Node node) {
        job.printPage(node);


    }


    /***********************************************************/
    private void configureNotifyColumn() {
        notifyCol.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setText("Notifier");
                    setOnMouseClicked(event -> {
                        Reservation reservationToNotify = getTableView().getItems().get(getIndex());
                        notifyWithReservationId(reservationToNotify.getId());
                    });
                }
            }
        });
    }

   /******************************************************************/

   private void notifyWithReservationId(int reservationId) {
       // Afficher un message avec l'ID de réservation
       System.out.println("Notifié avec l'ID de réservation : " + reservationId);

       // Passer à la vue de Notification.fxml
       navigateToNotificationView(reservationId);
   }

    private void navigateToNotificationView(int reservationId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Notification.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de Notificationcontroller
            Notificationcontroller notificationController = loader.getController();

            // Appeler la méthode pour initialiser les données avec l'ID de réservation
            notificationController.initDataWithReservationId(reservationId);

            // Afficher la vue de Notification.fxml
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Notification");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*************************************************************************/


    @FXML
    void searchReservation(ActionEvent event) {
        // Additional search logic or actions to perform when the user clicks "Rechercher"
        System.out.println("Search button clicked!");

        // You can retrieve the search query from the searchField
        String searchQuery = searchField.getText();
        System.out.println("Search Query: " + searchQuery);

        // You might want to update or refresh other parts of your UI or perform specific actions here
    }


    public void EcoModeButton(ActionEvent actionEvent) {
    }
}




