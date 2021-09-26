package bikeProject.database;

import java.sql.SQLException;

public interface TotemRackDatabaseInterface {

    long addNewRack(String address) throws SQLException;
}