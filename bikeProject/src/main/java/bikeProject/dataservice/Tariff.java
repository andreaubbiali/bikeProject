package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tariff implements DataserviceInterface {

    // lavoro a mezz'ore

    private /* @ not_null @ */ int passedTimeInMinutes; // the 0 value indicates if exceed maximum time
    private /* @ not_null @ */ Float tariff;
    private /* @ not_null @ */ BikeType bikeType;

    public Float calculateCostOfRent(int timeOfRentInMinutes, BikeType bikeType) throws SQLException {

        List<Tariff> tariffList = tariffDB.getTariffsByBikeTypeID(bikeType.getID());

        Float totalCost = 0F;

        for ( Tariff tariff : tariffList ) {
            if (tariff.passedTimeInMinutes > timeOfRentInMinutes ){
                break;
            }

            totalCost += tariff.tariff;
        }

        return totalCost;
    }

    public int calculateMinutesFromDate(Date date){
        return (int)date.getTime()/60000;
    }


    // GETTERS AND SETTERS

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