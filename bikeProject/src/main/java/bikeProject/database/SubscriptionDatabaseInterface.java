package bikeProject.database;

import java.sql.SQLException;

import bikeProject.dataservice.Subscription;

public interface SubscriptionDatabaseInterface {

	public void getSubscriptionByUniqueCode(String uniqueCode, Subscription subscription) throws SQLException;

}
