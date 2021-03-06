package bikeProject.dataservice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Tariff implements DataserviceInterface {

    public static List<Tariff> tariffInstance = null;

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ int passedTimeInMinutes;
    private /* @ not_null @ */ Float tariff;
    private /* @ not_null @ */ BikeType bikeType;

    public Tariff() {
    }

    public static List<Tariff> getInstance() throws SQLException {
        if ( tariffInstance == null ) {
            tariffInstance = tariff();
        }
        return tariffInstance;
    }

    private static List<Tariff> tariff() throws SQLException {

        // get tariff from db
        return tariffDB.getAllTariffs();
    }

    public static Float calculateTariffByBikeType(int timeOfRentInMinutes, BikeType bikeType) {

        Float totalCost = 0F;

        for ( Tariff tariff : tariffInstance ) {

            if ( tariff.getBikeType().getType().equals(bikeType.getType()) ) {
                totalCost += tariff.tariff;

                if ( tariff.passedTimeInMinutes > timeOfRentInMinutes ) {
                    break;
                }
            }
        }

        return totalCost;
    }

    public static int calculateMinutesFromDate(LocalDateTime date) {
        return (int) date.toEpochSecond(ZoneOffset.UTC) / 60;
    }

    // GETTERS AND SETTERS

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getPassedTimeInMinutes() {
        return passedTimeInMinutes;
    }

    public void setPassedTimeInMinutes(int passedTimeInMinutes) {
        this.passedTimeInMinutes = passedTimeInMinutes;
    }

    public Float getTariff() {
        return tariff;
    }

    public void setTariff(Float tariff) {
        this.tariff = tariff;
    }

    public BikeType getBikeType() {
        return bikeType;
    }

    public void setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
    }

}