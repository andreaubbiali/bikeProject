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
    private /* @ not_null @ */ Date startDate;

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
     * if the mustStartDate = null the startDate can't be null
     *
     * @return
     */
    public boolean isValid() {
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        int duration;

        if ( startDate == null ) {
            // the subscription hasn't been started

            cal.setTime(subscriptionDate);

            // oggi deve essere < mustStartIn+daysDuration

            // add duration to subscriptionDate
            cal.add(Calendar.DAY_OF_YEAR, type.getMustStartIn());

            // today before subscriptionDate+nustStartIn
            if ( today.before(cal.getTime()) || today.toString().equals(cal.getTime().toString()) ) {
                return true;
            }

        } else {
            // the subscription has been started

            // oggi < startDate+daysDuration
            cal.setTime(startDate);

            cal.add(Calendar.DAY_OF_YEAR, type.getDaysDuration());

            if ( today.before(cal.getTime()) || today.toString().equals(cal.getTime().toString()) ) {
                return true;
            }

        }

        return false;
    }

    /*public Subscription(String uniqueCode) throws SQLException {
		// TODO
		subscriptionDB.getSubscriptionByUniqueCode(uniqueCode, this);
	}*/

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