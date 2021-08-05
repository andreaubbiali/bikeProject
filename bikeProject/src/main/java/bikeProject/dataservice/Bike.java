package bikeProject.dataservice;

import java.sql.SQLException;

public class Bike implements DataserviceInterface {
    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ BikeType type;
    private /* @ not_null @ */ boolean isInMaintenance;

    public void setInMaintenance(boolean isInMaintenance) throws SQLException {

        this.isInMaintenance = isInMaintenance;
        bikeDB.updateIsInMaintenance(this);
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    public boolean isInMaintenance() {
        return isInMaintenance;
    }

}