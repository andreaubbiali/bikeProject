package bikeProject.dataservice;

import bikeProject.database.Database;
import bikeProject.database.Database_Interface;
import bikeProject.database.User_Database;
import bikeProject.database.Database_Interface.Database_Interface_User;

public interface Dataservice_Interface {
	
	Database db = new Database();
	
	Database_Interface.Database_Interface_User userDB = new User_Database();
	
	// public boolean login(String username, String password);
	
	
}
