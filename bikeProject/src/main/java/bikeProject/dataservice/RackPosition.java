package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.exception.RackException;

import java.sql.SQLException;

public class RackPosition implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ boolean isBroken;
    private /* @ not_null @ */ BikeType acceptedBikeType;
    private Bike bike;

    public void addRackPositions(long rackID, int numberPositions, String acceptedBike) throws SQLException {
        rackDB.addRackPositions(rackID, numberPositions, acceptedBike);
    }

    /**
     * check all requisites: a bike into the rack position, bikeType requested by the user, bike not in maintenance
     * and a not broken position
     *
     * @return
     */
    public boolean isBikeTypeRentable(BikeType bikeType) {

        // bike null or in maintenance
        if ( this.bike == null || this.bike.isInMaintenance() ) {
            return false;
        }

        // different bikeType
        if ( !bike.getType().getType().equals(bikeType.getType()) ) {
            return false;
        }

        // rack broken
        if ( this.isBroken ) {
            return false;
        }

        return true;
    }

    public boolean unlock() throws SQLException {
        if ( Config.getInstance().isProductionMode() ) {
            try {
                // try to unlock the position
            } catch ( Exception e ) {
                setIsBroken(true);
                return false;
            }
        } else {

            if ( !Config.getInstance().getRackMockResponse() ) {
                setIsBroken(true);
                return false;
            }
        }

        return true;
    }

    public boolean lock() throws SQLException {
        if ( Config.getInstance().isProductionMode() ) {
            try {
                // try to lock the position
            } catch ( Exception e ) {
                setIsBroken(true);
                return false;
            }
        } else {

            if ( !Config.getInstance().getRackMockResponse() ) {
                setIsBroken(true);
                return false;
            }
        }

        return true;
    }

    public void updateIsBroken(boolean isBroken) throws SQLException {

        this.isBroken = isBroken;

        rackDB.updateIsBroken(this.ID, isBroken);
    }

    public void addBike(Bike bike) throws SQLException {

        setBike(bike);
        rackDB.addBike(this.ID, bike.getID());
    }

    public void deleteBike() throws SQLException {
        bike.delete();
        setBike(null);
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

    public void setIsBroken(boolean isBroken) {
        this.isBroken = isBroken;
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