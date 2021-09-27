package bikeProject.controller;

import bikeProject.dataservice.*;
import bikeProject.exception.RackException;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Controller_Update_Rack implements Initializable {

    public class tblDTO {
        public String bikeType;
        public int numberPositions;
        public int occupiedPositions;

        public tblDTO(String bikeType, int numberPositions, int occupiedPositions) {
            this.bikeType = bikeType;
            this.numberPositions = numberPositions;
            this.occupiedPositions = occupiedPositions;
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
    }

    public static long selectedRackID;
    TotemRack rack = new TotemRack();
    BikeType bikeType = new BikeType();
    List<BikeType> bikeTypeList = new ArrayList<>();
    private Map<String, tblDTO> tableRows = new HashMap<>();

    @FXML
    private Label lblError;

    @FXML
    private TableView<tblDTO> tblBike;

    @FXML
    private TableColumn<tblDTO, String> columnBikeType;

    @FXML
    private TableColumn<tblDTO, Integer> columnNumber;

    @FXML
    private TableColumn<tblDTO, Integer> columnOccupied;

    @FXML
    private TextField txtAddress;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeTypeAddBike;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeTypeDeleteBike;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeIDDeleteBike;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // load bikeType comboBoxes

        try {
            bikeTypeList = bikeType.getBikeTypes();

            ObservableList<KeyValuePair> bikeTypesChoices = FXCollections.observableArrayList();

            for ( BikeType bikeTypeTmp : bikeTypeList ) {
                bikeTypesChoices.add(new KeyValuePair(bikeTypeTmp, bikeTypeTmp.getType().toString()));
            }
            cmbBikeTypeDeleteBike.getItems().setAll(bikeTypesChoices);
            cmbBikeTypeAddBike.getItems().setAll(bikeTypesChoices);

        } catch ( SQLException sql ) {
            lblError.setText("ERROR SQL");
            sql.printStackTrace();
            return;
        }

        // load address into txtBox
        try {
            rack.getRackByID(selectedRackID);

            txtAddress.setText(rack.getAddress());
        } catch ( SQLException sql ) {
            lblError.setText("ERROR SQL");
            sql.printStackTrace();
            return;
        }

        // set the table
        columnBikeType.setCellValueFactory(new PropertyValueFactory<>("bikeType"));
        columnNumber.setCellValueFactory(new PropertyValueFactory<>("numberPositions"));
        columnOccupied.setCellValueFactory(new PropertyValueFactory<>("occupiedPositions"));

        // load data into table
        loadTable();
    }

    public void loadTable() {

        for ( BikeType bt : bikeTypeList ) {
            int countPositions = 0;
            int countOccupied = 0;

            for ( RackPosition rp : rack.getRackPositionList() ) {
                if ( rp.getAcceptedBikeType().getID() == bt.getID() ) {
                    countPositions++;

                    if ( rp.getBike() != null ) {
                        countOccupied++;
                    }
                }
            }

            tableRows.put(bt.getType().toString(), new tblDTO(bt.getType().toString(), countPositions, countOccupied));
        }

        if ( tableRows.size() == 0 ) {
            tblBike.setPlaceholder(new Label("There aren't subscription types"));
        } else {
            tblBike.getItems().setAll(tableRows.values());
        }

    }

    public void loadCmbBikeToDelete(long bikeTypeID) {
        ObservableList<KeyValuePair> bikeToDeleteChoices = FXCollections.observableArrayList();

        for ( RackPosition rp : rack.getRackPositionList() ) {

            if ( rp.getBike() != null && rp.getAcceptedBikeType().getID() == bikeTypeID ) {
                bikeToDeleteChoices.add(new KeyValuePair(rp, String.valueOf(rp.getBike().getID())));
            }

        }

        cmbBikeIDDeleteBike.getItems().setAll(bikeToDeleteChoices);
    }

    @FXML
    void deleteRack(ActionEvent event) {

        // try to delete rack
        try {
            rack.deleteRack();

            goBackPage(event);

        } catch ( SQLException sql ) {
            if ( sql.getMessage().equals("noDelete") ) {
                lblError.setText("Is not possible to delete a rack with bike in it");
            } else {
                lblError.setText("ERROR SQL");
                sql.printStackTrace();
            }
        }

    }

    @FXML
    void loadBikeToDelete(ActionEvent event) {
        BikeType selectedType = (BikeType) cmbBikeTypeDeleteBike.getSelectionModel().getSelectedItem().getKey();

        loadCmbBikeToDelete(selectedType.getID());
    }

    @FXML
    void deleteBike(ActionEvent event) {
        RackPosition selectedBike = (RackPosition) cmbBikeIDDeleteBike.getSelectionModel().getSelectedItem().getKey();
        long selectedBikeTypeID = selectedBike.getBike().getType().getID();

        try {
            // delete the selected bike from the db
            selectedBike.deleteBike();

            // reload table
            loadTable();
            loadCmbBikeToDelete(selectedBikeTypeID);
        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
        }
    }

    @FXML
    void addBike(ActionEvent event) {

        // add a new bike into the rack
        KeyValuePair selectedType = cmbBikeTypeAddBike.getSelectionModel().getSelectedItem();

        if ( selectedType == null ) {
            lblError.setText("Select a bike type to add");
            return;
        }

        try {
            rack.addNewBike((BikeType) selectedType.getKey());

            // reload table
            loadTable();

        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
        } catch ( RackException r ) {
            lblError.setText(r.getMessage());
        }
    }

    @FXML
    void fixDamagedBike(ActionEvent event) {

        // check if there is some damaged bike in the rack
        boolean found = false;
        for ( RackPosition rp : rack.getRackPositionList() ) {
            if ( rp.getBike() != null && rp.getBike().isInMaintenance() ) {
                found = true;
                break;
            }
        }

        if ( !found ) {
            lblError.setText("There are not damaged bike into this rack");
            return;
        }

        // go to damaged bikes page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/fixDamagedBikePanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    void fixRackPosition(ActionEvent event) {

        // check if there is some damaged rack position in the rack
        boolean found = false;
        for ( RackPosition rp : rack.getRackPositionList() ) {

            if ( rp.isBroken() ) {
                found = true;
                break;
            }
        }

        if ( !found ) {
            lblError.setText("There are not rack position broken into this rack");
            return;
        }

        // go to damaged bikes page
        try {
            Controller_Fix_Broken_Rack_Position.rack = rack;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/fixBrokenRackPositionPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateAddress(ActionEvent event) {

        if ( txtAddress.getText().isEmpty() ) {
            lblError.setText("The address is required");
            return;
        }

        try {
            // update rack address
            rack.updateRackAddress(txtAddress.getText());

        } catch ( SQLException sql ) {
            lblError.setText("ERROR SQL");
            sql.printStackTrace();
            return;
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        goBackPage(event);
    }

    public void goBackPage(ActionEvent event) {
        // go back to previous page
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