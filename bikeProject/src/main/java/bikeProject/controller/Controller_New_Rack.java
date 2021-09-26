package bikeProject.controller;

import bikeProject.dataservice.*;
import com.sun.javafx.collections.MappingChange;
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

public class Controller_New_Rack implements Initializable {

    public class tblDTO {
        public String bikeType;
        public int numberPositions;

        public tblDTO(String bikeType, int numberPositions) {
            this.bikeType = bikeType;
            this.numberPositions = numberPositions;
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
    }

    private Map<String, tblDTO> tableRows = new HashMap<>();

    @FXML
    private TextField txtAddress;

    @FXML
    private TableView<tblDTO> tblBike;

    @FXML
    private TableColumn<tblDTO, String> columnBikeType;

    @FXML
    private TableColumn<tblDTO, Integer> columnNumber;

    @FXML
    private Label lblError;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeType;

    @FXML
    private TextField txtNumber;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // fill the bike type table
        columnBikeType.setCellValueFactory(new PropertyValueFactory<>("bikeType"));
        columnNumber.setCellValueFactory(new PropertyValueFactory<>("numberPositions"));

        BikeType bikeType = new BikeType();

        try {
            List<BikeType> bikeTypes = bikeType.getBikeTypes();

            if ( bikeTypes.size() == 0 ) {
                tblBike.setPlaceholder(new Label("There aren't bikeTypes"));
            } else {

                for ( BikeType obj : bikeTypes ) {
                    tableRows.put(obj.getType().toString(), new tblDTO(obj.getType().toString(), 0));
                }
                tblBike.getItems().setAll(tableRows.values());
            }

            // initialize the comboBox
            ObservableList<KeyValuePair> bikeTypesChoice = FXCollections.observableArrayList();

            for ( BikeType bikeTypeTmp : bikeTypes ) {
                bikeTypesChoice.add(new KeyValuePair(bikeTypeTmp, bikeTypeTmp.getType().toString()));
            }
            cmbBikeType.getItems().setAll(bikeTypesChoice);

        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
        }

    }

    @FXML
    void addBikePositions(ActionEvent event) {

        if ( txtNumber.getText().isEmpty() || cmbBikeType.getSelectionModel().getSelectedItem() == null ) {
            lblError.setText("Choose a bike type and insert a number");
            return;
        }

        // update the list
        BikeType selectedBikeType = (BikeType) cmbBikeType.getSelectionModel().getSelectedItem().getKey();
        tableRows.put(selectedBikeType.getType().toString(), new tblDTO(selectedBikeType.getType().toString(),
                Integer.parseInt(txtNumber.getText())));

        // update table
        tblBike.getItems().setAll(tableRows.values());
    }

    @FXML
    void addRack(ActionEvent event) {

        if ( txtAddress.getText().isEmpty() ) {
            lblError.setText("The address is required");
            return;
        }

        TotemRack rack = new TotemRack();

        try {
            // add the rack into db
            long rackID = rack.addNewRack(txtAddress.getText());

            // add positions into the rack
            for ( Map.Entry<String, tblDTO> entry : tableRows.entrySet() ) {

                if ( entry.getValue().numberPositions != 0 ) {
                    RackPosition rackPosition = new RackPosition();
                    rackPosition.addRackPositions(rackID, entry.getValue().numberPositions, entry.getValue().bikeType);
                }
            }

        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
            return;
        }

        // return to manage page
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