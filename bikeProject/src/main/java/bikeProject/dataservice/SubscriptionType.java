package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.List;

public class SubscriptionType implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String name;
    private /* @ not_null @ */ float price;
    private /* @ not_null @ */ int daysDuration;
    private /* @ not_null @ */ int mustStartIn;

    public SubscriptionType getSubscriptionType() throws SQLException {
        return subTypeDB.getSubscriptionTypeByID(this.ID);
    }

    public List<SubscriptionType> getAllSubscriptionTypes() throws SQLException {
        return subTypeDB.getAllSubscriptionTypes();
    }

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDaysDuration() {
        return daysDuration;
    }

    public void setDaysDuration(int daysDuration) {
        this.daysDuration = daysDuration;
    }

    public int getMustStartIn() {
        return mustStartIn;
    }

    public void setMustStartIn(int mustStartIn) {
        this.mustStartIn = mustStartIn;
    }

}