package bikeProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

/*
 * Ricordati che per far partire il controller devi aggiungere 
 * import javafx.event.ActionEvent;
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/loginPanel.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    	
    }

    public static void main(String[] args) {
    	
    	try {
    		Application.launch(args);
    	}catch(Exception e) {
    		System.out.println(e);
    	}

    }

}