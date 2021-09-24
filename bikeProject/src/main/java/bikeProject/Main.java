package bikeProject;

import bikeProject.config.Config;
import bikeProject.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/*
 * Ricordati che per far partire il controller devi aggiungere
 * import javafx.event.ActionEvent;
 *
 * da aggiungere se vuoi usare libreria scaricata da te e non maven
 * --module-path
/etc/javafx-sdk-11.0.2/lib
--add-modules
javafx.controls,javafx.fxml
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
            // start the database
            Database.getInstance();

            // read the config file
            Config.getInstance();

            Application.launch(args);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {

            // close db connection
            try {
                Database.closeConnection();
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
    }

}