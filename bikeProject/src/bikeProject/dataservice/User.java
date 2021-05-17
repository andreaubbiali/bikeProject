package bikeProject.dataservice;

import java.util.List;

import bikeProject.PasswordUtils;

public class User implements Dataservice_Interface.User {

	// public final /*@ not_null @*/ long ID;
	public /* @ not_null @ */ String name;
	public /* @ not_null @ */ String surname;
	public /* @ not_null @ */ String username;
	public /* @ not_null @ */ String email;

	public List<Credit_Card> creditCard;

	/*
	 * @PRE the user is registered but not authenticated to the application
	 * 
	 * @POST the use is authenticated
	 */
	public boolean login(String username, String password) {

		System.out.println("stampa di this.username prima    " + this.username + " fine");

		try {
			userDB.login(username, password, this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("stampa di this.username dopo    " + this.username + " fine");

		return false;

	}

	public void registerNewUser(String name, String surname, String email, String username, String password) {

		// Generate Salt. The generated value can be stored in DB.
		String salt = PasswordUtils.getSalt(30);

		// Protect user's password. The generated value can be stored in DB.
		String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);

		try {
			userDB.registerNewUser(name, surname, email, username, mySecurePassword, salt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setUser(name, surname, email, username);

		System.out.println("New user registered: " + this.username);

	}

	public void setUser(String name, String surname, String email, String username) {
		setName(name);
		setSurname(surname);
		setUsername(username);
		setEmail(email);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
