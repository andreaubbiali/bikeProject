package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.exception.InvalidCreditCardException;
import bikeProject.exception.PaymentException;

import java.sql.SQLException;
import java.time.LocalDate;

public class CreditCard implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ long number;
    private /* @ not_null @ */ long cvv;
    private /* @ not_null @ */ LocalDate expireDate;

    public CreditCard(long id, long number, long cvv, LocalDate expireDate) {
        this.setID(id);
        this.setNumber(number);
        this.setCvv(cvv);
        this.setExpireDate(expireDate);
    }

    public CreditCard(long number, long cvv, LocalDate expireDate, User user) throws SQLException,
            InvalidCreditCardException {
        this.setNumber(number);
        this.setCvv(cvv);
        this.setExpireDate(expireDate);
        registerNewCreditCard(user);
    }

    private void registerNewCreditCard(User user) throws SQLException, InvalidCreditCardException {

        if ( this.number == 0 || this.cvv == 0 || this.expireDate == null ) {
            throw new InvalidCreditCardException("Missing some fields");
        }

        if ( !isCreditCardValid(expireDate) ) {
            throw new InvalidCreditCardException("The credit card is expired");
        }

        // register new credit card for a user
        this.ID = credCardDB.registerNewCreditCard(user.getID(), number, cvv, expireDate);
    }

    /**
     * To be valid the credit card must not be expired (return true if expire today)
     */
    public boolean isCreditCardValid(LocalDate expireDate) {
        LocalDate today = LocalDate.now();

        if ( expireDate.compareTo(today) < 0 ) {
            return false;
        }

        return true;
    }

    /**
     * @param amount the amount to be payed
     * @throws PaymentException if something with the call goes wrong
     */
    public void pay(float amount) throws PaymentException {
        if ( Config.getInstance().isProductionMode() ) {
            try {
                // send request to bank
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } else {

            if ( !Config.getInstance().getBankMockResponse() ) {
                throw new PaymentException();
            }
        }
    }

    /**
     * The credit card is valid if is valid for all the period of the subscription
     * from today. The credit card must be valid for at least the subtype.MustStartIn +
     * subtype.daysDuration + 10 days. 10 days are added to be secure that the user can pay
     * penal in next days.
     *
     * @param subType the type of subscription
     * @return true if the credit card is valid, false otherwise
     */
    public boolean isCreditCardValidForSubscription(SubscriptionType subType) {
        LocalDate minimumDate = LocalDate.now();

        // check that the credit card isn't expired or expire today
        if ( !isCreditCardValid(this.expireDate) ) {
            return false;
        }

        // minimum days' duration of the credit card
        int daysDuration = subType.getMustStartIn() + subType.getDaysDuration() + 10;

        // add the minimum days the credit card must be valid from today
        minimumDate = minimumDate.plusDays(daysDuration);

        // control the expireDate
        if ( minimumDate.compareTo(expireDate) > 0 ) {
            return false;
        }

        return true;
    }

    public void setCreditCard(long id, long number, long cvv, LocalDate expireDate) {
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

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

}