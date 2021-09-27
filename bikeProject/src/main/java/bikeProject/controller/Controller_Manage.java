package bikeProject.controller;

import bikeProject.dataservice.RackPosition;
import bikeProject.dataservice.SubscriptionType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Manage implements Initializable {

    @FXML
    private ComboBox<KeyValuePair> cmbManageRack;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        TotemRack rack = new TotemRack();
        try {
            List<TotemRack> racks = rack.getAllRacks();

            // fill the rack comboBox
            ObservableList<KeyValuePair> rackChoices = FXCollections.observableArrayList();

            for ( TotemRack rackTmp : racks ) {
                rackChoices.add(new KeyValuePair(rackTmp, rackTmp.getAddress()));
            }

            cmbManageRack.getItems().setAll(rackChoices);

        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
        }
    }

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
        KeyValuePair selectedRack = cmbManageRack.getSelectionModel().getSelectedItem();

        if ( selectedRack == null ) {
            lblError.setText("Select a rack");
            return;
        }

        TotemRack rack = (TotemRack) selectedRack.getKey();

        Controller_Update_Rack.selectedRackID = rack.getID();

        // go to update rack page
        try {
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