package bikeProject.database;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;
import bikeProject.dataservice.TotemRack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

    public List<TotemRack> getAllRacks() throws SQLException {

        List<TotemRack> rackList = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM rack ;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            TotemRack rack = new TotemRack();

            rack.setID(res.getLong("id"));
            rack.setAddress(res.getString("address"));

            rackList.add(rack);
        }

        res.close();

        return rackList;
    }
}