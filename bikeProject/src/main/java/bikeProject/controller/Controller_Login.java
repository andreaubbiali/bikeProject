package bikeProject.controller;

import bikeProject.dataservice.DataserviceInterface;
import bikeProject.dataservice.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller_Login {

    @FXML
    private Pane panLogin;

    @FXML
    private AnchorPane anchorPaneLogin;

    @FXML
    private PasswordField pswLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnSingIn;

    @FXML
    private Text linkRegisterNewUser;

    @FXML
    private Button btnProva;

    @FXML
    void prova(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/panels/privateUserAreaPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerNewUser(MouseEvent event) {
        // open the registration panel

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/registerNewUserPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void login(ActionEvent event) {
        DataserviceInterface inter = new User();

        // System.out.println(inter.login(txtUsername.getText(), pswLogin.getText()));
//    	User user = new User();
//    	System.out.println(user.login(txtUsername.getText(), pswLogin.getText()));
    }

}