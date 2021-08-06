package bikeProject.database;

import bikeProject.dataservice.Tariff;

import java.sql.SQLException;
import java.util.List;

public interface TariffDatabaseInterface {

    List<Tariff> getTariffsByBikeTypeID(long id) throws SQLException;

}