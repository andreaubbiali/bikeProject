package bikeProject.database;

import bikeProject.dataservice.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface RentDatabaseInterface {
    long createRent(Bike bike, Date startDate, Subscription subscription) throws SQLException;

    List<Rent> getRentFromSubscriptionID(long subscriptionID) throws SQLException;

    void updateRentEndDate(Rent rent) throws SQLException;
}