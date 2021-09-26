package bikeProject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller_Manage {

    @FXML
    private ComboBox<?> cmbManageRack;

    @FXML
    private Label lblError;

    @FXML
    void addRack(ActionEvent event) {

        // go to next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/newRackPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    void manageRack(ActionEvent event) {

    }
}