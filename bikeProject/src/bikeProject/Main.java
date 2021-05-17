package bikeProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Ricordati che per far partire il controller devi aggiungere 
 * import javafx.event.ActionEvent;
 * DOVE METTERE LA CLOSE DELLA DB CONNECTION?
 */

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/panels/loginPanel.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

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

		try {
			Application.launch(args);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}