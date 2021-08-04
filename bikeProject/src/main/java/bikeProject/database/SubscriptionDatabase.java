package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;

public class SubscriptionDatabase extends Database implements SubscriptionDatabaseInterface {

	public long createNewSubscription(Subscription subscription) throws SQLException {
		return 5;
	}

	public void setSubscriptionDateNow() throws SQLException {

	}

	/*public void getSubscriptionByUniqueCode(String uniqueCode, Subscription subscription) throws SQLException {

		SubscriptionType subType = new SubscriptionType();

		PreparedStatement statement = conn.prepareStatement(
				"SELECT S.id as subscription_id, S.unique_code, S.user_id, S.subscription_date, S.start_date"
						+ "ST.id as type_id, ST.type, ST.price, ST.days_duration, ST.must_start_in"
						+ "FROM subscription S \n" + "INNER JOIN subscription_type ST ON S.type_id = ST.id "
						+ "WHERE S.unique_code = 'sdasd' LIMIT 1;");

		statement.setString(1, uniqueCode);
		ResultSet res = statement.executeQuery();

		while (res.next()) {
			// get from result and set subscription type values
			subType.setID(res.getInt("type_id"));
			subType.setType(res.getString("type"));
			subType.setPrice(res.getFloat("price"));
			subType.setDaysDuration(res.getInt("days_duration"));
			subType.setMustStartIn(res.getInt("must_start_in"));

			// get from result and set subscription values
			subscription.setID(res.getInt("subscription_id"));
			subscription.setType(subType);
			subscription.setUniqueCode(res.getString("unique_code"));
			subscription.setUserID(res.getInt("user_id"));
			subscription.setSubscriptionDate(res.getDate("subscription_date"));

			if (res.getDate("start_date") != null) {
				subscription.setStartDate(res.getDate("start_date"));
			}

		}

		res.close();

	}*/

}