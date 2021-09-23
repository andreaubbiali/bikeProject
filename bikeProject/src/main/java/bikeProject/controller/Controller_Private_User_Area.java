package bikeProject.controller;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.User;
import bikeProject.exception.AccessDeniedException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Private_User_Area implements Initializable {

    @FXML
    private TableView<CreditCard> tblCreditCard;

    @FXML
    private TableColumn<CreditCard, String> columnNumber;

    @FXML
    private TableColumn<CreditCard, String> columnExpireDate;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // tblCreditCard.setPlaceholder(new Label("No rows to display"));

        columnNumber.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("number"));
        columnExpireDate.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("expireDate"));
        try {
            User user = User.getInstance();

            // fill the credit card table form
            if ( user.getCreditCard().size() == 0 ) {
                tblCreditCard.setPlaceholder(new Label("No rows to display"));
                return;
            }
            tblCreditCard.getItems().setAll(user.getCreditCard());

        } catch ( AccessDeniedException e ) {
            lblError.setText("Access denied for user.");
            e.printStackTrace();
            return;
        }

    }

}