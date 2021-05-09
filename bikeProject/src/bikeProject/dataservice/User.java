package bikeProject.dataservice;

import bikeProject.PasswordUtils;


public class User implements Dataservice_Interface {
	
	// public final /*@ not_null @*/ long ID;
	public /*@ not_null @ */ String username;
	
//	public User(User user) {
//		this.username = user.username;
//	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/*
	 * @PRE the user is registered but not authenticated to the application
	 * @POST the use is authenticated 
	 */
	public boolean login(String username, String password) {
		
		System.out.println("stampa di this.username prima    " + this.username + " fine");
		
		try {
			userDB.login(username, password, this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("stampa di this.username dopo    " + this.username + " fine");
		
		return false;
		
	}
	
	public void registerNewUser(String username, String password) {
		
        
        // Generate Salt. The generated value can be stored in DB. 
        String salt = PasswordUtils.getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
        
        try {
        	userDB.registerNewUser(username, mySecurePassword, salt);
        }catch(Exception e ) {
        	e.printStackTrace();
        }
        
        System.out.println("New user registered: " + this.username);
        
	}
	
}
