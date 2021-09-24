package bikeProject.controller;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.SubscriptionType;
import bikeProject.dataservice.User;
import bikeProject.exception.AccessDeniedException;
import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_New_Subscription implements Initializable {

    @FXML
    private TableView<SubscriptionType> tblSubscriptionType;

    @FXML
    private TableColumn<SubscriptionType, String> columnType;

    @FXML
    private TableColumn<SubscriptionType, Float> columnPrice;

    @FXML
    private TableColumn<SubscriptionType, Integer> columnDuration;

    @FXML
    private TableColumn<SubscriptionType, Integer> columnMustStartIn;

    @FXML
    private ChoiceBox<KeyValuePair> chkCreditCard;

    @FXML
    private ChoiceBox<KeyValuePair> chkSubscriptionType;

    @FXML
    private Label lblError;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try {
            SubscriptionType subType = new SubscriptionType();

            // fill the subscription type table
            columnType.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            columnDuration.setCellValueFactory(new PropertyValueFactory<>("daysDuration"));
            columnMustStartIn.setCellValueFactory(new PropertyValueFactory<>("mustStartIn"));

            List<SubscriptionType> subscriptionTypes = subType.getAllSubscriptionTypes();

            if ( subscriptionTypes.size() == 0 ) {
                tblSubscriptionType.setPlaceholder(new Label("There aren't subscription types"));
            } else {
                tblSubscriptionType.getItems().setAll(subscriptionTypes);
            }

            // fill the subscription type choice box
            ObservableList<KeyValuePair> subTypesChoices = FXCollections.observableArrayList();

            for ( SubscriptionType subTypeTmp : subscriptionTypes ) {
                subTypesChoices.add(new KeyValuePair(subTypeTmp, subTypeTmp.getName()));
            }
            chkSubscriptionType.getItems().setAll(subTypesChoices);

            // fill the credit card choice box
            User user = User.getInstance();

            List<CreditCard> creditCards = user.getCreditCard();

            ObservableList<KeyValuePair> CreditCardChoices = FXCollections.observableArrayList();

            for ( CreditCard creditCardTmp : creditCards ) {
                CreditCardChoices.add(new KeyValuePair(creditCardTmp, String.valueOf(creditCardTmp.getNumber())));
            }
            chkCreditCard.getItems().setAll(CreditCardChoices);

        } catch ( SQLException sql ) {
            lblError.setText("Error sql.");
            sql.printStackTrace();
        } catch ( AccessDeniedException e ) {
            lblError.setText("Access denied for user.");
            e.printStackTrace();
        }
    }

    @FXML
    void createSubscription(ActionEvent event) {
        KeyValuePair selectedCreditCard = chkCreditCard.getSelectionModel().getSelectedItem();
        KeyValuePair selectedSubscriptionType = chkSubscriptionType.getSelectionModel().getSelectedItem();

        if ( selectedCreditCard == null || selectedSubscriptionType == null ) {
            lblError.setText("select a credit card and a subscription type.");
            return;
        }

        try {
            User user = User.getInstance();

            user.addSubscription((SubscriptionType) selectedSubscriptionType.getKey(),
                    (CreditCard) selectedCreditCard.getKey());

        } catch ( AccessDeniedException e ) {
            lblError.setText("Access denied for user.");
            e.printStackTrace();
            return;
        } catch ( InvalidCreditCardException ce ) {
            lblError.setText(ce.getMessage());
            return;
        } catch ( PaymentException pe ) {
            lblError.setText("Something went wrong with the payment. Retry or change the credit card.");
            return;
        } catch ( SQLException sql ) {
            lblError.setText("SQL error.");
            sql.printStackTrace();
            return;
        }

        // return to private area
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