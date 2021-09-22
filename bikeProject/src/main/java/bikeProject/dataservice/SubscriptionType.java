package bikeProject.dataservice;

import java.sql.SQLException;

public class SubscriptionType implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String type;
    private /* @ not_null @ */ float price;
    private /* @ not_null @ */ int daysDuration;
    private /* @ not_null @ */ int mustStartIn;

    public SubscriptionType getType() throws SQLException {
        return subTypeDB.getTypeByID(this.ID);
    }

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
    }

    public String getTypeName() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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