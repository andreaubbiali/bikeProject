package test.dataservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import bikeProject.dataservice.Subscription;
import bikeProject.dataservice.SubscriptionType;

// Subscription valida se:
// SE startDate NULL -->
//				SE subscriptionDate+mustStartIn < oggi VALIDO
//				SE subscriptionDate+mustStartIn == oggi VALIDO
//				SE subscriptionDate+mustStartIn > oggi NON VALIDO
// SE startDate NOT NULL -->
//			endDate = startDate + daysDuration
//				SE endDate < oggi NON VALIDO
//				SE endDate == oggi VALIDO
//				SE endDate > oggi VALIDO

public class IsValidSubscriptionTest {

    Subscription subscription = new Subscription();
    SubscriptionType subType = new SubscriptionType();
    Date today = new Date();

    public void setSubscriptionType(int daysDuration, int mustStartIn) {
        subType.setDaysDuration(daysDuration);
        subType.setMustStartIn(mustStartIn);
    }

    public void setSubscription(LocalDateTime subscriptionDate, LocalDateTime startDate) {
        subscription.setType(subType);
        subscription.setSubscriptionDate(subscriptionDate);
        subscription.setStartDate(startDate);
    }

    public LocalDateTime createDateFromToday(int number) {
        LocalDateTime date = LocalDateTime.now();

        return date.plusDays(number);
    }

    // STARTDATE NULL

    // subscription fatta 5 giorni fa,mustStartIn 6 giorni
    @Test
    public void isValid_Subscription_True_1() {

        LocalDateTime subscriptionDate = createDateFromToday(-5);

        subscription.setDeleted(false);
        setSubscriptionType(10, 6);
        setSubscription(subscriptionDate, null);

        boolean res = subscription.isValid();

        assertTrue(res);
    }

    // subscription fatta 5 giorni fa,mustStartIn 5 giorni
    @Test
    public void isValid_Subscription_True_2() {

        LocalDateTime subscriptionDate = createDateFromToday(-5);

        subscription.setDeleted(false);
        setSubscriptionType(10, 5);
        setSubscription(subscriptionDate, null);

        boolean res = subscription.isValid();

        assertTrue(res);

    }

    // subscription fatta 5 giorni fa,mustStartIn 4 giorni
    @Test
    public void isValid_Subscription_False_3() {

        LocalDateTime subscriptionDate = createDateFromToday(-5);

        subscription.setDeleted(false);
        setSubscriptionType(10, 4);
        setSubscription(subscriptionDate, null);

        boolean res = subscription.isValid();

        assertFalse(res);
    }

    // STARTDATE NOT NULL

    // partito 10 giorni fa, daysDuration 11
    @Test
    public void isValid_Subscription_True_4() {

        LocalDateTime startDate = createDateFromToday(-10);

        subscription.setDeleted(false);
        setSubscriptionType(11, 10);
        setSubscription(null, startDate);

        boolean res = subscription.isValid();

        assertTrue(res);
    }

    // partito 10 giorni fa, daysDuration 10
    @Test
    public void isValid_Subscription_True_5() {

        LocalDateTime startDate = createDateFromToday(-10);

        subscription.setDeleted(false);
        setSubscriptionType(10, 10);
        setSubscription(null, startDate);

        boolean res = subscription.isValid();

        assertTrue(res);
    }

    // partito 10 giorni fa, daysDuration 9
    @Test
    public void isValid_Subscription_False_6() {

        LocalDateTime startDate = createDateFromToday(-10);

        subscription.setDeleted(false);
        setSubscriptionType(9, 10);
        setSubscription(null, startDate);

        boolean res = subscription.isValid();

        assertFalse(res);
    }

    // deleted
    @Test
    public void isValid_Subscription_False_7() {

        LocalDateTime subscriptionDate = createDateFromToday(-5);

        subscription.setDeleted(true);
        boolean res = subscription.isValid();

        assertFalse(res);
    }

}