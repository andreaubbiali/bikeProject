package test.dataservice;

import bikeProject.dataservice.CreditCard;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreditCard_isCreditCardValid_Test {

    LocalDate today = LocalDate.now();
    CreditCard creditCard = new CreditCard(1, 58, 59, today);

    public void addDaysToExpireDate(int days) {
        creditCard.setExpireDate(creditCard.getExpireDate().plusDays(days));
    }

    @Test
    public void isCreditCardValid_true() {

        addDaysToExpireDate(1);

        boolean res = creditCard.isCreditCardValid(creditCard.getExpireDate());
        assertTrue(res);
    }

    @Test
    public void isCreditCardValid_False() {

        addDaysToExpireDate(-1);

        boolean res = creditCard.isCreditCardValid(creditCard.getExpireDate());
        assertFalse(res);
    }

    @Test
    public void isCreditCardValid_True() {

        addDaysToExpireDate(0);

        boolean res = creditCard.isCreditCardValid(creditCard.getExpireDate());
        assertTrue(res);
    }

}