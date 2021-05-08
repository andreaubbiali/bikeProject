package bikeProject.database;

import java.sql.ResultSet;
import java.sql.Statement;

import bikeProject.database.Database_Interface.Database_Interface_User;

public class User_Database extends Database implements Database_Interface.Database_Interface_User {
	
	public ResultSet getUser() throws Exception {
		
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM User";
			ResultSet res = statement.executeQuery(query);
	
		return res;
	}
	
}
