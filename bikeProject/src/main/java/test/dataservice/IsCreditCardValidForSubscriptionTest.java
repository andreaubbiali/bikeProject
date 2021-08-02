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

    @Test
    public void isCreditCardValidForSubscription_False_equalDate() {

        creditCard.setExpireDate(today);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    @Test
    public void isCreditCardValidForSubscription_False_ExpireDateBefore() {

        // expire date yesterday
        Date expireDate = createDateFromToday(-1);

        creditCard.setExpireDate(expireDate);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // test with mustStartIn = 0

    // partito oggi, dura 5 giorni, la carta scade tra 5 giorni
    @Test
    public void isCreditCardValidForSubscription_False_1() {

        int daysDuration = 5;

        subType.setMustStartIn(0);
        subType.setDaysDuration(daysDuration);

        // expireDate equal to daysDuration
        Date expireDate = createDateFromToday(daysDuration);
        creditCard.setExpireDate(expireDate);

        // subscription.setStartDate(today);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // partito oggi, dura 5 giorni, la carta scade tra 34 giorni (30 gg + 5 gg)-1
    @Test
    public void isCreditCardValidForSubscription_False_2() {

        int daysDuration = 5;

        subType.setMustStartIn(0);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(34);
        creditCard.setExpireDate(expireDate);

        // subscription.setStartDate(today);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // partito oggi, dura 5 giorni, la carta scade tra 35 giorni (30 gg + 5 gg)
    @Test
    public void isCreditCardValidForSubscription_True_3() {

        int daysDuration = 5;

        subType.setMustStartIn(0);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(35);
        creditCard.setExpireDate(expireDate);

        // subscription.setStartDate(today);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    // partito oggi, dura 5 giorni, la carta scade tra 35 giorni (30 gg + 5 gg)+1
    @Test
    public void isCreditCardValidForSubscription_True_4() {

        int daysDuration = 5;

        subType.setMustStartIn(0);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(36);
        creditCard.setExpireDate(expireDate);

        // subscription.setStartDate(today);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    /*
     * // partito 20 gg fa, dura 30 giorni, carta scade 39 giorni ==
     * ((20gg-10gg)+30)-1
     *
     * @Test public void isCreditCardValidForSubscription_False_5() {
     *
     * int daysDuration = 30;
     *
     * subType.setMustStartIn(0); subType.setDaysDuration(daysDuration);
     *
     * Date expireDate = createDateFromToday(39);
     * creditCard.setExpireDate(expireDate);
     *
     * Date startDate = createDateFromToday(-20);
     * subscription.setStartDate(startDate);
     *
     * boolean res = creditCard.isCreditCardValidForSubscription(subType,
     * subscription); assertFalse(res); }
     *
     * // partito 20 gg fa, dura 30 giorni, carta scade 40 giorni ==
     * ((20gg-10gg)+30)
     *
     * @Test public void isCreditCardValidForSubscription_True_6() {
     *
     * int daysDuration = 30;
     *
     * subType.setMustStartIn(0); subType.setDaysDuration(daysDuration);
     *
     * Date expireDate = createDateFromToday(40);
     * creditCard.setExpireDate(expireDate);
     *
     * Date startDate = createDateFromToday(-20);
     * subscription.setStartDate(startDate);
     *
     * boolean res = creditCard.isCreditCardValidForSubscription(subType,
     * subscription); assertTrue(res); }
     *
     * // partito 20 gg fa, dura 30 giorni, carta scade 41 giorni ==
     * ((20gg-10gg)+30)+1
     *
     * @Test public void isCreditCardValidForSubscription_True_7() {
     *
     * int daysDuration = 30;
     *
     * subType.setMustStartIn(0); subType.setDaysDuration(daysDuration);
     *
     * Date expireDate = createDateFromToday(41);
     * creditCard.setExpireDate(expireDate);
     *
     * Date startDate = createDateFromToday(-20);
     * subscription.setStartDate(startDate);
     *
     * boolean res = creditCard.isCreditCardValidForSubscription(subType,
     * subscription); assertTrue(res); }
     */

    // test with mustStartIn != 0

    // abbonamento deve partire entro 20 gg.
    // daysDuration = 10
    // (20gg+10gg)+30gg == 60gg

    // carta scade tra 59 gg
    @Test
    public void isCreditCardValidForSubscription_False_8() {

        int daysDuration = 10;

        subType.setMustStartIn(20);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(59);
        creditCard.setExpireDate(expireDate);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertFalse(res);
    }

    // carta scade tra 60gg
    @Test
    public void isCreditCardValidForSubscription_True_9() {

        int daysDuration = 10;

        subType.setMustStartIn(20);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(60);
        creditCard.setExpireDate(expireDate);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }

    // carta scade tra 61gg
    @Test
    public void isCreditCardValidForSubscription_True_10() {

        int daysDuration = 10;

        subType.setMustStartIn(20);
        subType.setDaysDuration(daysDuration);

        Date expireDate = createDateFromToday(61);
        creditCard.setExpireDate(expireDate);

        boolean res = creditCard.isCreditCardValidForSubscription(subType);
        assertTrue(res);
    }
}