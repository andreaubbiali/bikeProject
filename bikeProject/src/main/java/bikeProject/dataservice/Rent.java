package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.exception.AccessDeniedException;
import bikeProject.exception.InvalidSubscriptionException;
import bikeProject.exception.PaymentException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Rent implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ Bike bike;
    private /* @ not_null @ */ LocalDateTime startDate;
    private /* @ not_null @ */ LocalDateTime endDate;
    private DamageMessage damageMessage;

    public void createRent(Bike bike, Subscription subscription) throws SQLException, AccessDeniedException {
        LocalDateTime today = LocalDateTime.now();

        this.bike = bike;
        this.startDate = today;

        // add the record into db
        rentDB.createRent(bike, startDate, subscription);
    }

    public float endRent(CreditCard creditCard) throws PaymentException, SQLException, InvalidSubscriptionException {
        this.endDate = LocalDateTime.now();
        float totalCost = 0f;

        rentDB.updateRentEndDate(this);

        if ( User.getIsStudent() && bike.getType().getType().equals(BikeTypeEnum.NORMAL) ) {
            // student user can use bike normal freely
            return totalCost;
        }

        // calculate the duration of the rent in minutes
        int rentMinutes = Tariff.calculateMinutesFromDate(endDate) - Tariff.calculateMinutesFromDate(startDate);

        // calculate the cost to be pay
        totalCost = Tariff.calculateTariffByBikeType(rentMinutes, bike.getType());

        if ( rentMinutes > Config.getInstance().getMaximumRentMinutes() ) {

            // update user subscription because has exceeded the maximum time
            Subscription subscription = User.getSubscriptionByRent(this);
            subscription.exceededRentTime();
        }

        // if the rent minutes > 24 hours pay penal
        if ( rentMinutes > 1440 ) {
            // exceeded the maximum rent time so pay the penal + the rent
            totalCost += Config.getInstance().getTariffExceed24Hours();
        }

        // payment
        creditCard.pay(totalCost);

        return totalCost;
    }

    public List<Rent> getRentFromSubscriptionID(long subscriptionID) throws SQLException {
        return rentDB.getRentFromSubscriptionID(subscriptionID);
    }

    public void damageCommunication(String communicationText) throws SQLException {

        // add new damage message into db
        damageMessage.addNewDamageMessage(communicationText, this);

        // set the bike in maintenance to allow the personal to fix it
        bike.setBikeInMaintenance();
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DamageMessage getDamageMessage() {
        return damageMessage;
    }

    public void setDamageMessage(DamageMessage damageMessage) {
        this.damageMessage = damageMessage;
    }

}