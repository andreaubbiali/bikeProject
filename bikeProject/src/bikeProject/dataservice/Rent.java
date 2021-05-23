package bikeProject.dataservice;

import java.util.Date;

import bikeProject.exception.NotValidRentException;

public class Rent {

	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ long bikeID;
	private /* @ not_null @ */ long userID;
	private /* @ not_null @ */ Date startDate;

	public Rent(String uniqueCode, String password) throws NotValidRentException {
		User user = new User();

		// check the validity of uniqueCode and the password
		if (!user.isValidUniqueCodeSubscriptionForUser(uniqueCode, password)) {
			throw new NotValidRentException("Not found subscription with this uniqueCode and password");
		}

		this.userID = user.getID();
	}

	public void createRent(long bike) {
		Date today = new Date();

		this.bikeID = bike;
		this.startDate = today;

		// TODO
		// create the rent into the database
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getBikeID() {
		return bikeID;
	}

	public void setBikeID(long bikeID) {
		this.bikeID = bikeID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
