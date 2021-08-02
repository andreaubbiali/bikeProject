package bikeProject.controller;

import bikeProject.dataservice.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller_Register_New_User_Panel {

    @FXML
    private TextField txtInsertName;

    @FXML
    private TextField txtInsertSurname;

    @FXML
    private TextField txtInsertEmail;

    @FXML
    private TextField txtInsertUsername;

    @FXML
    private Text txtName;

    @FXML
    private Text txtSurname;

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtUsername;

    @FXML
    private Text txtPassword;

    @FXML
    private Text txtRepeatPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField pswPassword;

    @FXML
    private PasswordField pswRepeatPassword;

    @FXML
    void registerUser(ActionEvent event) {

        if (pswPassword.getText().equals(pswRepeatPassword.getText())) {
            User user = new User();
            // user.registerNewUser(txtInsertName.getText(), txtInsertSurname.getText(), txtInsertEmail.getText(),
            //        txtInsertUsername.getText(), pswPassword.getText());
        }

    }

}