package test.dataservice;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.SubscriptionType;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsCreditCardValidForSubscriptionTest {

    CreditCard creditCard = new CreditCard();
    SubscriptionType subType = new SubscriptionType();
    Date today = new Date();

    public Date createDateFromToday(int number) {
        Calendar date = Calendar.getInstance();
        date.setTime(today);
        date.add(Calendar.DAY_OF_YEAR, number);
        return date.getTime();
    }

    //credi card expired
    @Test
    public void isCreditCardValidForSubscription_False_CreditCardExpired() {

        // the credit card will expire in 20 days
        creditCard.setExpireDate(createDateFromToday(-20));

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // start immediately, duration 10 days + 10 days for penalties = 20
    @Test
    public void isCreditCardValidForSubscription_True_equalDate() {

        // the credit card will expire in 20 days
        creditCard.setExpireDate(createDateFromToday(20));

        subType.setMustStartIn(0);
        subType.setDaysDuration(10);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    // start immediately, duration 5 days + 10 days for penalties = 15
    @Test
    public void isCreditCardValidForSubscription_True_ExpireAfter() {

        // the credit card will expire in 20 days
        creditCard.setExpireDate(createDateFromToday(20));

        subType.setMustStartIn(0);
        subType.setDaysDuration(5);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    // start immediately, duration 15 days + 10 days for penalties = 25
    @Test
    public void isCreditCardValidForSubscription_False_ExpireBefore() {

        // the credit card will expire in 20 days
        creditCard.setExpireDate(createDateFromToday(20));

        subType.setMustStartIn(0);
        subType.setDaysDuration(15);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

}