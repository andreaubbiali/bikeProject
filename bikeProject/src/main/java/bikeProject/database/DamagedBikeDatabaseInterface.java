package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.User;

import java.sql.SQLException;

public interface DamagedBikeDatabaseInterface {

    public long addNewDamage(String message, long rentID) throws SQLException;
}