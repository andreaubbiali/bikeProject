package bikeProject;

import java.sql.ResultSet;

public interface Database_Interface {
	
	public void createConnection();
	
	interface Database_Interface_User{
		public ResultSet getUser() throws Exception;  
    }  
	
}
