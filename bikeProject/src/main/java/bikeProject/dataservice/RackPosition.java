package bikeProject.dataservice;

import bikeProject.exception.RackException;

import java.sql.SQLException;

public class RackPosition implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ boolean isBroken;
    private /* @ not_null @ */ BikeType acceptedBikeType;
    private Bike bike;

    public boolean unlock() throws SQLException {
        if ( config.isProductionMode() ) {
            try {
                // try to unlock the position
            } catch ( Exception e ) {
                setIsBroken(true);
                return false;
            }
        } else {

            if ( !config.getRackMockResponse() ) {
                setIsBroken(true);
                return false;
            }
        }

        return true;
    }

    public boolean lock() throws SQLException {
        if ( config.isProductionMode() ) {
            try {
                // try to lock the position
            } catch ( Exception e ) {
                setIsBroken(true);
                return false;
            }
        } else {

            if ( !config.getRackMockResponse() ) {
                setIsBroken(true);
                return false;
            }
        }

        return true;
    }

    public void setIsBroken(boolean isBroken) throws SQLException {

        this.isBroken = isBroken;

        rackDB.updateIsBroken(isBroken);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public BikeType getAcceptedBikeType() {
        return acceptedBikeType;
    }

    public void setAcceptedBikeType(BikeType acceptedBikeType) {
        this.acceptedBikeType = acceptedBikeType;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

}