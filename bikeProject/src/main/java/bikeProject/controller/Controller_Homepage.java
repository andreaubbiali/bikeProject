package bikeProject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller_Homepage {

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnLogin;

    @FXML
    void login(ActionEvent event) {

        // open login panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/loginPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    void register(ActionEvent event) {

        // open register panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/registerNewUserPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}