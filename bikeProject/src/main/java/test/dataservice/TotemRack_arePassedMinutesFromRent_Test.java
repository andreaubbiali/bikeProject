package test.dataservice;

import bikeProject.config.Config;
import bikeProject.dataservice.Rent;
import bikeProject.dataservice.TotemRack;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TotemRack_arePassedMinutesFromRent_Test {

    TotemRack totemRack = new TotemRack();
    Rent rent = new Rent();
    LocalDateTime today = LocalDateTime.now();

    // endDate < today-5min
    // endDate < today-Config.getMinutesBetweenTwoRent()

    @Before
    public void setConfig() {
        Config.getInstance();
    }

    public void setRentEndDate(int minutes) {
        rent.setEndDate(today.minusMinutes(Config.getMinutesBetweenTwoRent() + minutes));
    }

    public void setRentEndDateSeconds(int seconds) {
        rent.setEndDate(today.minusMinutes(Config.getMinutesBetweenTwoRent()).minusSeconds(seconds));
    }

    @Test
    public void arePassedMinutesFromRent_true_NoRent() {

        boolean res = totemRack.arePassedMinutesFromRent(null);
        assertTrue(res);
    }

    @Test
    public void arePassedMinutesFromRent_false_miss1minute() {
        setRentEndDate(-1);

        boolean res = totemRack.arePassedMinutesFromRent(rent);
        assertFalse(res);
    }

    @Test
    public void arePassedMinutesFromRent_true_passed1minute() {
        setRentEndDate(+1);

        boolean res = totemRack.arePassedMinutesFromRent(rent);
        assertTrue(res);
    }

    @Test
    public void arePassedMinutesFromRent_false_miss5Seconds() {
        setRentEndDateSeconds(-10);

        boolean res = totemRack.arePassedMinutesFromRent(rent);
        assertFalse(res);
    }

    @Test
    public void arePassedMinutesFromRent_true_passed5Seconds() {
        setRentEndDateSeconds(+10);

        boolean res = totemRack.arePassedMinutesFromRent(rent);
        assertTrue(res);
    }
}