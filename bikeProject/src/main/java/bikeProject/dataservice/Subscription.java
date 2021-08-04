package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Subscription implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ SubscriptionType type;
    private /* @ not_null @ */ Date subscriptionDate;
    private /* @ not_null @ */ long userID;
    private /* @ not_null @ */ long creditCardID;
    private Date startDate;

    public void createNewSubscription(long userID, SubscriptionType subType, long creditCardID) throws SQLException {
        Date today = new Date();

        this.type = subType;
        this.subscriptionDate = today;
        this.userID = userID;
        this.creditCardID = creditCardID;
        if ( type.getMustStartIn() == 0 ) {
            // the subscription start immediately
            this.startDate = today;
        }

        // add the subscription into db
        this.ID = subscriptionDB.createNewSubscription(this);

    }

    /**
     * A subscription is valid if today is:
     * if the subscription is started, today must be before or equal to: startDate + daysDuration
     * if the subscription isn't started, today must be before or equal to: subscriptionDate + mustStartIn
     *
     * @return true if is valid false otherwise
     */
    public boolean isValid() {
        Calendar cal = Calendar.getInstance();
        Date today = new Date();

        if ( this.startDate != null ) {
            // subscription started

            // calculate startDate + daysDuration
            cal.setTime(this.startDate);
            cal.add(Calendar.DAY_OF_YEAR, this.type.getDaysDuration());

        } else {
            // subscription not started

            // calculate subscriptionDate + mustStartIn
            cal.setTime(this.subscriptionDate);
            cal.add(Calendar.DAY_OF_YEAR, this.type.getMustStartIn());
        }

        // check the condition
        if ( today.before(cal.getTime()) || today.toString().equals(cal.getTime().toString()) ) {
            return true;
        }

        return false;
    }

    public void startSubscriptionNow() throws SQLException {
        Date today = new Date();

        this.startDate = today;

        // update record on db
        subscriptionDB.setSubscriptionDateNow();
    }

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        this.ID = iD;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getCreditCardID() {
        return creditCardID;
    }

    public void setCreditCardID(long creditCardID) {
        this.creditCardID = creditCardID;
    }

}