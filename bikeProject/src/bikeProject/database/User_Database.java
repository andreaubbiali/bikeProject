package bikeProject.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import bikeProject.dataservice.User;
import bikeProject.PasswordUtils;

public class User_Database extends Database implements Database_Interface.Database_Interface_User {
	
	public ResultSet getUser() throws Exception {
		
		Statement statement = conn.createStatement();
		String query = "SELECT * FROM User";
		ResultSet res = statement.executeQuery(query);
	
		return res;
	}
	
	public void login(String username, String password, User user) throws Exception {
		
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE username = ? LIMIT 1;");
		statement.setString(1, username);
		ResultSet res = statement.executeQuery();
		
		while (res.next()) {
			// Encrypted and Base64 encoded password read from database
	        String securePassword = res.getString("password");
	        
	        // Salt value stored in database 
	        String salt = res.getString("salt");
	        
	        boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);
	        
	        if (! passwordMatch) {
	        	// TODO: sistemare questo throw
	        	throw new Exception();
	        }
	        
	        user.setUsername(res.getString("username"));
		}
		
		res.close();
		
	}
	
	public void registerNewUser(String username, String password, String salt) throws Exception {

		PreparedStatement statement = conn.prepareStatement("INSERT INTO User (username, password, salt) VALUES (?,?,?);");
		statement.setString(1, username);
		statement.setString(2, password);
		statement.setString(3, salt);
		int res = statement.executeUpdate();
		if (res == 0) {
			// TODO: sistemare questo throw
			throw new Exception();
		}
	}
	
}
