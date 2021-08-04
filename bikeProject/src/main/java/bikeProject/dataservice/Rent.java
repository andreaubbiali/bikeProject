package bikeProject.dataservice;

import java.util.Date;

import bikeProject.database.RentDatabase;
import bikeProject.exception.NotValidRentException;

public class Rent implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ Bike bike;
    private /* @ not_null @ */ User user;
    private /* @ not_null @ */ Date startDate;

    public void createRent(User user, Bike bike) {
        Date today = new Date();

        this.user = user;
        this.bike = bike;
        this.startDate = today;

        // add the record into db
        rentDB.createRent(user, bike, startDate);
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}