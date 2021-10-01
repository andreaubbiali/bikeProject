package bikeProject.controller;

import bikeProject.dataservice.TotemRack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Statistics_Data implements Initializable {

    @FXML
    private Label lblNumberActiveSubscription;

    @FXML
    private Label lblNumberActiveRent;

    @FXML
    private Label lblNumberUser;

    @FXML
    private Label lblAverageUseBike;

    @FXML
    private Label lblMostlyBikeUsed;

    @FXML
    private Label lblRackMostUsed;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    void goBackHome(ActionEvent event) {

        // open homePage panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/homePagePanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}