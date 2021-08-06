package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.Rent;
import bikeProject.dataservice.User;

import java.sql.SQLException;
import java.util.Date;

public interface RentDatabaseInterface {
    void createRent(User user, Bike bike, Date startDate);

    Rent getRentByEmail(String email) throws SQLException;

    void updateRent(Rent rent) throws SQLException;
}