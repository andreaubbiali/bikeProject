package bikeProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database implements Database_Interface {
	
	public Connection conn; 
	
	public Database() {
		try {
			System.out.println("connessione con il db");
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bikeProject?user=root&password=aeri2aicee5Oyo1euQuai7niexa8choo");
			System.out.println("conenssione eseguita");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Database(Connection conn) {
		this.conn = conn;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public void createConnection() {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bikeProject?user=root&password=aeri2aicee5Oyo1euQuai7niexa8choo");
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Connected to db: bikeProject");
	}
	
}
