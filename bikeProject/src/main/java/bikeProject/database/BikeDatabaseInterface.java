package bikeProject.database;

import bikeProject.dataservice.Bike;

import java.sql.SQLException;

public interface BikeDatabaseInterface {

    void updateBike(Bike bike) throws SQLException;
}