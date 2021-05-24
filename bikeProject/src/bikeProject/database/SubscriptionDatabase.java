package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bikeProject.dataservice.User;

public class SubscriptionDatabase extends Database implements SubscriptionDatabaseInterface {

	public User controlUniqueCodeOfAUser(String uniqueCode) throws SQLException {
		User user = new User();

		PreparedStatement statement = conn.prepareStatement(
				"" + "SELECT username " + "FROM subscription INNER JOIN user ON user.id = subscription.user_id"
						+ "WHERE subscription.unique_code = ?;");

		statement.setString(1, uniqueCode);
		ResultSet res = statement.executeQuery();

		while (res.next()) {

			user.setUsername(res.getString("username"));

		}

		res.close();

		return user;

	}

}
