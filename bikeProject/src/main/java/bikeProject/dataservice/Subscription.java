package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Subscription implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ SubscriptionType type;
    private /* @ not_null @ */ Date subscriptionDate;
    private /* @ not_null @ */ User user;
    private /* @ not_null @ */ int countExceededTime;
    private Date startDate;
    private /* @ not_null @ */ boolean deleted;

    public void createNewSubscription(User user, SubscriptionType subType) throws SQLException {
        Date today = new Date();

        this.type = subType;
        this.subscriptionDate = today;
        this.user = user;
        if ( type.getMustStartIn() == 0 ) {
            // the subscription start immediately
            this.startDate = today;
        }
        this.countExceededTime = 0;
        this.deleted = false;

        // add the subscription into db
        this.ID = subscriptionDB.createNewSubscription(this);

    }

    /**
     * A subscription is valid if is not deleted and today is:
     * if the subscription is started, today must be before or equal to: startDate + daysDuration
     * if the subscription isn't started, today must be before or equal to: subscriptionDate + mustStartIn
     *
     * @return true if is valid false otherwise
     */
    public boolean isValid() {
        Calendar cal = Calendar.getInstance();
        Date today = new Date();

        if ( this.deleted ) {
            return false;
        }

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

    /**
     * added 1 to countExceededTime and check if the subscription must be deleted
     *
     * @throws SQLException
     */
    public void exceededRentTime() throws SQLException {
        this.countExceededTime += 1;

        if ( countExceededTime == 3 ) {
            // exceeded the maximum times so delete the subscription
            this.deleted = true;
        }

        subscriptionDB.updateSubscription(this);
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

    public int getCountExceededTime() {
        return countExceededTime;
    }

    public void setCountExceededTime(int countExceededTime) {
        this.countExceededTime = countExceededTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}