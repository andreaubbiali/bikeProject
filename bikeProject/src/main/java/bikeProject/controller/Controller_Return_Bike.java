package bikeProject.controller;

import bikeProject.dataservice.*;
import bikeProject.exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Return_Bike {

    public static TotemRack totemRack = null;
    private static Rent userRent = null;
    private static List<RackPosition> freeRackPosition = null;

    @FXML
    private ComboBox<KeyValuePair> cmbRackPositionID;

    @FXML
    private Label lblError;

    @FXML
    private TextArea txtDamageText;

    public static void loadObjects() throws NotValidRentException, RackException {

        // get user active rent
        userRent = User.activeUserRent();

        if ( userRent == null ) {
            throw new NotValidRentException("You don't have any active rent.");
        }

        // get free rack position where the bike can be set
        freeRackPosition = totemRack.getFreeRackPositionForBikeType(userRent.getBike().getType());
        if ( freeRackPosition == null || freeRackPosition.isEmpty() ) {
            throw new RackException("Sorry there are not free rack position for your bike. Try in another totem rack.");
        }
    }

    @FXML
    private void initialize() {

        // fill the rack position choice box
        ObservableList<KeyValuePair> rackPositionChoices = FXCollections.observableArrayList();

        for ( RackPosition rackPosTmp : freeRackPosition ) {
            rackPositionChoices.add(new KeyValuePair(rackPosTmp, String.valueOf(rackPosTmp.getID())));
        }
        cmbRackPositionID.getItems().setAll(rackPositionChoices);
    }

    @FXML
    void returnBike(ActionEvent event) {

        KeyValuePair selectedCmb = cmbRackPositionID.getSelectionModel().getSelectedItem();

        if ( selectedCmb == null ) {
            lblError.setText("Select the position where you have put the bike");
            return;
        }

        RackPosition position = (RackPosition) selectedCmb.getKey();

        System.out.println(txtDamageText.getText());

        try {
            String damageText = null;
            if ( !txtDamageText.getText().isEmpty() ) {
                damageText = txtDamageText.getText();
            }
            float totalCost = totemRack.returnBike(position, damageText);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Rent ended");
            alert.setContentText("For this rent you have payed: " + totalCost);

            Optional<ButtonType> result = alert.showAndWait();
            if ( result.get() == ButtonType.OK ) {
                returnTotemRackPanel(event);
            }

        } catch ( SQLException sql ) {
            lblError.setText("SQL Error");
            sql.printStackTrace();
            return;
        } catch ( RackException | InvalidSubscriptionException | PaymentException | InvalidCreditCardException | NotValidRentException e ) {
            lblError.setText(e.getMessage());
            return;
        }

    }

    public void returnTotemRackPanel(ActionEvent event) {
        // open totem panel
        try {
            Controller_Totem_Rack.totemRack = totemRack;

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