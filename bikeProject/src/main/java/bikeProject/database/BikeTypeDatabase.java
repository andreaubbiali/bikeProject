package bikeProject.database;

import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.BikeTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BikeTypeDatabase implements BikeTypeDatabaseInterface {

    public List<BikeType> getBikeTypes() throws SQLException {

        List<BikeType> bikeTypes = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT * FROM bike_type;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            BikeType bikeType = new BikeType();

            bikeType.setID(res.getLong("id"));
            bikeType.setBabySeat(res.getBoolean("baby_seat"));
            bikeType.setType(BikeTypeEnum.valueOf(res.getString("type")));

            bikeTypes.add(bikeType);
        }

        res.close();

        return bikeTypes;
    }
}