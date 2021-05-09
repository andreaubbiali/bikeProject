package bikeProject.dataservice;

import bikeProject.database.Database_Interface;
import bikeProject.database.User_Database;

public interface Dataservice_Interface {
		
	Database_Interface.Database_Interface_User userDB = new User_Database();
	
	
	
}
