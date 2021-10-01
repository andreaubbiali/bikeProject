package test.dataservice;

import bikeProject.dataservice.Rent;
import bikeProject.dataservice.Subscription;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Subscription_isThereAnActiveRent_Test {

    Subscription subscription = new Subscription();
    LocalDateTime today = LocalDateTime.now();

    @Test
    public void isThereAnActiveRent_False_NullList() {

        Rent res = subscription.isThereAnActiveRent();
        assertNull(res);
    }

    @Test
    public void isThereAnActiveRent_False_EmptyList() {
        List<Rent> rent = new ArrayList<>();
        subscription.setRentList(rent);

        Rent res = subscription.isThereAnActiveRent();
        assertNull(res);
    }

    public void setSubscriptionRentListWithEndDate() {
        List<Rent> rentList = new ArrayList<>();
        Rent rent = new Rent();
        rent.setEndDate(today);

        Rent rent1 = new Rent();
        rent1.setEndDate(today.plusDays(5));

        Rent rent2 = new Rent();
        rent2.setEndDate(today.plusDays(10));

        rentList.add(rent);
        rentList.add(rent1);
        rentList.add(rent2);

        subscription.setRentList(rentList);
    }

    @Test
    public void isThereAnActiveRent_False_NoActiveRents() {
        setSubscriptionRentListWithEndDate();

        Rent res = subscription.isThereAnActiveRent();
        assertNull(res);
    }

    public void setSubscriptionRentList() {
        List<Rent> rentList = new ArrayList<>();
        Rent rent = new Rent();
        rent.setEndDate(today);

        Rent rent1 = new Rent();
        rent1.setEndDate(null);

        Rent rent2 = new Rent();
        rent2.setEndDate(today.plusDays(10));

        rentList.add(rent);
        rentList.add(rent1);
        rentList.add(rent2);

        subscription.setRentList(rentList);
    }

    @Test
    public void isThereAnActiveRent_True() {
        setSubscriptionRentList();

        Rent res = subscription.isThereAnActiveRent();
        assertNotNull(res);
    }
}