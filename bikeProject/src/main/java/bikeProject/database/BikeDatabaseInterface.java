package bikeProject.database;

import bikeProject.dataservice.Bike;

import java.sql.SQLException;

public interface BikeDatabaseInterface {

    void updateBike(Bike bike) throws SQLException;

    long createBike(long bikeTypeID) throws SQLException;

    void delete(long bikeID) throws SQLException;

    void fixBike(long bikeID) throws SQLException;
}