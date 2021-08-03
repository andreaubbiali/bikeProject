package bikeProject.dataservice;

import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;
import bikeProject.exception.WrongPasswordException;

import java.sql.SQLException;

public interface UserInterface {

    String addSubscription(SubscriptionType subType, long creditCardID) throws InvalidCreditCardException, PaymentException;

    String addSubscription(String password, SubscriptionType subType, CreditCard selectedCreditCard) throws WrongPasswordException, SQLException, InvalidCreditCardException, PaymentException;

//	public void setUsername(String username);
//
//	public boolean login(String username, String password);
//
//	public void registerNewUser(String name, String surname, String email, String username, String password);
//
//	public void setStudentUser();
}