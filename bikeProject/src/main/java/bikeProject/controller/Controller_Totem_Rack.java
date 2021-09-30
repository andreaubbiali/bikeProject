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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Controller_Totem_Rack implements Initializable {

    public class tblDTO {
        public String bikeType;
        public int numberPositions;
        public int occupiedPositions;
        public int rentableBikes;

        public tblDTO(String bikeType, int numberPositions, int occupiedPositions, int rentableBikes) {
            this.bikeType = bikeType;
            this.numberPositions = numberPositions;
            this.occupiedPositions = occupiedPositions;
            this.rentableBikes = rentableBikes;
        }

        public String getBikeType() {
            return bikeType;
        }

        public void setBikeType(String bikeType) {
            this.bikeType = bikeType;
        }

        public int getNumberPositions() {
            return numberPositions;
        }

        public void setNumberPositions(int numberPositions) {
            this.numberPositions = numberPositions;
        }

        public int getOccupiedPositions() {
            return occupiedPositions;
        }

        public void setOccupiedPositions(int occupiedPositions) {
            this.occupiedPositions = occupiedPositions;
        }

        public int getRentableBikes() {
            return rentableBikes;
        }

        public void setRentableBikes(int rentableBikes) {
            this.rentableBikes = rentableBikes;
        }

    }

    BikeType bikeType = new BikeType();
    public static TotemRack totemRack = null;
    List<BikeType> bikeTypeList = new ArrayList<>();
    private Map<String, tblDTO> tableRows = new HashMap<>();

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
    private TableView<tblDTO> tblBike;

    @FXML
    private TableColumn<tblDTO, String> columnBikeType;

    @FXML
    private TableColumn<tblDTO, Integer> columnNumber;

    @FXML
    private TableColumn<tblDTO, Integer> columnOccupied;

    @FXML
    private TableColumn<tblDTO, Integer> columnRentableBikes;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        if ( totemRack == null ) {
            System.out.println("totemRack static var not set up");
        }

        // load the bikeType
        try {
            bikeTypeList = bikeType.getBikeTypes();

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

        // set the table
        columnBikeType.setCellValueFactory(new PropertyValueFactory<>("bikeType"));
        columnNumber.setCellValueFactory(new PropertyValueFactory<>("numberPositions"));
        columnOccupied.setCellValueFactory(new PropertyValueFactory<>("occupiedPositions"));
        columnRentableBikes.setCellValueFactory(new PropertyValueFactory<>("rentableBikes"));

        // load data into table
        loadTable();
    }

    public void loadTable() {

        for ( BikeType bt : bikeTypeList ) {
            int countPositions = 0;
            int countOccupied = 0;
            int countRentableBikes = 0;

            for ( RackPosition rp : totemRack.getRackPositionList() ) {
                if ( rp.getAcceptedBikeType().getID() == bt.getID() ) {
                    countPositions++;

                    if ( rp.getBike() != null ) {
                        countOccupied++;
                    }

                    if ( rp.getBike() != null && !rp.getBike().isInMaintenance() && !rp.isBroken() ) {
                        countRentableBikes++;
                    }
                }

            }

            tableRows.put(bt.getType().toString(), new tblDTO(bt.getType().toString(), countPositions, countOccupied,
                    countRentableBikes));
        }

        if ( tableRows.size() == 0 ) {
            tblBike.setPlaceholder(new Label("There aren't bikes types"));
        } else {
            tblBike.getItems().setAll(tableRows.values());
        }

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

            txtResponse.setText("Go to the position: " + positionID + " and take your bike. \nEnjoy the ride.");
        } catch ( SQLException sql ) {
            txtResponse.setText("Error sql.");
            sql.printStackTrace();
        } catch ( AccessDeniedException | NotValidRentException | InvalidSubscriptionException | RackException e ) {
            txtResponse.setText(e.getMessage());
        }
        loadTable();
        User.logout();
    }

    @FXML
    void returnBike(ActionEvent event) {
        // try the login
        login();

        // open return bike panel
        try {
            Controller_Return_Bike.totemRack = totemRack;
            Controller_Return_Bike.loadObjects();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/returnBikePanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);

        } catch ( NotValidRentException r ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rent error");
            alert.setContentText(r.getMessage());

            alert.showAndWait();
        } catch ( RackException ra ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rack error");
            alert.setContentText(ra.getMessage());

            alert.showAndWait();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        loadTable();
        User.logout();
    }

    @FXML
    void goBackHome(ActionEvent event) {
        User.logout();

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