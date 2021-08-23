package bikeProject.database;

import bikeProject.dataservice.Bike;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BikeDatabase extends Database implements BikeDatabaseInterface {

    public void updateBike(Bike bike) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE bike(is_in_maintenance) VALUES(?) WHERE id = ?; ");

        statement.setBoolean(1, bike.isInMaintenance());
        statement.setLong(2, bike.getID());
        int res = statement.executeUpdate();
        
        if ( res != 1 ) {
            throw new SQLException();
        }

        statement.close();

    }
}