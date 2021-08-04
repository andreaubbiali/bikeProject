package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.User;

import java.util.Date;

public interface RentDatabaseInterface {
    void createRent(User user, Bike bike, Date startDate);
}