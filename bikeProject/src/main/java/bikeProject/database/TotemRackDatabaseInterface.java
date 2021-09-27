package bikeProject.database;

import bikeProject.dataservice.RackPosition;
import bikeProject.dataservice.TotemRack;

import java.sql.SQLException;
import java.util.List;

public interface TotemRackDatabaseInterface {

    long addNewRack(String address) throws SQLException;

    void delete(long ID) throws SQLException;

    List<TotemRack> getAllRacks() throws SQLException;

    TotemRack getRackByID(long ID) throws SQLException;

    void updateRackAddress(long ID, String address) throws SQLException;

    List<RackPosition> getRackPositionsByRackID(long ID) throws SQLException;
}