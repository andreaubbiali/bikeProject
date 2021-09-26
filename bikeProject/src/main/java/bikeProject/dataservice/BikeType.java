package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.List;

public class BikeType implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ BikeTypeEnum type;
    private /* @ not_null @ */ boolean babySeat;

    public List<BikeType> getBikeTypes() throws SQLException {
        return bikeTypeDB.getBikeTypes();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public BikeTypeEnum getType() {
        return type;
    }

    public void setType(BikeTypeEnum type) {
        this.type = type;
    }

    public boolean isBabySeat() {
        return babySeat;
    }

    public void setBabySeat(boolean babySeat) {
        this.babySeat = babySeat;
    }
}