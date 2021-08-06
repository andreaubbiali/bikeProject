package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import bikeProject.database.RentDatabase;
import bikeProject.exception.NotValidRentException;
import bikeProject.exception.PaymentException;

public class Rent implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ Bike bike;
    private /* @ not_null @ */ User user;
    private /* @ not_null @ */ Date startDate;
    private /* @ not_null @ */ Date endDate;

    public void createRent(User user, Bike bike) {
        Date today = new Date();

        this.user = user;
        this.bike = bike;
        this.startDate = today;

        // add the record into db
        rentDB.createRent(user, bike, startDate);
    }

    public void endRent(CreditCard creditCard) throws PaymentException, SQLException {
        this.endDate = new Date();

        rentDB.updateRent(this);

        if (user.isStudent() && bike.getType().getType().equals(BikeTypeEnum.NORMAL)){
            // student user can use bike normal freely
            return;
        }

        Tariff tariff = new Tariff();

        // calculate the duration of the rent in minutes
        int rentMinutes = tariff.calculateMinutesFromDate(endDate) - tariff.calculateMinutesFromDate(startDate);

        // calculate the cost to be pay
        Float totalCost;
        if (config.getMaximumRentMinutes() < rentMinutes){
            totalCost =config.getTariffExceedMaximumRentMinutes();
        } else {
            totalCost = tariff.calculateCostOfRent(rentMinutes, bike.getType());
        }

        // payment
        creditCard.pay(totalCost);

    }

    // GETTERS AND SETTERS

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}