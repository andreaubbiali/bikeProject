package bikeProject.database;

import bikeProject.dataservice.Bike;

import java.sql.SQLException;

public interface BikeDatabaseInterface {

    public void updateIsInMaintenance(Bike bike) throws SQLException;
}