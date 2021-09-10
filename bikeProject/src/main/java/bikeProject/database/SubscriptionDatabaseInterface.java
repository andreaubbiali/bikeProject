package bikeProject.database;

import java.sql.SQLException;
import java.util.List;

import bikeProject.dataservice.Subscription;

public interface SubscriptionDatabaseInterface {

    long createNewSubscription(Subscription subscription) throws SQLException;

    void updateSubscription(Subscription subscription) throws SQLException;

    void setSubscriptionDateNow() throws SQLException;

    List<Subscription> getSubscriptionByUserID(long id) throws SQLException;
}