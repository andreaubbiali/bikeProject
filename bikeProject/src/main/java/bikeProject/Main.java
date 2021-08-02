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
        System.out.println("ARRIVA");
        try{
            // Se vuoi usare la cartella panels nel path di bikeProject devi mettere gli fxml li e usare queste stringhe:
            /*URL url = Paths.get("src/main/java/bikeProject/panels/loginPanel.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);*/
            // getResource punta alle resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPanel.fxml"));
            System.out.println("PROBLEMA");
            Parent root = loader.load();
            System.out.println("qua");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }


//		// to mantain the state throught different panels
//
//		FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("/bikeProject/panels/loginPanel.fxml"));
//		Parent firstPane = firstPaneLoader.load();
//		Scene firstScene = new Scene(firstPane);
//
////		// getting loader and a pane for the second scene
////		FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("/bikeProject/panels/provaPanel.fxml"));
////		Parent secondPane = secondPageLoader.load();
////		Scene secondScene = new Scene(secondPane);
//
//		// injecting second scene into the controller of the first scene
//		bikeProject.controller.Controller firstPaneController = (bikeProject.controller.Controller) firstPaneLoader
//				.getController();
//		firstPaneController.setSecondScene(secondScene);
//
//		// injecting first scene into the controller of the second scene
//		bikeProject.controller.ControllerProva secondPaneController = (bikeProject.controller.ControllerProva) secondPageLoader
//				.getController();
//		secondPaneController.setFirstScene(firstScene);
//
//		primaryStage.setTitle("Switching scenes");
//		primaryStage.setScene(firstScene);
//		primaryStage.show();

    }

    public static void main(String[] args) {
        System.out.println("start of the application");
        Config tmp = Config.getInstance();

        try {
            Application.launch(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    }*/

    /*requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;*/

    /*opens org.openjfx to javafx.fxml;
    exports org.openjfx;*/
    // opens bikeProject to javafx.fxml;
    //opens bikeProject.controller to javafx.fxml;
    //exports bikeProject;

}