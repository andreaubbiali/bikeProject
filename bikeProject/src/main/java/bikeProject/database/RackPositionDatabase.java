package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RackPositionDatabase implements RackPositionDatabaseInterface {

    public void updateIsBroken(long ID, boolean isBroken) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE rack_position SET is_broken = ? " +
                "WHERE id = ?; ");

        statement.setBoolean(1, isBroken);
        statement.setLong(2, ID);

        // execute the query
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException();
        }

        statement.close();
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

    public void addBike(long rackPositionID, long bikeID) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE rack_position SET bike_id = ? " +
                "WHERE id = ?");
        statement.setLong(1, bikeID);
        statement.setObject(2, rackPositionID);

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Add bike failed, no rows affected.");
        }
    }
}