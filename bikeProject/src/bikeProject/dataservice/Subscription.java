package bikeProject.dataservice;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Subscription implements DataserviceInterface {

	// SE mustStartIN = 0 --> startDate today

	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ SubscriptionType type;
	private /* @ not_null @ */ String uniqueCode;
	private /* @ not_null @ */ Date subscriptionDate;
	private /* @ not_null @ */ long userID;
	private /* @ not_null @ */ Date startDate;

	public Subscription() {
	}

	public Subscription(String uniqueCode) throws SQLException {
		// TODO
		subscriptionDB.getSubscriptionByUniqueCode(uniqueCode, this);
	}

	public String createSubscription(SubscriptionType type, long userID) {
		Date today = new Date();

		this.type = type;
		if (type.getMustStartIn() == 0) {
			this.startDate = today;
		}
		this.subscriptionDate = today;
		this.userID = userID;

		this.uniqueCode = UUID.randomUUID().toString();

		// TODO
//		this.ID = db.createSubscription(this);

		return uniqueCode;
	}

	/**
	 * if the mustStartDate = null the startDate can't be null
	 * 
	 * @return
	 */
	public boolean isValid() {
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		int duration;

		if (startDate == null) {
			// the subscription hasn't been started

			cal.setTime(subscriptionDate);

			// oggi deve essere < mustStartIn+daysDuration

			// add duration to subscriptionDate
			cal.add(Calendar.DAY_OF_YEAR, type.getMustStartIn());

			if (today.after(cal.getTime())) {
				return false;
			}

			return true;

		} else {
			// the subscription has been started

			// oggi < startDate+daysDuration
			cal.setTime(startDate);

			cal.add(Calendar.DAY_OF_YEAR, type.getDaysDuration());

			if (today.after(cal.getTime())) {
				return false;
			}

			return true;
		}

	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
