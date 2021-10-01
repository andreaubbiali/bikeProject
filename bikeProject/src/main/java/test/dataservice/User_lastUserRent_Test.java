package test.dataservice;

import bikeProject.dataservice.Rent;
import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class User_lastUserRent_Test {

    LocalDateTime today = LocalDateTime.now();
    Rent r1 = new Rent();
    Rent r2 = new Rent();
    Rent r3 = new Rent();

    public void setSubscriptionToUser(List<Rent> rentList) {
        List<Subscription> subscriptionList = new ArrayList<>();

        Subscription sub = new Subscription();
        sub.setRentList(rentList);

        subscriptionList.add(sub);
        User.setSubscriptionList(subscriptionList);
    }

    public List<Rent> createRentList() {
        List<Rent> rentList = new ArrayList<>();

        r1.setEndDate(today.minusMinutes(50));

        r2.setEndDate(today.minusMinutes(20));

        r3.setEndDate(today.minusMinutes(40));

        rentList.add(r1);
        rentList.add(r2);
        rentList.add(r3);

        return rentList;
    }

    @Test
    public void lastUserRent_nullSubscriptionList() {

        Rent res = User.lastUserRent();
        assertNull(res);
    }

    @Test
    public void lastUserRent_NoRentList() {

        setSubscriptionToUser(null);

        Rent res = User.lastUserRent();
        assertNull(res);
    }

    @Test
    public void lastUserRent() {

        setSubscriptionToUser(createRentList());

        Rent res = User.lastUserRent();
        assertEquals(res, r2);
    }
}