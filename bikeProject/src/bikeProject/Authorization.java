package bikeProject;

import java.sql.Statement;
import java.sql.ResultSet;

public class Authorization implements Dataservice {
	
	public Authorization() {
	}
	
//	public Authorization(Connection conn) {
//		this.conn = conn;
//	}
	
	public boolean login(String username, String password) {
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT * FROM User";
			ResultSet res = statement.executeQuery(query);
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
	
}
