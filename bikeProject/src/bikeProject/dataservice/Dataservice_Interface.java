package bikeProject.dataservice;

import bikeProject.database.Database_Interface;
import bikeProject.database.User_Database;

public interface Dataservice_Interface {
		
	Database_Interface.Database_Interface_User userDB = new User_Database();
	
	// User
	public void setUsername(String username);
	public boolean login(String username, String password);
	public void registerNewUser(String username, String password);
	
	
	
}
