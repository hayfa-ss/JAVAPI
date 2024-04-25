package controllers;

import entities.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;


public class AjouterPersonneController {


    @FXML
    private TextField ageTF;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check21;
    @FXML
    private Pane pId;

    @FXML
    private TextField emailFt;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private Label iamount;

    @FXML
    private Label idate;

    @FXML
    private Label iemail;

    @FXML
    private Label ifirstname;

    @FXML
    private Label ilastname;

    @FXML
    private Label ipayment;

    @FXML
    private Pane invoicePan;




    @FXML
    void InsererPersonne(ActionEvent event) {
        // Récupération des valeurs des champs
        String firstName = nomTF.getText();
        String lastName = prenomTF.getText();
        String inputEmail = emailFt.getText();


        String paymentOptions = "";
        if (check2.isSelected()) {

            check21.setSelected(false);
            pId.setVisible(true);
        } else if (check21.isSelected()) {
            paymentOptions = "Cash";
            check2.setSelected(true);
            pId.setVisible(false);

        }


        int amount = 0;
        String amountText = ageTF.getText();
        boolean amountValid = isNumeric(amountText) && Integer.parseInt(amountText) > 0;
        if (amountValid) {
            amount = Integer.parseInt(amountText);
        }

        if (validateFields() && amountValid) {
            Date aDate = new Date();
            Payment p = new Payment(firstName, lastName, inputEmail, paymentOptions, aDate, amount);
            ServicePersonne sp = new ServicePersonne();
            try {
                sp.ajouter2(p);
                iamount.setText(String.valueOf(amount));
                idate.setText(inputEmail);
                iemail.setText(inputEmail);
                ifirstname.setText(firstName);
                ilastname.setText(lastName);
                ipayment.setText(paymentOptions);
                invoicePan.setVisible(true);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Congratulation! Your payment has been successfully processed . This is your Invoice ");
                alert.show();
                paneonline.setVisible(false);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields correctly and ensure that the amount is a positive number.");
            alert.showAndWait();
        }
    }

    private boolean validateFields() {
        // Vérification de la validité des champs et mise en surbrillance des champs vides ou invalides
        boolean allFieldsFilled = true;

        if (nomTF.getText().isEmpty()) {
            allFieldsFilled = false;
            nomTF.setStyle("-fx-border-color: red;");
        } else {
            nomTF.setStyle("");
        }

        if (prenomTF.getText().isEmpty()) {
            allFieldsFilled = false;
            prenomTF.setStyle("-fx-border-color: red;");
        } else {
            prenomTF.setStyle("");
        }

       if (emailFt.getText().isEmpty()) {
            allFieldsFilled = false;
            emailFt.setStyle("-fx-border-color: red;");
        } else {
            emailFt
                    .setStyle("");
        }

        if (!(check2.isSelected() || check21.isSelected())) {
            allFieldsFilled = false;
            check2.setStyle("-fx-border-color: red;");
            check21.setStyle("-fx-border-color: red;");
        } else {
            check2.setStyle("");
            check21.setStyle("");
        }

        if (!isNumeric(ageTF.getText()) || Integer.parseInt(ageTF.getText()) <= 0) {
            allFieldsFilled = false;
            ageTF.setStyle("-fx-border-color: red;");
        } else {
            ageTF.setStyle("");
        }

        return allFieldsFilled;
    }

    @FXML
    void naviguez(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonnes.fxml"));
            Parent root = loader.load();
            AfficherPersonnesController apc = loader.getController();
            apc.setData(nomTF.getText());
            ageTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    private Pane paneonline;

    @FXML
    void visfalse(ActionEvent event) {
        paneonline.setVisible(false);
        if (check2.isSelected()) {
            check21.setSelected(false);
        }
    }

    @FXML
    void vistrue(ActionEvent event) {
        if (check21.isSelected()) {
            pId.setVisible(false); // Hide the pane when "Cash" checkbox is selected
            check2.setSelected(false); // Deselect the "Online" checkbox
        } else {
            pId.setVisible(true); // Show the pane otherwise
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}

