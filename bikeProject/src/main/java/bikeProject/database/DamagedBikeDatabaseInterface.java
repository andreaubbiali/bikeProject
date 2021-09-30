package bikeProject.database;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.DamageMessage;
import bikeProject.dataservice.User;

import java.sql.SQLException;

public interface DamagedBikeDatabaseInterface {

    long addNewDamage(String message, long rentID) throws SQLException;

    DamageMessage getDamageByBikeID(long bikeID) throws SQLException;

    void deleteMessage(long bikeID) throws SQLException;
}