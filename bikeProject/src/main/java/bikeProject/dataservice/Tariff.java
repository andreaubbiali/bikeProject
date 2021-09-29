package bikeProject.dataservice;

import bikeProject.config.Config;
import bikeProject.exception.AccessDeniedException;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tariff implements DataserviceInterface {

    private static List<Tariff> tariffInstance = null;

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ int passedTimeInMinutes; // the 0 value indicates if exceed maximum time
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
            if ( tariff.passedTimeInMinutes > timeOfRentInMinutes ) {
                break;
            }

            totalCost += tariff.tariff;
        }

        return totalCost;
    }

    public static int calculateMinutesFromDate(Date date) {
        return (int) date.getTime() / 60000;
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