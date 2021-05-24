package bikeProject.dataservice;

import bikeProject.database.Database;
import bikeProject.database.Database_Interface;
import bikeProject.database.SubscriptionDatabase;
import bikeProject.database.SubscriptionDatabaseInterface;
import bikeProject.database.UserDatabase;
import bikeProject.database.UserDatabaseInterface;

public interface DataserviceInterface {

	Database_Interface database = new Database();
	UserDatabaseInterface userDB = new UserDatabase();
	SubscriptionDatabaseInterface subscriptionDB = new SubscriptionDatabase();

//	Database_Interface.Database_Interface_User userDB = new User_Database();

//	// User_Admin interface
//	public interface User_Admin {
//		public boolean login(String username, String password);
//	}
//
//	// Credit_Card interface
//	public interface Credit_Card {
//		public boolean registerNewCreditCard(long cardNumber, long cvv, Date expireDate, long userID);
//
//		public boolean deleteCreditCard(long cardNumber);
//
//		public boolean payment(long cardNumber, long subscriptionID, float amount, String causal);
//
//	}
//
//	// Bike interface
//	public interface Bike {
//		public void addBike(String bikeType);
//
//		public void setInMaintenance(long ID);
//
//		public void deleteBike(long bikeID);
//
//	}
//
//	// Rack interface
//	public interface Rack {
//		public void addRack(String address);
//
//		public void deleteRack(long rackID);
//
//		public void addBike(long rackID);
//
//		public void removeBike(long rackID, long bikeID);
//
//	}
//
//	// Subscription interface
//	public interface Subscription {
//		public boolean addSubscription(String subscriptionType, long userID);
//
//		public boolean deleteSubscription(long subscriptionID);
//	}
//
//	// Rent interface
//	public interface Rent {
//		public void rentBike(long bikeID, long userID, Date startDate);
//
//		public void returnBike(long bikeID, long userID, long rackID);
//
//		public void damageCommunication(String communication);
//
//		public void returnVerified();
//
//	}

}
