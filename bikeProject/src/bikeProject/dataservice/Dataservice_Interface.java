package bikeProject.dataservice;

import bikeProject.database.Database_Interface;
import bikeProject.database.User_Database;

public interface Dataservice_Interface {

	Database_Interface.Database_Interface_User userDB = new User_Database();

	// User interface
	public interface User {
		public void setUsername(String username);

		public boolean login(String username, String password);

		public void registerNewUser(String name, String surname, String email, String username, String password);

		public void setStudentUser();
	}

	// Admin_User interface
	public interface Admin_User {

	}

	// Credit_Card interface
	public interface Credit_Card {
		public boolean registerNewCreditCard(long cardNumber, long cvv, Date expireDate, long userID);

		public boolean deleteCreditCard(long cardNumber);

		public boolean payment(long cardNumber, long subscriptionID, float amount, String causal);

	}

	// Bike_Type interface
	public interface Bike_Type {
		// USE ENUMERAL
	}

	// Bike interface
	public interface Bike {
		public void addBike(String bikeType);

		public void setInMaintenance(long ID);

		public void rent(long ID, long userID);

	}

	// Rack interface
	public interface Rack {
		public void addRack(String address);

		public void deleteRack(long rackID);

		public void addBike(long rackID);

		public void removeBike(long rackID, long bikeID);

	}

	// Subscription interface
	public interface Subscription {
		public boolean addSubscription(String subscriptionType, long userID);

		public boolean deleteSubscription(long subscriptionID);
	}

}
