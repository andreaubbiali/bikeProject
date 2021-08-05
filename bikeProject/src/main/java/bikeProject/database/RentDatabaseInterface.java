package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.User;

import java.sql.SQLException;
import java.util.Date;

public interface RentDatabaseInterface {
    void createRent(User user, Bike bike, Date startDate);

    BikeType getBikeTypeByUserEmail(String email) throws SQLException;
}