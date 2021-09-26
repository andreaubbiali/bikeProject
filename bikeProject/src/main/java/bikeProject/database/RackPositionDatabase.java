package bikeProject.database;

import bikeProject.dataservice.BikeType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RackPositionDatabase implements RackPositionDatabaseInterface {

    public void updateIsBroken(boolean isBroken) throws SQLException {

    }

    public void addRackPositions(long rackID, int numberPositions, String bikeType) throws SQLException {

        for ( int i = 0; i < numberPositions; i++ ) {

            // prepare the statement
            PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO rack_position " +
                    "(accepted_bike_type_id, rack_id) VALUES ((SELECT id FROM bike_type WHERE type = ?),?);");
            statement.setString(1, bikeType);
            statement.setLong(2, rackID);

            // execute the query
            int res = statement.executeUpdate();
            if ( res == 0 ) {
                throw new SQLException("Rack not added, no rows affected.");
            }

        }

    }
}