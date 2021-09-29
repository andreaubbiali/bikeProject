package bikeProject.database;

import bikeProject.dataservice.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class TariffDatabase implements TariffDatabaseInterface {

    public List<Tariff> getAllTariffs() throws SQLException {

        List<Tariff> tariffList = new ArrayList<>();

        PreparedStatement statement = Database.getConn().prepareStatement("SELECT t.id, t.time_in_minutes, t.tariff, "
                + "b.id as bikeID, b.baby_seat, b.type FROM bike_tariff t INNER JOIN " + "bike_type b ON b.id = t" +
                ".bike_type_id;");
        ResultSet res = statement.executeQuery();

        while ( res.next() ) {
            Tariff tariffTmp = new Tariff();

            tariffTmp.setID(res.getLong("id"));
            tariffTmp.setPassedTimeInMinutes(res.getInt("time_in_minutes"));
            tariffTmp.setTariff(res.getFloat("tariff"));

            // add the bikeType
            BikeType bikeType = new BikeType();
            bikeType.setID(res.getLong("bikeID"));
            bikeType.setBabySeat(res.getBoolean("baby_seat"));
            bikeType.setType(BikeTypeEnum.valueOf(res.getString("type")));
            tariffTmp.setBikeType(bikeType);

            tariffList.add(tariffTmp);
        }

        res.close();

        return tariffList;
    }
}