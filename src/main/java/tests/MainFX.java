//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tests;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    public MainFX() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        try {                                                               /*/guide/afficherGuideClient*/
                                                                            /*/guide/afficherguide*/
                                                                            /*/transport/afficherTransport*/
                                                                            /*/transport/ajouterTransport*/

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/transport/ajouterTransport.fxml"));

            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gérer guide");
            primaryStage.show();
        } catch (IOException var5) {
            System.err.println(var5.getMessage());
        }

    }

}
