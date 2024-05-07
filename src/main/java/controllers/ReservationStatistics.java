package controllers;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.ReservationServices;

import java.util.List;

public class ReservationStatistics extends Application {

    @FXML
    private BarChart<String, Number> barChart;

    @Override
    public void start(Stage stage) throws Exception {
        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/chemin/vers/ReservationStatistics.fxml"));

        // Créer la scène
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Statistiques des Réservations");

        // Afficher la fenêtre
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void loadStatistics(ActionEvent actionEvent) {
        // Récupérer les statistiques des réservations par nombre de places
        ReservationServices reservationServices = new ReservationServices();
        List<Integer> stats = reservationServices.getReservationStatsByPlace();

        // Obtenir la série de données du graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre de Réservations");

        // Remplir les données de la série
        for (int i = 0; i < stats.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), stats.get(i)));
        }

        // Ajouter la série au graphique à barres
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
