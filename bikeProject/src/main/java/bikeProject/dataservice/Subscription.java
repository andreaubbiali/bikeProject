package bikeProject.dataservice;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Subscription implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ SubscriptionType type;
    private /* @ not_null @ */ LocalDate subscriptionDate;
    private /* @ not_null @ */ User user;
    private /* @ not_null @ */ int countExceededTime;
    private LocalDate startDate;
    private /* @ not_null @ */ boolean deleted;

    public void createNewSubscription(User user, SubscriptionType subType) throws SQLException {
        LocalDate today = LocalDate.now();

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

    public List<Subscription> getSubscriptionByUser(User user) throws SQLException {
        List<Subscription> subscriptionList = subscriptionDB.getSubscriptionByUserID(user.getID());

        for ( Subscription sub : subscriptionList ) {
            sub.setType(sub.type.getSubscriptionType());
            sub.setUser(user);
        }

        return subscriptionList;
    }

    /**
     * A subscription is valid if is not deleted and today is:
     * if the subscription is started, today must be before or equal to: startDate + daysDuration
     * if the subscription isn't started, today must be before or equal to: subscriptionDate + mustStartIn
     *
     * @return true if is valid false otherwise
     */
    public boolean isValid() {
        LocalDate date;
        LocalDate today = LocalDate.now();

        if ( this.deleted ) {
            return false;
        }

        if ( this.startDate != null ) {
            // subscription started

            // calculate startDate + daysDuration
            date = this.startDate;
            date.plusDays(this.type.getDaysDuration());

        } else {
            // subscription not started

            // calculate subscriptionDate + mustStartIn
            date = this.subscriptionDate;
            date.plusDays(this.type.getMustStartIn());
        }

        // check the condition
        if ( today.compareTo(date) <= 0 ) {
            return true;
        }

        return false;
    }

    public void startSubscriptionNow() throws SQLException {
        LocalDate today = LocalDate.now();

        this.startDate = today;

        // update record on db
        subscriptionDB.setSubscriptionStartDateNow(this);
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

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
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