package controllers;

import entities.Payment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import services.ServicePersonne;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javafx.scene.image.Image;

public class AfficherPersonnesController {
    ServicePersonne sp = new ServicePersonne();

    @FXML
    private TableColumn<Payment, Integer> Amount;

    @FXML
    private TableColumn<Payment, Date> Date;

    @FXML
    private TableColumn<Payment,String> Email;

    @FXML
    private TableColumn<Payment, String> LastName;

    @FXML
    private TableColumn<Payment, String> PaymentOp;


    @FXML
    private TableColumn<Payment,String> firstName;
    @FXML
    private TableColumn<Payment, Integer> ID;

    @FXML
    private TableView<Payment> tableView;
    @FXML
    private Label welcomeLBL;
    ObservableList<Payment> observableList;

    Payment selectedPayment;

///info payment**

    @FXML
    private Label Amountinfo;

    @FXML
    private Label Dateinfo;

    @FXML
    private Label Emailinfo;

    @FXML
    private Label LastNameinfo;

    @FXML
    private Label PaymentOpinfo;

    @FXML
    private Label firstNameinfo;

    @FXML
    private Label idinfo;


//end info payment

// modifinfo


    @FXML
    private TextField mamount;



    @FXML
    private TextField memail;

    @FXML
    private TextField mfirstname;

    @FXML
    private TextField mid;

    @FXML
    private TextField mlastname;


    @FXML
    private TextField mpaymentop;

    @FXML
    private DatePicker mdate;
    @FXML
    private TextField rechercher;


    ///endmodif


    @FXML
    private Pane modifypane;
    @FXML
    void initialize() {
        try {
            List<Payment> personneList = sp.afficher();
            observableList = FXCollections.observableList(personneList);

            tableView.setItems(observableList);

            ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            Email.setCellValueFactory(new PropertyValueFactory<>("email"));
            PaymentOp.setCellValueFactory(new PropertyValueFactory<>("paymentOptions"));
            Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            FilteredList<Payment> filteredData = new FilteredList<>(observableList, p -> true);
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedPayment = newValue;

                    Amountinfo.setText(String.valueOf(selectedPayment.getAmount()));
                    Dateinfo.setText(selectedPayment.getDate().toString());
                    Emailinfo.setText(selectedPayment.getEmail());
                    LastNameinfo.setText(selectedPayment.getLastName());
                    PaymentOpinfo.setText(selectedPayment.getPaymentOptions());
                    firstNameinfo.setText(selectedPayment.getFirstName());
                    idinfo.setText(String.valueOf(selectedPayment.getId()));

                    mamount.setText(String.valueOf(selectedPayment.getAmount()));
                    LocalDate initialDate = LocalDate.of(selectedPayment.getDate().getYear(), selectedPayment.getDate().getMonth(), selectedPayment.getDate().getDay());
                    mdate.setValue(initialDate);
                    memail.setText(selectedPayment.getEmail());
                    mid.setText(String.valueOf(selectedPayment.getId()));
                    mfirstname.setText(selectedPayment.getFirstName());
                    mlastname.setText(selectedPayment.getLastName());
                    mpaymentop.setText(selectedPayment.getPaymentOptions());
                }
            });
            rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(payment -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return String.valueOf(payment.getId()).toLowerCase().contains(lowerCaseFilter)
                            || payment.getFirstName().toLowerCase().contains(lowerCaseFilter)
                            || payment.getLastName().toLowerCase().contains(lowerCaseFilter)
                            || payment.getEmail().toLowerCase().contains(lowerCaseFilter)
                            || payment.getPaymentOptions().toLowerCase().contains(lowerCaseFilter);
                });
            });


           /* rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(payment -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return payment.getFirstName().toLowerCase().contains(lowerCaseFilter)
                            || payment.getLastName().toLowerCase().contains(lowerCaseFilter)
                            || payment.getEmail().toLowerCase().contains(lowerCaseFilter)
                            || payment.getPaymentOptions().toLowerCase().contains(lowerCaseFilter);

                });
            });*/

            SortedList<Payment> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        try {
            Payment selectedPayment = tableView.getSelectionModel().getSelectedItem();
            if (selectedPayment != null) {
                sp.supprimer(selectedPayment.getId());
                observableList.remove(selectedPayment);
            } else {
                System.err.println("none is selected.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }

    @FXML
    void btnop(ActionEvent event) {
        modifypane.setVisible(true);
    }

    @FXML
    void cancelmodif(ActionEvent event) {
        modifypane.setVisible(false);
    }


    @FXML
    void savemodify(ActionEvent event) {
        try {
             selectedPayment = tableView.getSelectionModel().getSelectedItem();
            if (selectedPayment != null) {
                selectedPayment.setFirstName(mfirstname.getText());
                selectedPayment.setLastName(mlastname.getText());
                selectedPayment.setEmail(memail.getText());
                selectedPayment.setPaymentOptions(mpaymentop.getText());
                selectedPayment.setAmount(Integer.parseInt(mamount.getText()));
                LocalDate selectedDate = mdate.getValue();
               selectedPayment.setDate(java.sql.Date.valueOf(selectedDate));
                sp.modifier(selectedPayment);

                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                observableList.set(selectedIndex, selectedPayment);
                modifypane.setVisible(false);
            } else {
                System.err.println("none is selected.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



}


