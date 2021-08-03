package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bikeProject.PasswordUtils;
import bikeProject.dataservice.User;
import bikeProject.exception.UserNotFoundException;

public class UserDatabase extends Database implements UserDatabaseInterface {

    public User login(String email, String password) throws SQLException {
        User user = new User();
        return user;
    }

    public int registerNewUser(String name, String surname, String email, boolean isStudent, String password, String salt) throws SQLException {
        return 4;
    }

    public boolean checkPassword(long id, String password) throws SQLException {
        return true;
    }

	/*public ResultSet getUser() throws Exception {

		Statement statement = conn.createStatement();
		String query = "SELECT * FROM user";
		ResultSet res = statement.executeQuery(query);

		return res;
	}

	public void login(String username, String password, User user) throws Exception {

		PreparedStatement statement = conn.prepareStatement("SELECT * FROM user WHERE username = ? LIMIT 1;");
		statement.setString(1, username);
		ResultSet res = statement.executeQuery();

		while (res.next()) {
			// Encrypted and Base64 encoded password read from database
			String securePassword = res.getString("password");

			// Salt value stored in database
			String salt = res.getString("salt");

			boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword, salt);

			if (!passwordMatch) {
				// TODO: sistemare questo throw
				throw new Exception();
			}

			user.setUsername(res.getString("username"));
		}

		res.close();

	}

	public void registerNewUser(String name, String surname, String email, String username, String password,
			String salt) throws Exception {

		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO user (name, surname, username, email, password, salt) VALUES (?,?,?,?,?,?);");
		statement.setString(1, name);
		statement.setString(2, surname);
		statement.setString(3, username);
		statement.setString(4, email);
		statement.setString(5, password);
		statement.setString(6, salt);
		int res = statement.executeUpdate();
		if (res == 0) {
			// TODO: sistemare questo throw
			throw new Exception();
		}
	}*/

}