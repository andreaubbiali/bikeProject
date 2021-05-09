package bikeProject.database;

import java.sql.ResultSet;

import bikeProject.dataservice.User;

public interface Database_Interface {
	
	public void createConnection();
	
	interface Database_Interface_User{
		public ResultSet getUser() throws Exception;
		public void login(String username, String password, User user) throws Exception;
		public void registerNewUser(String username, String password, String salt) throws Exception;
    }  
	
}
