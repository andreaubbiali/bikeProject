package bikeProject.database;

import java.sql.SQLException;

import bikeProject.dataservice.User;

public interface SubscriptionDatabaseInterface {

	public User controlUniqueCodeOfAUser(String uniqueCode) throws SQLException;

}
