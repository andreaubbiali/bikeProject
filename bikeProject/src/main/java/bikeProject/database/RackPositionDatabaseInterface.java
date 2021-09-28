package bikeProject.database;

import java.sql.SQLException;

public interface RackPositionDatabaseInterface {
    void updateIsBroken(long ID, boolean isBroken) throws SQLException;

    void addRackPositions(long rackID, int numberPositions, String bikeType) throws SQLException;

    void addBike(long rackPositionID, long bikeID) throws SQLException;

    void setBikeNull(long rackID) throws SQLException;
}