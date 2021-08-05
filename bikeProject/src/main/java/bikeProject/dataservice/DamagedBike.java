package bikeProject.dataservice;

import java.sql.SQLException;

public class DamagedBike implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ Bike bike;
    private /* @ not_null @ */ User user;
    private /* @ not_null @ */ String message;

    public void comunicationNewDamage(Bike bike, User user, String message) throws SQLException {

        // set the bike in maintenance to allow the personal to fix it
        bike.setInMaintenance(true);

        // add the communication to db
        damagedBikeDB.addNewDamage(bike, user, message);

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}