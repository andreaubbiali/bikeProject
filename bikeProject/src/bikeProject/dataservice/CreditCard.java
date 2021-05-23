package bikeProject.dataservice;

import java.util.Calendar;
import java.util.Date;

import bikeProject.exception.PaymentException;

public class CreditCard {

	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ long number;
	private /* @ not_null @ */ long cvv;
	private /* @ not_null @ */ Date expireDate;

	public CreditCard(long ID) throws Exception {
		// TODO
		// database get creditCardByID() probabilmente mi serve anche lo userid per
		// controllare meglio
		setID(ID);
	}

	public CreditCard() {
	}

	public CreditCard(long number, long cvv, Date expireDate) {
		this.number = number;
		this.cvv = cvv;
		this.expireDate = expireDate;
	}

	/**
	 * 
	 * @param amount the amount to be payed
	 * @throws PaymentException if something with the call goes wrong
	 */
	public void pay(float amount) throws PaymentException {
		// TODO
		// connect to banks payment
	}

	/**
	 * 
	 * @return true if is valid false otherwise
	 */
	public boolean isValid() {
		Date today = new Date();

		if (today.after(expireDate)) {
			return false;
		}

		return true;
	}

	/**
	 * The credit card is valid if is valid for all the period of the subscription.
	 * The credit card must be valid for at least the duration of the subscription
	 * +30 days if the subscription start automatically. For other subscription the
	 * card must be valid for the 'MustStartIn' + 30 days.
	 * 
	 * @param subType the type of subscription
	 * @return true if the credit card is valid, false otherwise
	 */
	public boolean isCreditCardValidForSubscription(SubscriptionType subType) {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		// check that the credit card isn't expired or expire today
		if (today.after(expireDate) || today.equals(expireDate)) {
			return false;
		}

		// minimum days duration of the credit card
		int daysDuration;

		if (subType.isAutomaticStart()) {
			daysDuration = subType.getDaysDuration() + 30;
		} else {
			daysDuration = subType.getMustStartIn() + 30;
		}

		// add the numbers of subscription duration days to today date
		cal.add(Calendar.DAY_OF_YEAR, daysDuration);
		if (cal.getTime().after(expireDate)) {
			return false;
		}

		return true;
	}

	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getCvv() {
		return cvv;
	}

	public void setCvv(long cvv) {
		this.cvv = cvv;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
