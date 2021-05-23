package bikeProject.dataservice;

public class Bike {
	private /* @ not_null @ */ long ID;
	private /* @ not_null @ */ BikeType type;
	private /* @ not_null @ */ boolean isInMaintenance;

	public BikeType getType() {
		return type;
	}

	public void setType(BikeType type) {
		this.type = type;
	}

	public boolean isInMaintenance() {
		return isInMaintenance;
	}

	public void setInMaintenance(boolean isInMaintenance) {
		this.isInMaintenance = isInMaintenance;
	}

}
