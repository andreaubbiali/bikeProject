package bikeProject.database;

import bikeProject.dataservice.TotemRack;

import java.sql.SQLException;
import java.util.List;

public interface TotemRackDatabaseInterface {

    long addNewRack(String address) throws SQLException;

    List<TotemRack> getAllRacks() throws SQLException;
}