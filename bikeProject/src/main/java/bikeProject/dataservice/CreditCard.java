package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;

public class CreditCard implements DataserviceInterface, CreditCardInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ long number;
    private /* @ not_null @ */ long cvv;
    private /* @ not_null @ */ Date expireDate;

    /**
     * @param userID
     * @param number
     * @param cvv
     * @param expireDate
     */
    public void registerNewCreditCard(long userID, long number, long cvv, Date expireDate) throws SQLException, InvalidCreditCardException {
        try {
            if ( !isCreditCardValid(expireDate) ) {
                throw new InvalidCreditCardException("The credit card is expired");
            }

            // register new credit card for a user
            int id = credCardDB.registerNewCreditCard(userID, number, cvv, expireDate);

            // set credit card fields
            setCreditCard(id, number, cvv, expireDate);

        } catch ( SQLException e ) {
            throw e;
        }
    }

    /**
     * To be valid the credit card must not be expired
     *
     * @param expireDate
     * @return
     */
    public boolean isCreditCardValid(Date expireDate) {
        Date today = new Date();

        if ( today.after(expireDate) ) {
            return false;
        }

        return true;
    }

    /**
     * @param amount the amount to be payed
     * @throws PaymentException if something with the call goes wrong
     */
    public void pay(float amount) throws PaymentException {
        // TODO
        // connect to banks payment
    }

    /**
     * The credit card is valid if is valid for all the period of the subscription
     * from today. The credit card must be valid for at least the duration of the
     * subscription +30 days if the subscription start automatically. For other
     * subscription the card must be valid for the 'MustStartIn' + daysDuration + 30
     * days.
     *
     * @param subType the type of subscription
     * @return true if the credit card is valid, false otherwise
     */
    public boolean isCreditCardValidForSubscription(SubscriptionType subType) {
        Date today = new Date();
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(today);

        // check that the credit card isn't expired or expire today
        if ( today.after(expireDate) ) {
            return false;
        }

        // minimum days duration of the credit card
        int daysDuration;

        if ( subType.getMustStartIn() == 0 ) {
            // subscription start automatically so the credit card must be valid for the
            // daysDuration+30
            daysDuration = subType.getDaysDuration() + 30;
        } else {
            // subscription not start automatically so the credit card must be valid for the
            // mustStartIn + daysDuration + 30
            daysDuration = subType.getMustStartIn() + subType.getDaysDuration() + 30;
        }

        // add the minimum days the credit card must be valid from today
        calDate.add(Calendar.DAY_OF_YEAR, daysDuration);

        // don't find any better methods
        boolean isDateEqual = calDate.getTime().toString().equals(expireDate.toString());
        // control the expireDate
        if ( calDate.getTime().after(expireDate) && !isDateEqual ) {
            return false;
        }

        return true;
    }

    public void setCreditCard(long id, long number, long cvv, Date expireDate) {
        setID(id);
        setNumber(number);
        setCvv(cvv);
        setExpireDate(expireDate);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getCvv() {
        return cvv;
    }

    public void setCvv(long cvv) {
        this.cvv = cvv;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}