package bikeProject.dataservice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Subscription implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ SubscriptionType type;
    private /* @ not_null @ */ LocalDateTime subscriptionDate;
    private /* @ not_null @ */ UserGeneric user;
    private /* @ not_null @ */ int countExceededTime;
    private LocalDateTime startDate;
    private /* @ not_null @ */ boolean deleted;
    private List<Rent> rentList;

    public void createNewSubscription(User user, SubscriptionType subType) throws SQLException {
        LocalDateTime today = LocalDateTime.now();

        this.type = subType;
        this.subscriptionDate = today;
        this.user = user;
        if ( type.getMustStartIn() == 0 ) {
            // the subscription start immediately
            this.startDate = today;
        }
        this.countExceededTime = 0;
        this.deleted = false;
        this.rentList = null;

        // add the subscription into db
        this.ID = subscriptionDB.createNewSubscription(this);

    }

    public List<Subscription> getSubscriptionByUser(UserGeneric user) throws SQLException {
        List<Subscription> subscriptionList = subscriptionDB.getSubscriptionByUserID(user.getID());

        Rent rent = new Rent();

        for ( Subscription sub : subscriptionList ) {
            sub.setType(sub.type.getSubscriptionType());
            sub.setUser(user);
            sub.setRentList(rent.getRentFromSubscriptionID(sub.getID()));
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
        LocalDateTime date;
        LocalDateTime today = LocalDateTime.now();

        if ( this.deleted ) {
            return false;
        }

        // calculate the last date in which the subscription is valid
        if ( this.startDate != null ) {
            // subscription started

            // calculate startDate + daysDuration
            date = this.startDate;
            date = date.plusDays(this.type.getDaysDuration());

        } else {
            // subscription not started

            // calculate subscriptionDate + mustStartIn
            date = this.subscriptionDate;
            date = date.plusDays(this.type.getMustStartIn());
        }

        // check the condition
        if ( today.toLocalDate().compareTo(date.toLocalDate()) > 0 ) {
            return false;
        }

        return true;
    }

    /*
     * start the subscription if not started yet
     */
    public void startSubscriptionNow() throws SQLException {

        if ( this.startDate == null ) {

            this.startDate = LocalDateTime.now();

            // update record on db
            subscriptionDB.setSubscriptionStartDateNow(this);
        }

    }

    public Rent isThereAnActiveRent() {
        if ( rentList == null || rentList.size() == 0 ) {
            return null;
        }

        for ( Rent rentTmp : rentList ) {
            if ( rentTmp.getEndDate() == null ) {
                return rentTmp;
            }
        }

        return null;
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

    public static int getActiveSubscription() throws SQLException {
        List<Subscription> subscriptions = subscriptionDB.getAllSubscription();
        int count = 0;

        for ( Subscription sub : subscriptions ) {
            if ( sub.isValid() ) {
                count++;
            }
        }

        return count;
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

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public UserGeneric getUser() {
        return user;
    }

    public void setUser(UserGeneric user) {
        this.user = user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
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

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

}