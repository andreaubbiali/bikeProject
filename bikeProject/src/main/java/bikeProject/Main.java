package bikeProject;

import bikeProject.config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Ricordati che per far partire il controller devi aggiungere
 * import javafx.event.ActionEvent;
 * DOVE METTERE LA CLOSE DELLA DB CONNECTION?
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagePanel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("start of the application");

        try {
            Application.launch(args);
        } catch ( Exception e ) {
            System.out.println(e);
        }
    }

}