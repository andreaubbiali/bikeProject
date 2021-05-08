package bikeProject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

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
    void login(ActionEvent event) {
    	System.out.println("funziona");
    	System.out.println(txtUsername.getText());
    	System.out.println(pswLogin.getText());
    }

}

