package bikeProject.controller;

import bikeProject.dataservice.TotemRack;
import bikeProject.dataservice.User;
import bikeProject.dataservice.UserAdmin;
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
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Homepage implements Initializable {

    TotemRack rack = new TotemRack();

    @FXML
    private ComboBox<KeyValuePair> cmbTotems;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // logout the user if have a login
        User.logout();
        UserAdmin.logout();

        // load the totems
        try {
            List<TotemRack> totems = rack.getAllRacks();

            ObservableList<KeyValuePair> totemRackChoices = FXCollections.observableArrayList();

            for ( TotemRack totemTmp : totems ) {
                totemRackChoices.add(new KeyValuePair(totemTmp, totemTmp.getAddress()));
            }
            cmbTotems.getItems().setAll(totemRackChoices);

        } catch ( SQLException sql ) {
            lblError.setText("ERROR SQL");
            sql.printStackTrace();
        }

    }

    @FXML
    void loginAsUser(ActionEvent event) {
        Controller_Login.userType = "user";

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
    void loginAsAdmin(ActionEvent event) {
        Controller_Login.userType = "admin";

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
    void openTotemPage(ActionEvent event) {

        if ( cmbTotems.getSelectionModel().getSelectedItem() == null ) {
            lblError.setText("No totem selected");
            return;
        }

        // open rent bike panel
        try {
            Controller_Totem_Rack.totemRack = (TotemRack) cmbTotems.getSelectionModel().getSelectedItem().getKey();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/totemRackPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}