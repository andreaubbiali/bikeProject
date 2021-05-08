package bikeProject;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
//import java.sql.*;

/*
 * Ricordati che per far partire il controller devi aggiungere 
 * import javafx.event.ActionEvent;
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	
    	URL url = getClass().getResource("/bikeProject/loginPanel.fxml");
    	try {
    		Parent root = FXMLLoader.load(url);
    		System.out.println("caricato");
        	stage.setScene(new Scene(root, 400, 300));
        	stage.show();
    	}catch(Exception e) {
    		System.out.println("entra qua   ");
    		e.printStackTrace();
    	}
    	
    }

    public static void main(String[] args) {
//    	try {
//    		System.out.println("connessione con il db");
//    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bikeProject?user=root&password=aeri2aicee5Oyo1euQuai7niexa8choo");
//    		System.out.println("conenssione eseguita");
//    		System.out.println(conn);
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    	}
//    	
//    	return;
    	try {
    		Application.launch(args);
    	}catch(Exception e) {
    		System.out.println(e);
    	}

    }

}