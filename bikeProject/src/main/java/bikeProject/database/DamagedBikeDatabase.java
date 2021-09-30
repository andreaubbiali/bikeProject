package bikeProject.database;

import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.BikeTypeEnum;
import bikeProject.dataservice.DamageMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public DamageMessage getDamageByBikeID(long bikeID) throws SQLException {
        DamageMessage damageMessage = new DamageMessage();

        PreparedStatement statement =
                Database.getConn().prepareStatement("SELECT * FROM damage d INNER JOIN rent r " + "ON d.rent_id = r" + ".id INNER JOIN bike b ON b.id = r.bike_id AND b.id = ?;");
        statement.setLong(1, bikeID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            damageMessage.setID(res.getLong("id"));
            damageMessage.setMessage(res.getString("message"));
        }

        res.close();

        return damageMessage;
    }

    public void deleteMessage(long bikeID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("DELETE from damage " + "WHERE damage" +
                ".rent_id = (" + "SELECT r.id" + " from rent r" + " inner join bike b on b.id = r.bike_id and b" +
                ".id" + " = ?" + ") ");
        statement.setLong(1, bikeID);

        // execute query
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException("Cannot delete damage");
        }

        statement.close();
    }
}