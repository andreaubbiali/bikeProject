package bikeProject.dataservice;

public enum BikeType {
	NORMAL("Normal", false), ELECTRIC("Electric", false), ELETTRICSEAT("ElectricSeat", true);

	private boolean babySeat;
	private String type;

	public String getType() {
		return this.type;
	}

	public boolean getSimbolo() {
		return this.babySeat;
	}

	private BikeType(String type, boolean babySeat) {
		this.type = type;
		this.babySeat = babySeat;
	}
}
