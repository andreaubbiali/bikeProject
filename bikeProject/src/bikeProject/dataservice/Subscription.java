package bikeProject.dataservice;

import java.util.Date;
import java.util.UUID;

public class Subscription {

	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ SubscriptionType type;
	private /* @ not_null @ */ String uniqueCode;
	private /* @ not_null @ */ Date startDate;
	private /* @ not_null @ */ Date endDate;
	private /* @ not_null @ */ long userID;

	public Subscription() {
	}

	public Subscription(String uniqueCode) {
		// TODO
		// db.getSubscriptionByUniqueCode
	}

	public String createSubscription(SubscriptionType type, long userID) {
		Date today = new Date();

		this.type = type;
		this.startDate = today;
		this.endDate = today;
		this.userID = userID;

		this.uniqueCode = UUID.randomUUID().toString();

		// TODO
//		this.ID = db.createSubscription(this);

		return uniqueCode;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}
