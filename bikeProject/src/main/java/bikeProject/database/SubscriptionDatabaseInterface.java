package bikeProject.database;

import java.sql.SQLException;

import bikeProject.dataservice.Subscription;

public interface SubscriptionDatabaseInterface {

    long createNewSubscription(Subscription subscription) throws SQLException;

    void updateSubscription(Subscription subscription) throws SQLException;

    void setSubscriptionDateNow() throws SQLException;
}