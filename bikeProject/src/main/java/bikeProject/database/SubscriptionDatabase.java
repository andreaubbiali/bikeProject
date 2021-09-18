package bikeProject.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bikeProject.PasswordUtils;
import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;
import bikeProject.dataservice.User;
import bikeProject.exception.WrongPasswordException;

public class SubscriptionDatabase extends Database implements SubscriptionDatabaseInterface {

    public long createNewSubscription(Subscription subscription) throws SQLException {

        // prepare the statement
        PreparedStatement statement = conn.prepareStatement("INSERT INTO subscription (subscription_type_id, " +
                "subscription_date, user_id, count_exceeded_time, start_date, deleted) VALUES " + "(?,?,?,?,?,?);");
        statement.setLong(1, subscription.getType().getID());
        statement.setDate(2, (Date) subscription.getSubscriptionDate());
        statement.setLong(3, subscription.getUser().getID());
        statement.setInt(5, subscription.getCountExceededTime());
        if ( subscription.getStartDate() == null ) {
            statement.setNull(6, java.sql.Types.NULL);
        } else {
            statement.setDate(6, (Date) subscription.getStartDate());
        }
        statement.setBoolean(7, subscription.isDeleted());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Register user failed, no rows affected.");
        }

        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Register user failed, no ID obtained.");
            }
        }
    }

    public void updateSubscription(Subscription subscription) throws SQLException {

        // prepare the statement
        PreparedStatement statement = conn.prepareStatement("UPDATE subscription SET (subscription_type_id = ?, " +
                "subscription_date = ?, user_id = ?, count_exceeded_time = ?, start_date = ?, deleted = ?) WHERE id " + "=" + " ?");
        statement.setLong(1, subscription.getType().getID());
        statement.setDate(2, (Date) subscription.getSubscriptionDate());
        statement.setLong(3, subscription.getUser().getID());
        statement.setInt(5, subscription.getCountExceededTime());
        if ( subscription.getStartDate() == null ) {
            statement.setNull(6, java.sql.Types.NULL);
        } else {
            statement.setDate(6, (Date) subscription.getStartDate());
        }
        statement.setBoolean(7, subscription.isDeleted());
        statement.setLong(7, subscription.getID());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Update user failed, no rows affected.");
        }

    }

    public void setSubscriptionStartDateNow(Subscription subscription) throws SQLException {

        // prepare the statement
        PreparedStatement statement =
                conn.prepareStatement("UPDATE subscription SET (start_date = ?) " + "WHERE id " + "=" + " ?");

        statement.setDate(1, (Date) subscription.getStartDate());
        statement.setLong(2, subscription.getID());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Update user failed, no rows affected.");
        }

    }

    public List<Subscription> getSubscriptionByUserID(long id) throws SQLException {
        List<Subscription> subscriptionList = new ArrayList<Subscription>();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM subscription WHERE user_id = ?;");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            Subscription tempSub = new Subscription();

            tempSub.setID(res.getLong("id"));
            tempSub.setSubscriptionDate(res.getDate("subscription_date"));
            tempSub.setCountExceededTime(res.getInt("count_exceeded_time"));
            tempSub.setStartDate(res.getDate("start_date"));
            tempSub.setDeleted(res.getBoolean("deleted"));

            // add the subType
            SubscriptionType subType = new SubscriptionType();
            subType.setID(res.getLong("subscription_type_id"));
            tempSub.setType(subType);

            subscriptionList.add(tempSub);
        }

        res.close();

        return subscriptionList;
    }

}