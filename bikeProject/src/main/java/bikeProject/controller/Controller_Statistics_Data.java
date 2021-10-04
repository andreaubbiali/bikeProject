package bikeProject.controller;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.Rent;
import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.User;
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
import java.sql.SQLException;
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
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try {
            lblNumberActiveSubscription.setText(String.valueOf(Subscription.getActiveSubscriptionCount()));
            lblNumberActiveRent.setText(String.valueOf(Rent.getActiveRentCount()));
            lblNumberUser.setText(String.valueOf(User.getNumberUserCount()));
            lblAverageUseBike.setText(String.valueOf(Rent.getAverageUseBike()));
            lblMostlyBikeUsed.setText(Bike.getMostlyUsedBikeType());
        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR.");
            sql.printStackTrace();
        }

    }

    @FXML
    void goBackHome(ActionEvent event) {

        // open homePage panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/managePanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}