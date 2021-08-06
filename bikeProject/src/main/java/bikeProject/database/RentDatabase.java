package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.Rent;
import bikeProject.dataservice.User;

import java.sql.SQLException;
import java.util.Date;

public class RentDatabase implements RentDatabaseInterface {
    public void createRent(User user, Bike bike, Date startDate) {
    }

    public Rent getRentByEmail(String email) throws SQLException {
        return new Rent();
    }

    public void updateRent(Rent rent) throws SQLException{}
}