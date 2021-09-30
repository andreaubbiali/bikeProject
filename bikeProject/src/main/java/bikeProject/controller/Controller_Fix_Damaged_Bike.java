package bikeProject.controller;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.DamageMessage;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller_Fix_Damaged_Bike implements Initializable {

    public class tblDTO {
        public long bikeID;
        public String message;

        public tblDTO(long bikeID, String message) {
            this.bikeID = bikeID;
            this.message = message;
        }

        public long getBikeID() {
            return bikeID;
        }

        public void setBikeID(long bikeID) {
            this.bikeID = bikeID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static List<Bike> damagedBikeList = null;
    private Map<Bike, tblDTO> tableRows = new HashMap<>();

    @FXML
    private TableView<tblDTO> tblDamagedBike;

    @FXML
    private TableColumn<tblDTO, Long> columnBikeID;

    @FXML
    private TableColumn<tblDTO, String> columnUserMessage;

    @FXML
    private ComboBox<KeyValuePair> cmbBikeID;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // fill the map used for the table
        DamageMessage damage = new DamageMessage();
        for ( Bike bikeTmp : damagedBikeList ) {

            try {
                damage = damage.getDamageByBikeID(bikeTmp.getID());
            } catch ( SQLException sql ) {
                lblError.setText("ERROR SQL");
                sql.printStackTrace();
                return;
            }

            tableRows.put(bikeTmp, new tblDTO(bikeTmp.getID(), damage.getMessage()));
        }

        // fill the bike id choice box
        loadCmb();

        // fill the table
        columnBikeID.setCellValueFactory(new PropertyValueFactory<>("bikeID"));
        columnUserMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        loadTable();
    }

    public void loadCmb() {
        ObservableList<KeyValuePair> bikeIDChoices = FXCollections.observableArrayList();

        for ( Map.Entry<Bike, tblDTO> entry : tableRows.entrySet() ) {
            bikeIDChoices.add(new KeyValuePair(entry.getKey(), String.valueOf(entry.getValue().bikeID)));
        }

        cmbBikeID.getItems().setAll(bikeIDChoices);
    }

    public void loadTable() {

        if ( tableRows.size() == 0 ) {
            tblDamagedBike.setPlaceholder(new Label("There aren't damaged bikes types"));
        } else {
            tblDamagedBike.getItems().setAll(tableRows.values());
        }
    }

    @FXML
    void setFixed(ActionEvent event) {

        if ( cmbBikeID.getSelectionModel().getSelectedItem() == null ) {
            lblError.setText("Select a bike id to fix");
        }

        Bike bike = (Bike) cmbBikeID.getSelectionModel().getSelectedItem().getKey();

        try {
            bike.fixBikeInMaintenance();
        } catch ( SQLException sql ) {
            lblError.setText("SQL ERROR");
            sql.printStackTrace();
            return;
        }

        tableRows.remove(bike);

        loadCmb();
        loadTable();
    }

    @FXML
    void returnBack(ActionEvent event) {

        // go to damaged bikes page
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