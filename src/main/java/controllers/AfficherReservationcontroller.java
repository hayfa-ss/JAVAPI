/*package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Reservation;
import services.ReservationServices;

import java.sql.SQLException;
import java.util.List;

public class AfficherReservationcontroller {
    private final ReservationServices rs = new ReservationServices();

    @FXML
    private TableView<Reservation> tableView;

    @FXML
    private TableColumn<Reservation, Integer> nbrplacecol;

    @FXML
    private TableColumn<Reservation, Integer> id_usercol;

    @FXML
    private TableColumn<Reservation, Integer> id_eventcol;

    @FXML
    private TableColumn<Reservation, String> date_reservationcol;

    @FXML
    private TableColumn<Reservation, String> emailcol;

    @FXML
    void initialize() {
        try {
            List<Reservation> reservations = rs.recuperer();
            ObservableList<Reservation> observableList = FXCollections.observableList(reservations);
            tableView.setItems(observableList);
            nbrplacecol.setCellValueFactory(new PropertyValueFactory<>("nbrplace"));
            id_usercol.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_eventcol.setCellValueFactory(new PropertyValueFactory<>("id_event"));
            date_reservationcol.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }}
*/