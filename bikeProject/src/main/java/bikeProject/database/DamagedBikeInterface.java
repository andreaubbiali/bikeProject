package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.User;

import java.sql.SQLException;

public interface DamagedBikeInterface {

    public void addNewDamage(Bike bike, User user, String message) throws SQLException;
}