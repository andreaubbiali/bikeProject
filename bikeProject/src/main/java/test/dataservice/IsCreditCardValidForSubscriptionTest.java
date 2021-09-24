package test.dataservice;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.SubscriptionType;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsCreditCardValidForSubscriptionTest {

    LocalDate today = LocalDate.now();
    CreditCard creditCard = new CreditCard(1, 585548484445555l, 5864, today);
    SubscriptionType subType = new SubscriptionType();

    public LocalDate createDateFromToday(int number) {

        return today.plusDays(number);
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