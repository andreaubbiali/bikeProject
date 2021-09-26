package bikeProject.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TotemRackDatabase implements TotemRackDatabaseInterface {

    public long addNewRack(String address) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO rack (address) VALUES (?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, address);

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Register new rack failed, no rows affected.");
        }

        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Register new rack failed, no ID obtained.");
            }
        }
    }
}