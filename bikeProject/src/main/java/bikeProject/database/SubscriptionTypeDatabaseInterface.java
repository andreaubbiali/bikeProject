package bikeProject.database;

import bikeProject.dataservice.SubscriptionType;

import java.sql.SQLException;

public interface SubscriptionTypeDatabaseInterface {

    SubscriptionType getTypeByID(long id) throws SQLException;
}