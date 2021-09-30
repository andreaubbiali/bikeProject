package bikeProject.database;

import bikeProject.dataservice.Bike;

import java.sql.*;

public class BikeDatabase implements BikeDatabaseInterface {

    public void updateBike(Bike bike) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE bike SET is_in_maintenance = ? " +
                "WHERE id = ?; ");

        statement.setBoolean(1, bike.isInMaintenance());
        statement.setLong(2, bike.getID());
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException();
        }

        statement.close();

    }

    public long createBike(long bikeTypeID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO bike (type_id) VALUES (?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, bikeTypeID);

        // execute the query
        statement.execute();

        // get the id
        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Register bike failed, no ID obtained.");
            }
        }
    }

    public void delete(long bikeID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("DELETE FROM bike WHERE id = ?");
        statement.setLong(1, bikeID);

        // execute query
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException("Cannot delete bike");
        }

        statement.close();
    }

    public void fixBike(long bikeID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE bike SET is_in_maintenance = false "
                + "WHERE id = ?; ");

        statement.setLong(1, bikeID);
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException();
        }

        statement.close();
    }
}