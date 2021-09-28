package bikeProject.controller;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.TotemRack;
import bikeProject.dataservice.User;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Totem_Rack implements Initializable {

    BikeType bikeType = new BikeType();
    public static TotemRack totemRack = null;

    @FXML
    private Label lblTitle;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeType;

    @FXML
    private TextArea txtResponse;

    @FXML
    private PasswordField pswLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private TableView<?> tblBike;

    @FXML
    private TableColumn<?, ?> columnBikeType;

    @FXML
    private TableColumn<?, ?> columnNumber;

    @FXML
    private TableColumn<?, ?> columnOccupied;

    @FXML
    private TableColumn<?, ?> columnRentableBikes;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        if ( totemRack == null ) {
            System.out.println("totemRack static var not set up");
        }

        // load the bikeType
        try {
            List<BikeType> bikeTypeList = bikeType.getBikeTypes();

            ObservableList<KeyValuePair> bikeTypesChoices = FXCollections.observableArrayList();

            for ( BikeType bikeTypeTmp : bikeTypeList ) {
                bikeTypesChoices.add(new KeyValuePair(bikeTypeTmp, bikeTypeTmp.getType().toString()));
            }
            cmbBikeType.getItems().setAll(bikeTypesChoices);

        } catch ( SQLException sql ) {
            txtResponse.setText("SQL ERROR");
            sql.printStackTrace();
        }

        lblTitle.setText("Totem address: " + totemRack.getAddress());

    }

    @FXML
    void startRent(ActionEvent event) {
        KeyValuePair selectedCmb = cmbBikeType.getSelectionModel().getSelectedItem();

        if ( selectedCmb == null ) {
            txtResponse.setText("Select a bike type to rent");
            return;
        }

        // try the login
        login();

        BikeType selectedBikeType = (BikeType) selectedCmb.getKey();

        // start the rent
        try {
            long positionID = totemRack.rentBike(selectedBikeType);

            txtResponse.setText("Go to the position: " + positionID + " and take your bike. \nEnjoy the service.");
        } catch ( SQLException sql ) {
            txtResponse.setText("Error sql.");
            sql.printStackTrace();
        } catch ( AccessDeniedException | NotValidRentException | InvalidSubscriptionException | RackException e ) {
            txtResponse.setText(e.getMessage());
        }
    }

    @FXML
    void returnBike(ActionEvent event) {

    }

    @FXML
    void goBackHome(ActionEvent event) {
        
        // open home panel
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

    public void login() {
        if ( txtUsername.getText().isEmpty() || pswLogin.getText().isEmpty() ) {
            txtResponse.setText("Username or password empty");
            return;
        }

        try {
            User.login(txtUsername.getText(), pswLogin.getText());
        } catch ( WrongPasswordException w ) {
            txtResponse.setText("Email or password wrong. RETRY");
        } catch ( SQLException s ) {
            txtResponse.setText("Error sql.");
            s.printStackTrace();
        }
    }

}