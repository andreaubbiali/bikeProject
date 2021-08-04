package bikeProject.dataservice;

import java.sql.SQLException;

public class RackPosition implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ boolean isBroken;
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

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

}