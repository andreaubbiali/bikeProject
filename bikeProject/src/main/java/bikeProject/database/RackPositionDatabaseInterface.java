package bikeProject.database;

import java.sql.SQLException;

public interface RackPositionDatabaseInterface {
    void updateIsBroken(boolean isBroken) throws SQLException;
}