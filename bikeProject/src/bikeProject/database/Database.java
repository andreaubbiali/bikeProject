package bikeProject.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database implements Database_Interface {

	public Connection conn;

	public Database() {
		createConnection();
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void createConnection() {
		try {
			this.conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.178.35:3306/bikeProject?user=bikeProject&password=bikeProject");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Connected to db: bikeProject");
	}

}
