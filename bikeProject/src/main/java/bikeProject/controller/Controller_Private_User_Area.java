package bikeProject.controller;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    public void initialize(URL location, ResourceBundle resources) {
        // tblCreditCard.setPlaceholder(new Label("No rows to display"));

        columnNumber.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("548454"));
        columnExpireDate.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("daff"));
        Controller_User user = new User();

        List<CreditCard> listCreditCard = tblCreditCard.getItems().setAll();
    }

}