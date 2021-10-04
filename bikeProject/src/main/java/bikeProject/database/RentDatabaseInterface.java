package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.Rent;
import bikeProject.dataservice.Subscription;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RentDatabaseInterface {
    long createRent(Bike bike, LocalDateTime startDate, Subscription subscription) throws SQLException;

    List<Rent> getRentFromSubscriptionID(long subscriptionID) throws SQLException;

    void updateRentEndDate(Rent rent) throws SQLException;

    List<Rent> getAllRents() throws SQLException;
}