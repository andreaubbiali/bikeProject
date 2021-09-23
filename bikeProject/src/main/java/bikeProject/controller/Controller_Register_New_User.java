package bikeProject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Controller_Register_New_User {

    @FXML
    private TextField txtRegisterName;

    @FXML
    private TextField txtRegisterSurname;

    @FXML
    private TextField txtRegisterEmail;

    @FXML
    private Text lblName;

    @FXML
    private Text lblSurname;

    @FXML
    private Text lblEmail;

    @FXML
    private Text lblPassword;

    @FXML
    private Text lblRepeatPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField pswPassword;

    @FXML
    private PasswordField pswRepeatPassword;

    @FXML
    private Button btnReturnHomePage;

    @FXML
    private Label lblError;

    @FXML
    private CheckBox chkStudent;

    @FXML
    void registerUser(ActionEvent event) {

        if ( txtRegisterName.getText().isEmpty() || txtRegisterSurname.getText().isEmpty() || txtRegisterEmail.getText().isEmpty() || pswPassword.getText().isEmpty() || pswRepeatPassword.getText().isEmpty() ) {
            lblError.setText("Some field missing.");
            return;
        }

        if ( !pswPassword.getText().equals(pswRepeatPassword.getText()) ) {
            lblError.setText("Mismatch passwords.");
            return;
        }

        Controller_User user = new Controller_User();

        try {
            user.registerUser(txtRegisterName.getText(), txtRegisterSurname.getText(), txtRegisterEmail.getText(),
                    pswPassword.getText(), chkStudent.isSelected());
        } catch ( SQLException e ) {
            lblError.setText("SQL exception.");
            e.printStackTrace();
        }

    }

    @FXML
    void returnHomePage(ActionEvent event) {
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

}