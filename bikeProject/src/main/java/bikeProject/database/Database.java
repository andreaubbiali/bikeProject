package bikeProject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database database = null;
    private static Connection conn;

    private Database() {
        createConnection();
    }

    public static Database getInstance() {
        if ( database == null ) {
            database = new Database();
        }

        return database;
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bikeProject?user=bikeProject" +
                    "&password" + "=8bikeProject8*");
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        System.out.println("Connected to db: bikeProject");
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Database closed.");
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        Database.conn = conn;
    }

}