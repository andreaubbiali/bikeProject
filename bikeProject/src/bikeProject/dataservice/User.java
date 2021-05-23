package bikeProject.dataservice;

import java.util.List;

import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;

public class User implements DataserviceInterface, UserInterface {

	/**
	 * @invariant
	 */

	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ String name;
	private /* @ not_null @ */ String surname;
	private /* @ not_null @ */ String username;
	private /* @ not_null @ */ String email;
	private /* @ not_null @ */ boolean student;
	private List<CreditCard> creditCard;
	private List<Subscription> subscription;

	/**
	 * @requires creditCardID > 0
	 * @ensures (a >= b && \result == a) || (b > a && \result == b)
	 * 
	 * @param subType      the type of subscription the user want to subscribe
	 * @param creditCardID the id of the credit card used for payment
	 * @throws InvalidCreditCardException if the selected credit card doesn't exist
	 *                                    or is not valid for the subscription
	 * @throws PaymentException           if the payment fails
	 * @return the uniqueCode of the subscription
	 */
	public String addSubscription(SubscriptionType subType, long creditCardID)
			throws InvalidCreditCardException, PaymentException {
		CreditCard selectedCreditCard = null;

		try {
			selectedCreditCard = new CreditCard(creditCardID);

			if (selectedCreditCard == null || !selectedCreditCard.isCreditCardValidForSubscription(subType)) {
				throw new InvalidCreditCardException("The selected creditCard isn't valid for the subscription");
			}

		} catch (Exception e) {
			throw new InvalidCreditCardException("The selected creditCard isn't valid");
		}

		// payment for the subscription
		selectedCreditCard.pay(subType.getPrice());

		// creation of the new subscription
		Subscription newSubscription = new Subscription();
		String uniqueCode = newSubscription.createSubscription(subType, ID);

		this.subscription.add(newSubscription);

		return uniqueCode;
	}

	/**
	 * 
	 * @param creditCard
	 */
	public void addCreditCard(CreditCard creditCard) {

		this.creditCard.add(creditCard);

	}

	/**
	 * 
	 * @param student
	 */
	public void setStudent(boolean student) {
		// TODO
		// send request to university
		this.student = student;
	}

	public boolean isValidUniqueCodeSubscriptionForUser(String uniqueCode, String password) {
		// Subscription subscription = new Subscription(uniqueCode);

		// TODO
		// userDB.controlUniqueCodeOfAUser(subscription);

		return true;
	}

//	/*
//	 * @PRE the user is registered but not authenticated to the application
//	 * 
//	 * @POST the use is authenticated
//	 */
//	public boolean login(String username, String password) {
//
//		System.out.println("stampa di this.username prima    " + this.username + " fine");
//
//		try {
//			userDB.login(username, password, this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("stampa di this.username dopo    " + this.username + " fine");
//
//		return false;
//
//	}
//
//	public void registerNewUser(String name, String surname, String email, String username, String password) {
//
//		// Generate Salt. The generated value can be stored in DB.
//		String salt = PasswordUtils.getSalt(30);
//
//		// Protect user's password. The generated value can be stored in DB.
//		String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
//
//		try {
//			userDB.registerNewUser(name, surname, email, username, mySecurePassword, salt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		setUser(name, surname, email, username);
//
//		System.out.println("New user registered: " + this.username);
//
//	}
//
//	public void setUser(String name, String surname, String email, String username) {
//		setName(name);
//		setSurname(surname);
//		setUsername(username);
//		setEmail(email);
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setSurname(String surname) {
//		this.surname = surname;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStudent() {
		return student;
	}

	public List<CreditCard> getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(List<CreditCard> creditCard) {
		this.creditCard = creditCard;
	}

	public List<Subscription> getSubscription() {
		return subscription;
	}

	public void setSubscription(List<Subscription> subscription) {
		this.subscription = subscription;
	}
}
