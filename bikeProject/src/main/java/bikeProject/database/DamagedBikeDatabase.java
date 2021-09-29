package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DamagedBikeDatabase implements DamagedBikeDatabaseInterface {

    public long addNewDamage(String message, long rentID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO damage (message, rent_id) " +
                "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, message);
        statement.setLong(2, rentID);

        // execute the query
        statement.execute();

        // get the id
        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Register damage failed, no ID obtained.");
            }
        }
    }
}