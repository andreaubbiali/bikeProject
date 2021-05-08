package bikeProject.dataservice;

import java.sql.Statement;

import bikeProject.PasswordUtils;

import java.sql.ResultSet;

public class User_Dataservice implements Dataservice {
	
	// public final /*@ not_null @*/ long ID;
	public /*@ not_null @ */ String username;
	// private String password;
	
//	public User(String username){
//		this.ID = 1;
//		login(username, "aa");
//	}
	
	/*
	 * @PRE the user is registered but not authenticated to the application
	 * @POST the use is authenticated 
	 */
	public boolean login(String username, String password) {

		try {
			ResultSet res = userDB.getUser();
			
			while (res.next()) {
				if (res.getString("username").equals(username) && res.getString("password").equals(password)) {
					// crea un nuovo user
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void registerNewUser() {
		
		String myPassword = "myPassword123";
        
        // Generate Salt. The generated value can be stored in DB. 
        String salt = PasswordUtils.getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
        
        // Print out protected password 
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);
        
	}
	
}
