package bikeProject.dataservice;

import java.sql.SQLException;

public class Bike implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ BikeType type;
    private /* @ not_null @ */ boolean isInMaintenance;

    public void setBikeInMaintenance() throws SQLException {

        this.isInMaintenance = true;
        bikeDB.updateBike(this);
    }

    public void createNewBike(BikeType bikeType) throws SQLException {
        setID(bikeDB.createBike(bikeType.getID()));
        setIsInMaintenance(false);
        setType(bikeType);
    }

    public void delete() throws SQLException {
        bikeDB.delete(this.ID);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public void setIsInMaintenance(boolean isInMaintenance) {
        this.isInMaintenance = isInMaintenance;
    }

}