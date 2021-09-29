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
        rackPositionDB.addRackPositions(rackID, numberPositions, acceptedBike);
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

    public boolean isFreeAndAccessibleForBikeType(BikeType bikeType) {

        if ( this.getBike() == null && this.getAcceptedBikeType().getID() == bikeType.getID() && !this.isBroken() ) {
            return true;
        }
        return false;
    }

    public boolean unlock() throws SQLException {
        if ( Config.getInstance().isProductionMode() ) {
            try {
                // call to the hardware to open the position
            } catch ( Exception e ) {
                updateIsBroken(true);
                return false;
            }
        } else {

            if ( !Config.getInstance().getRackMockResponse() ) {
                updateIsBroken(true);
                return false;
            }
        }

        bikeRented();

        return true;
    }

    public boolean lock(Bike bike) throws SQLException {
        if ( Config.getInstance().isProductionMode() ) {
            try {
                // try to lock the position
            } catch ( Exception e ) {
                updateIsBroken(true);
                return false;
            }
        } else {

            if ( !Config.getInstance().getRackMockResponse() ) {
                updateIsBroken(true);
                return false;
            }
        }

        addBike(bike);

        return true;
    }

    public void updateIsBroken(boolean isBroken) throws SQLException {

        this.isBroken = isBroken;

        rackPositionDB.updateIsBroken(this.ID, isBroken);
    }

    public void addBike(Bike bike) throws SQLException {

        setBike(bike);
        rackPositionDB.addBike(this.ID, bike.getID());
    }

    /**
     * delete bike from database
     */
    public void deleteBike() throws SQLException {
        bike.delete();
        setBike(null);
    }

    /**
     * delete bike from teh position
     */
    public void bikeRented() throws SQLException {

        this.bike = null;
        rackPositionDB.setBikeNull(this.ID);
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