package bikeProject.controller;

import bikeProject.dataservice.User;
import bikeProject.exception.AccessDeniedException;
import bikeProject.exception.InvalidCreditCardException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller_New_Credit_Card implements Initializable {

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtCVV;

    @FXML
    private DatePicker dtpExpireDate;

    @FXML
    private Button btnNewCreditCard;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // set localDate to datePicker
        dtpExpireDate.setValue(LocalDate.now());
    }

    @FXML
    void addCreditCard(ActionEvent event) {
        Date expireDate = java.sql.Date.valueOf(dtpExpireDate.getValue());
        Date today = new Date();

        if ( txtNumber.getText().isEmpty() || txtCVV.getText().isEmpty() ) {
            lblError.setText("All fields are required.");
            return;
        } else if ( expireDate.before(today) ) {
            lblError.setText("The expire date can't be before today.");
            return;
        }

        try {
            User user = User.getInstance();

            // add the credit card
            user.addCreditCard(Long.parseLong(txtNumber.getText()), Long.parseLong(txtCVV.getText()), expireDate);

        } catch ( AccessDeniedException e ) {
            lblError.setText("Access denied for user.");
            e.printStackTrace();
            return;
        } catch ( SQLException sql ) {
            lblError.setText("SQL error.");
            sql.printStackTrace();
            return;
        } catch ( InvalidCreditCardException cre ) {
            lblError.setText(cre.getMessage());
            cre.printStackTrace();
            return;
        }

        // return to private area
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/privateUserAreaPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}