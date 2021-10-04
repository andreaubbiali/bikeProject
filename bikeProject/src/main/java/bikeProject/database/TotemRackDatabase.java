package bikeProject.database;

import bikeProject.dataservice.*;

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

    public void delete(long ID) throws SQLException {

        PreparedStatement statement = Database.getConn().prepareStatement("DELETE FROM rack WHERE id = ? AND (SELECT "
                + "count(rp.id) from rack_position rp where rp.rack_id = 4 and rp.bike_id is not null) = 0");
        statement.setLong(1, ID);

        // execute query
        int res = statement.executeUpdate();

        if ( res != 1 ) {
            throw new SQLException("error the rack has not been deleted");
        }

        statement.close();

    }

    public List<TotemRack> getAllRacks() throws SQLException {

        List<TotemRack> rackList = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM rack ;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            TotemRack rack = new TotemRack();

            rack.setID(res.getLong("id"));
            rack.setAddress(res.getString("address"));
            rack.setRackPositionList(getRackPositionsByRackID(res.getLong("id")));

            rackList.add(rack);
        }

        res.close();

        return rackList;
    }

    public TotemRack getRackByID(long ID) throws SQLException {
        TotemRack totemRack = new TotemRack();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM rack WHERE id = ?;");
        statement.setLong(1, ID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {

            totemRack.setID(res.getLong("id"));
            totemRack.setAddress(res.getString("address"));
            totemRack.setRackPositionList(getRackPositionsByRackID(res.getLong("id")));

        }

        res.close();

        return totemRack;
    }

    public void updateRackAddress(long ID, String address) throws SQLException {

        // prepare the statement
        PreparedStatement statement = Database.getConn().prepareStatement("UPDATE rack SET address = ? WHERE id " +
                "= ?");
        statement.setString(1, address);
        statement.setLong(2, ID);

        // execute the query
        int res = statement.executeUpdate();
        if ( res == 0 ) {
            throw new SQLException("Update rack failed, no rows affected.");
        }
    }

    public List<RackPosition> getRackPositionsByRackID(long ID) throws SQLException {
        List<RackPosition> rackPositions = new ArrayList<>();

        PreparedStatement statement =
                Database.getConn().prepareStatement("SELECT rp.id as rackID, rp.is_broken as " + "rackIsBroken, b.id "
                        + "as bikeID, b.is_in_maintenance as bikeIsInMaintenance,\n" + "btb.id as " + "bikeTypeID, " + "btb" + ".baby_seat as bikeTypeBabySeat, btb.`type` as bikeType, bt.id as bikeAcceptedTypeID," + " " + "bt" + ".baby_seat as bikeAcceptedTypeBabySeatID, bt.`type` as bikeAcceptedType\n" + "FROM " + "rack_position " + "rp\n" + "LEFT JOIN bike b ON rp.bike_id = b.id\n" + "LEFT JOIN " + "bike_type " + "btb ON b.type_id = btb.id " + "\n" + "INNER JOIN bike_type bt ON bt.id=rp" + ".accepted_bike_type_id \n" + "WHERE rp.rack_id = ?;");

        statement.setLong(1, ID);
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            RackPosition rackTmp = new RackPosition();

            rackTmp.setID(res.getLong("rackID"));
            rackTmp.setIsBroken(res.getBoolean("rackIsBroken"));

            // set bike
            Bike bike = new Bike();
            BikeType bt = new BikeType();
            if ( res.getLong("bikeID") != 0 ) {
                bike.setIsInMaintenance(res.getBoolean("bikeIsInMaintenance"));
                bike.setID(res.getLong("bikeID"));
                bt.setID(res.getLong("bikeTypeID"));
                bt.setType(BikeTypeEnum.valueOf(res.getString("bikeType")));
                bt.setBabySeat(res.getBoolean("bikeTypeBabySeat"));
                bike.setType(bt);
                rackTmp.setBike(bike);
            }

            // set accepted bike type
            bt.setID(res.getLong("bikeAcceptedTypeID"));
            bt.setType(BikeTypeEnum.valueOf(res.getString("bikeAcceptedType")));
            bt.setBabySeat(res.getBoolean("bikeAcceptedTypeBabySeatID"));
            rackTmp.setAcceptedBikeType(bt);

            rackPositions.add(rackTmp);
        }

        res.close();

        return rackPositions;
    }
}