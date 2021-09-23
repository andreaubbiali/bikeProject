package bikeProject.controller;

import bikeProject.dataservice.DataserviceInterface;
import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;
import bikeProject.exception.WrongPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Controller_Login {

    @FXML
    private Pane panLogin;

    @FXML
    private PasswordField pswLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnGoBack;

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
    void login(ActionEvent event) {
        Controller_User user = new Controller_User();

        if ( txtUsername.getText().isEmpty() || pswLogin.getText().isEmpty() ) {
            lblError.setText("Username or password empty");
            return;
        }

        try {
            user.login(txtUsername.getText(), pswLogin.getText());
        } catch ( UserNotFoundException u ) {
            lblError.setText("User not found. RETRY");
            return;
        } catch ( WrongPasswordException w ) {
            lblError.setText("The email or password is wrong. RETRY");
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

}