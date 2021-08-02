package bikeProject.database;

import bikeProject.dataservice.User;

public interface UserDatabaseInterface {

	public void login(String username, String password, User user) throws Exception;

}
