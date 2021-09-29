package bikeProject.dataservice;

import java.sql.SQLException;

public class DamageMessage implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String message;

    public void addNewDamageMessage(String message, Rent rent) throws SQLException {

        setMessage(message);

        // add the communication to db
        setID(damagedBikeDB.addNewDamage(message, rent.getID()));

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