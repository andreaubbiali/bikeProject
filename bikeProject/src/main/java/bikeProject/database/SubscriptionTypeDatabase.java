package bikeProject.database;

import bikeProject.dataservice.SubscriptionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionTypeDatabase extends Database implements SubscriptionTypeDatabaseInterface {

    public SubscriptionType getTypeByID(long id) throws SQLException {

        SubscriptionType subType = new SubscriptionType();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM subscription_type WHERE id = ? LIMIT 1;");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {

            subType.setID(res.getLong("id"));
            subType.setType(res.getString("type"));
            subType.setPrice(res.getInt("price"));
            subType.setDaysDuration(res.getInt("days_duration"));
            subType.setMustStartIn(res.getInt("must_start_in"));

        }

        res.close();

        return subType;
    }

}