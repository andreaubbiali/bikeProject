package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.User;

import java.sql.SQLException;
import java.util.Date;

public class RentDatabase implements RentDatabaseInterface {
    public void createRent(User user, Bike bike, Date startDate) {
    }

    public BikeType getBikeTypeByUserEmail(String email) throws SQLException {
        return new BikeType();
    }
}