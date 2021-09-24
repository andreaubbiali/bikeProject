package bikeProject.database;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionTypeDatabase implements SubscriptionTypeDatabaseInterface {

    public SubscriptionType getSubscriptionTypeByID(long id) throws SQLException {

        SubscriptionType subType = new SubscriptionType();

        PreparedStatement statement =
                Database.getConn().prepareStatement("SELECT * FROM subscription_type WHERE id " + "=" + " ? LIMIT 1;");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {

            subType.setID(res.getLong("id"));
            subType.setName(res.getString("name"));
            subType.setPrice(res.getInt("price"));
            subType.setDaysDuration(res.getInt("days_duration"));
            subType.setMustStartIn(res.getInt("must_start_in"));

        }

        res.close();

        return subType;
    }

    public List<SubscriptionType> getAllSubscriptionTypes() throws SQLException {
        List<SubscriptionType> subTypes = new ArrayList<SubscriptionType>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM subscription_type;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            SubscriptionType subType = new SubscriptionType();

            subType.setID(res.getLong("id"));
            subType.setName(res.getString("name"));
            subType.setPrice(res.getInt("price"));
            subType.setDaysDuration(res.getInt("days_duration"));
            subType.setMustStartIn(res.getInt("must_start_in"));

            subTypes.add(subType);
        }

        res.close();

        return subTypes;
    }

}