package bikeProject.dataservice;

import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;

public interface UserInterface {

	public String addSubscription(SubscriptionType subType, long creditCardID)
			throws InvalidCreditCardException, PaymentException;

//	public void setUsername(String username);
//
//	public boolean login(String username, String password);
//
//	public void registerNewUser(String name, String surname, String email, String username, String password);
//
//	public void setStudentUser();
}
