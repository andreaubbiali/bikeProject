package bikeProject.dataservice;

import java.sql.SQLException;

public class DamageMessage implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String message;

    public void comunicationNewDamage(Bike bike, User user, String message) throws SQLException {

        // set the bike in maintenance to allow the personal to fix it
        bike.setBikeInMaintenance();

        // add the communication to db
        damagedBikeDB.addNewDamage(bike, user, message);

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}