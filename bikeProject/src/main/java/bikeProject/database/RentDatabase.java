package bikeProject.database;

import bikeProject.dataservice.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentDatabase implements RentDatabaseInterface {
    public long createRent(Bike bike, Date startDate, Subscription subscription) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("INSERT INTO rent (bike_id, " +
                "start_date, subscription_id) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, bike.getID());
        statement.setObject(2, startDate);
        statement.setLong(3, subscription.getID());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Register user failed, no rows affected.");
        }

        try ( ResultSet generatedKeys = statement.getGeneratedKeys() ) {
            if ( generatedKeys.next() ) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Register user failed, no ID obtained.");
            }
        }
    }

    public List<Rent> getRentFromSubscriptionID(long subscriptionID) throws SQLException {
        List<Rent> rentList = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT rent.*, damage.id as " + "damageID,"
                + " damage.message as damageMessage, bike.id as bikeID, bike.is_in_maintenance as " +
                "bikeIsInMaintenance, bike_type.id as bikeTypeID, bike_type.type as bikeTypeName, bike_type" +
                ".baby_seat" + " as babySeat FROM rent LEFT JOIN " + "damage ON damage.rent_id = rent" + ".id INNER " + "JOIN bike " + "ON bike.id = rent.bike_id INNER JOIN bike_type ON bike_type.id = bike.type_id WHERE " + "subscription_id " + "=" + " ?;");
        statement.setLong(1, subscriptionID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            Rent rentTemp = new Rent();

            rentTemp.setID(res.getLong("id"));
            rentTemp.setStartDate(res.getDate("start_date"));
            if ( res.getObject("end_date") != null ) {
                rentTemp.setEndDate(res.getDate("end_date"));
            }

            // set damage
            DamageMessage damage = new DamageMessage();
            damage.setID(res.getLong("damageID"));
            damage.setMessage(res.getString("damageMessage"));
            rentTemp.setDamageMessage(damage);

            // set bike
            Bike bike = new Bike();
            bike.setID(res.getLong("bikeID"));
            bike.setIsInMaintenance(res.getBoolean("bikeIsInMaintenance"));
            // bikeType
            BikeType bikeType = new BikeType();
            bikeType.setID(res.getLong("bikeTypeID"));
            bikeType.setType(BikeTypeEnum.valueOf(res.getString("bikeTypeName")));
            bikeType.setBabySeat(res.getBoolean("babySeat"));

            bike.setType(bikeType);
            rentTemp.setBike(bike);

            rentList.add(rentTemp);
        }

        res.close();

        return rentList;
    }

    public void updateRentEndDate(Rent rent) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE rent SET end_date = ? WHERE id = ?");
        statement.setObject(1, rent.getEndDate());
        statement.setLong(2, rent.getID());

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Update rent failed, no rows affected.");
        }

    }
}