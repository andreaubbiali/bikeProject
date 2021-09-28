package bikeProject.controller;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.User;
import bikeProject.exception.AccessDeniedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Private_User_Area implements Initializable {

    @FXML
    private TableView<SubscriptionDTO> tblSubscription;

    @FXML
    private TableColumn<SubscriptionDTO, String> columnType;

    @FXML
    private TableColumn<SubscriptionDTO, LocalDate> columnStartDate;

    @FXML
    private TableColumn<SubscriptionDTO, Boolean> columnDeleted;

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

        try {
            User user = User.getInstance();

            // fill the credit card table
            columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            columnExpireDate.setCellValueFactory(new PropertyValueFactory<>("expireDate"));

            List<CreditCard> creditCards = user.getCreditCardList();
            if ( creditCards.size() == 0 ) {
                tblCreditCard.setPlaceholder(new Label("There aren't credit cards"));
            } else {
                tblCreditCard.getItems().setAll(creditCards);
            }

            // fill the subscription table
            columnType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            columnStartDate.setCellValueFactory(new PropertyValueFactory<>("subscriptionDate"));
            columnDeleted.setCellValueFactory(new PropertyValueFactory<>("deleted"));

            List<Subscription> subscriptions = user.getSubscriptionList();
            List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();
            for ( Subscription sub : subscriptions ) {
                subscriptionDTOList.add(new SubscriptionDTO(sub));
            }

            if ( subscriptionDTOList.size() == 0 ) {
                tblSubscription.setPlaceholder(new Label("There aren't subscriptions"));
            } else {
                tblSubscription.getItems().setAll(subscriptionDTOList);
            }

        } catch ( AccessDeniedException e ) {
            lblError.setText("Access denied for user.");
            e.printStackTrace();
            return;
        }

    }

    @FXML
    void newCreditCard(ActionEvent event) {

        // open new credit card panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/newCreditCardPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    void newSubscription(ActionEvent event) {

        // open new subscription panel
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bikeProject/newSubscriptionPanel.fxml"));
            Parent pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}