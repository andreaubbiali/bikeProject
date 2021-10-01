package test.dataservice;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Subscription_isValid_Test {

    Subscription subscription = new Subscription();
    SubscriptionType subType = new SubscriptionType();
    LocalDateTime today = LocalDateTime.now();

    @Before
    public void prepareObjClass() {
        subType.setDaysDuration(5);
        subType.setMustStartIn(10);

        subscription.setType(subType);
        subscription.setDeleted(false);
    }

    public void addDaysToSubscriptionDate(int days) {
        subscription.setSubscriptionDate(today.plusDays(days));
    }

    public void addDaysToStartDate(int days) {
        subscription.setStartDate(today.plusDays(days));
    }

    // subscription deleted
    @Test
    public void isValid_False_subscriptionDeleted() {
        subscription.setDeleted(true);

        boolean res = subscription.isValid();
        assertFalse(res);
    }

    // subscription not deleted start date = null --> 10 days to start the subscription.
    // subscription 11 days ago
    @Test
    public void isValid_nullStartDate_False_passedMustStartIn() {
        addDaysToSubscriptionDate(-11);

        boolean res = subscription.isValid();
        assertFalse(res);
    }

    // subscription 10 days ago
    @Test
    public void isValid_nullStartDate_True_sameAsMustStartIn() {
        addDaysToSubscriptionDate(-10);

        boolean res = subscription.isValid();
        assertTrue(res);
    }

    // subscription 9 days ago
    @Test
    public void isValid_nullStartDate_True() {
        addDaysToSubscriptionDate(-9);

        boolean res = subscription.isValid();
        assertTrue(res);
    }

    // subscription not deleted start date != null --> 5 days to use the subscription.
    // started 6 days ago
    @Test
    public void isValid_False_subscriptionEnded() {
        addDaysToStartDate(-6);

        boolean res = subscription.isValid();
        assertFalse(res);
    }

    // started 5 days ago
    @Test
    public void isValid_True_lastDay() {
        addDaysToStartDate(-5);

        boolean res = subscription.isValid();
        assertTrue(res);
    }

    // started 4 days ago
    @Test
    public void isValid_True() {
        addDaysToStartDate(-4);

        boolean res = subscription.isValid();
        assertTrue(res);
    }

}