package bikeProject.database;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;
import bikeProject.dataservice.User;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDatabase implements SubscriptionDatabaseInterface {

    public long createNewSubscription(Subscription subscription) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO subscription " +
                "(subscription_type_id, subscription_date, user_id, count_exceeded_time, start_date, deleted) " +
                "VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, subscription.getType().getID());
        statement.setObject(2, subscription.getSubscriptionDate());
        statement.setLong(3, subscription.getUser().getID());
        statement.setInt(4, subscription.getCountExceededTime());
        if ( subscription.getStartDate() == null ) {
            statement.setNull(5, java.sql.Types.NULL);
        } else {
            statement.setObject(5, subscription.getStartDate());
        }
        statement.setBoolean(6, subscription.isDeleted());

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
        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE subscription SET " +
                "subscription_type_id = ?, " + "subscription_date = ?, user_id = ?, count_exceeded_time = ?, " +
                "start_date = ?, deleted = ? WHERE id " + "=" + " ?");
        statement.setLong(1, subscription.getType().getID());
        statement.setObject(2, subscription.getSubscriptionDate());
        statement.setLong(3, User.getID());
        statement.setInt(4, subscription.getCountExceededTime());
        if ( subscription.getStartDate() == null ) {
            statement.setNull(5, java.sql.Types.NULL);
        } else {
            statement.setObject(5, subscription.getStartDate());
        }
        statement.setBoolean(6, subscription.isDeleted());
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
                Database.getConn().prepareStatement("UPDATE subscription SET start_date = ? " + "WHERE id = ?");

        statement.setObject(1, subscription.getStartDate());
        statement.setLong(2, subscription.getID());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Update user failed, no rows affected.");
        }

    }

    public List<Subscription> getSubscriptionByUserID(long id) throws SQLException {
        List<Subscription> subscriptionList = new ArrayList<Subscription>();

        PreparedStatement statement =
                Database.getConn().prepareStatement("SELECT * FROM subscription WHERE user_id " + "=" + " ?;");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            Subscription tempSub = new Subscription();

            tempSub.setID(res.getLong("id"));
            tempSub.setSubscriptionDate(res.getTimestamp("subscription_date").toLocalDateTime());
            tempSub.setCountExceededTime(res.getInt("count_exceeded_time"));
            if ( res.getObject("start_date") != null ) {
                tempSub.setStartDate(res.getTimestamp("start_date").toLocalDateTime());
            }
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

    public List<Subscription> getAllSubscription() throws SQLException {
        List<Subscription> subscriptionList = new ArrayList<Subscription>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM subscription;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            Subscription tempSub = new Subscription();

            tempSub.setID(res.getLong("id"));
            tempSub.setSubscriptionDate(res.getTimestamp("subscription_date").toLocalDateTime());
            tempSub.setCountExceededTime(res.getInt("count_exceeded_time"));
            if ( res.getObject("start_date") != null ) {
                tempSub.setStartDate(res.getTimestamp("start_date").toLocalDateTime());
            }
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