package bikeProject.database;

import bikeProject.dataservice.BikeType;

import java.sql.SQLException;
import java.util.List;

public interface BikeTypeDatabaseInterface {

    List<BikeType> getBikeTypes() throws SQLException;
}