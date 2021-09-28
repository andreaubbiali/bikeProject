package bikeProject.controller;

import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.RackPosition;
import bikeProject.dataservice.TotemRack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller_Fix_Broken_Rack_Position implements Initializable {

    public static TotemRack rack;

    @FXML
    private ComboBox<KeyValuePair> cmbRackPosition;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        loadCmbRack();
    }

    public void loadCmbRack() {
        ObservableList<KeyValuePair> rackPositionChoices = FXCollections.observableArrayList();

        for ( RackPosition rp : rack.getRackPositionList() ) {
            if ( rp.isBroken() ) {
                rackPositionChoices.add(new KeyValuePair(rp, String.valueOf(rp.getID())));
            }
        }

        cmbRackPosition.getItems().setAll(rackPositionChoices);
    }

    @FXML
    void fixRackPosition(ActionEvent event) {
        KeyValuePair selectedCmb = cmbRackPosition.getSelectionModel().getSelectedItem();

        if ( selectedCmb == null ) {
            lblError.setText("Select a rack to fix");
        }

        RackPosition selectedRackPosition = (RackPosition) selectedCmb.getKey();

        try {
            selectedRackPosition.updateIsBroken(false);

            // reload the comboBox
            loadCmbRack();
        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
            return;
        }

    }

    @FXML
    void goBack(ActionEvent event) {
        goBackToPreviousPage(event);
    }

    public void goBackToPreviousPage(ActionEvent event) {
        // go back to previous page
        try {
            Controller_Update_Rack.selectedRackID = rack.getID();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/updateRackPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}