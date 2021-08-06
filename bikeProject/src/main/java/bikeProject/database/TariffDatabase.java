package bikeProject.database;

import bikeProject.dataservice.Tariff;

import java.sql.SQLException;
import java.util.*;

public class TariffDatabase implements TariffDatabaseInterface{

    public List<Tariff> getTariffsByBikeTypeID(long id) throws SQLException{
        List<Tariff> listTariff = new ArrayList<>();
        return listTariff;
    }
}