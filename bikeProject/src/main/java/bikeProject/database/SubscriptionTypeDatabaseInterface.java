package bikeProject.database;

import bikeProject.dataservice.SubscriptionType;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionTypeDatabaseInterface {

    SubscriptionType getSubscriptionTypeByID(long id) throws SQLException;

    List<SubscriptionType> getAllSubscriptionTypes() throws SQLException;
}