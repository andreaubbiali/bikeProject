package bikeProject.controller;

import bikeProject.dataservice.User;
import bikeProject.dataservice.UserAdmin;
import bikeProject.exception.AccessDeniedException;
import bikeProject.exception.WrongPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Controller_Login {

    @FXML
    private PasswordField pswLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblError;

    @FXML
    void goBackToHomePage(ActionEvent event) {

        // return to homepage
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

    @FXML
    void loginAsUser(ActionEvent event) {

        if ( txtUsername.getText().isEmpty() || pswLogin.getText().isEmpty() ) {
            lblError.setText("Username or password empty");
            return;
        }

        try {
            User.login(txtUsername.getText(), pswLogin.getText());
        } catch ( WrongPasswordException w ) {
            lblError.setText("Email or password wrong. RETRY");
            return;
        } catch ( SQLException s ) {
            lblError.setText("Error sql.");
            s.printStackTrace();
            return;
        }

        // go to next page
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

    @FXML
    void loginAsAdmin(ActionEvent event) {

        if ( txtUsername.getText().isEmpty() || pswLogin.getText().isEmpty() ) {
            lblError.setText("Username or password empty");
            return;
        }

        try {
            UserAdmin.login(txtUsername.getText(), pswLogin.getText());
        } catch ( WrongPasswordException w ) {
            lblError.setText("Email or password wrong. RETRY");
            return;
        } catch ( SQLException s ) {
            lblError.setText("Error sql.");
            s.printStackTrace();
            return;
        } catch ( AccessDeniedException ac ) {
            lblError.setText("The user is not an admin.");
            return;
        }

        // go to next page
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