package bikeProject.database;

import java.sql.SQLException;

public interface RackPositionDatabaseInterface {
    void updateIsBroken(boolean isBroken) throws SQLException;

    void addRackPositions(long rackID, int numberPositions, String bikeType) throws SQLException;
}