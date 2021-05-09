package bikeProject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import java.sql.Connection;

import bikeProject.dataservice.User;

public class Controller {
	
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
    void login(ActionEvent event) {
    	User user = new User();
    	System.out.println(user.login(txtUsername.getText(), pswLogin.getText()));
    }

    @FXML
    void signIn(ActionEvent event) {
    	
    	User user = new User();
    	user.registerNewUser(txtUsername.getText(), pswLogin.getText());
    	
//    	User user = new User();
//    	user.login(txtUsername.getText(), "SS");
//    	System.out.println(user.username);
    	
    }

}

