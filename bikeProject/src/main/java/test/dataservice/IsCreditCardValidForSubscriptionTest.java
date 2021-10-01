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
    CreditCard creditCard = new CreditCard(1, 585548484445555L, 5864, today);
    SubscriptionType subType = new SubscriptionType();

    public void addDaysToExpireDate(int number) {
        creditCard.setExpireDate(creditCard.getExpireDate().plusDays(number));
    }

    // credit card expired
    @Test
    public void isCreditCardValidForSubscription_False_CreditCardExpired() {
        addDaysToExpireDate(-1);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // credit card not expired mustStartIn = 5 daysDuration = 5 --> 10+10 = 20
    // expire 19
    @Test
    public void isCreditCardValidForSubscription_False_CreditCardExpireDayBefore() {
        addDaysToExpireDate(19);

        subType.setMustStartIn(5);
        subType.setDaysDuration(5);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // credit card not expired mustStartIn = 5 daysDuration = 5 --> 10+10 = 20
    // expire 20
    @Test
    public void isCreditCardValidForSubscription_False_CreditCardExpireSameDay() {
        addDaysToExpireDate(20);

        subType.setMustStartIn(5);
        subType.setDaysDuration(5);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    // credit card not expired mustStartIn = 5 daysDuration = 5 --> 10+10 = 20
    // expire 21
    @Test
    public void isCreditCardValidForSubscription_False_CreditCardExpireAfter() {
        addDaysToExpireDate(21);

        subType.setMustStartIn(5);
        subType.setDaysDuration(5);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

}