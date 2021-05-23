package bikeProject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import bikeProject.dataservice.CreditCard;
import bikeProject.dataservice.SubscriptionType;

class IsCreditCardValidForSubscriptionTest {

	@Test
	public void isCreditCardValidForSubscription_ExpireDateEqualToday_Fail() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();
		creditCard.setExpireDate(today);

		boolean res = creditCard.isCreditCardValidForSubscription(subType);
		assertFalse(res);

	}

	@Test
	public void isCreditCardValidForSubscription_TodayAfterExpireDate_Fail() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();
		Calendar expireDateYesterday = Calendar.getInstance();
		expireDateYesterday.setTime(today);
		expireDateYesterday.add(Calendar.DAY_OF_YEAR, -1);
		creditCard.setExpireDate(expireDateYesterday.getTime());

		boolean res = creditCard.isCreditCardValidForSubscription(subType);
		assertFalse(res);

	}

	@Test
	public void isCreditCardValidForSubscription_AutomaticStartTrue_False() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(today);
		expireDate.add(Calendar.DAY_OF_YEAR, 394);
		// ExpireDate = today+365 days --> daysduration+30-1
		creditCard.setExpireDate(expireDate.getTime());

		subType.setAutomaticStart(true);
		subType.setDaysDuration(365);

		boolean res = creditCard.isCreditCardValidForSubscription(subType);

		assertFalse(res);

	}

	@Test
	public void isCreditCardValidForSubscription_AutomaticStartTrue_True() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(today);
		expireDate.add(Calendar.DAY_OF_YEAR, 396);
		// ExpireDate = today+396 days --> daysduration+30+1
		creditCard.setExpireDate(expireDate.getTime());

		subType.setAutomaticStart(true);
		subType.setDaysDuration(365);

		boolean res = creditCard.isCreditCardValidForSubscription(subType);

		assertTrue(res);

	}

	@Test
	public void isCreditCardValidForSubscription_AutomaticStartFalse_False() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(today);
		expireDate.add(Calendar.DAY_OF_YEAR, 394);
		// ExpireDate = today+394 days --> daysduration+30-1
		creditCard.setExpireDate(expireDate.getTime());

		subType.setAutomaticStart(false);
		subType.setMustStartIn(365);

		boolean res = creditCard.isCreditCardValidForSubscription(subType);

		assertFalse(res);

	}

	@Test
	public void isCreditCardValidForSubscription_AutomaticStartFalse_True() {

		CreditCard creditCard = new CreditCard();
		SubscriptionType subType = new SubscriptionType();

		Date today = new Date();

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(today);
		expireDate.add(Calendar.DAY_OF_YEAR, 396);
		// ExpireDate = today+396 days --> daysduration+30+1
		creditCard.setExpireDate(expireDate.getTime());

		subType.setAutomaticStart(false);
		subType.setMustStartIn(365);

		boolean res = creditCard.isCreditCardValidForSubscription(subType);

		assertTrue(res);

	}

}
