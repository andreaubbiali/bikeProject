package bikeProject.dataservice;

import bikeProject.exception.*;

import java.sql.SQLException;
import java.util.List;

public class TotemRack implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String address;
    private /* @ not_null @ */ List<RackPosition> rackPositionList;

    public long rentBike(BikeType bikeType) throws SQLException, NotValidRentException, InvalidSubscriptionException,
            AccessDeniedException, RackException {

        // check positions of the rack
        if ( rackPositionList.size() == 0 ) {
            throw new NotValidRentException("The rack has no position");
        }

        // check the user doesn't have an active rent
        if ( User.haveUserActiveRent() != null ) {
            throw new NotValidRentException("The user can't rent a bike. There is an active rent yet");
        }

        // get valid subscription of the user
        Subscription subscription = User.getValidSubscription();

        // find the parkPosition requested
        RackPosition rackPosition = null;
        for ( RackPosition position : rackPositionList ) {

            if ( position.isBikeTypeRentable(bikeType) ) {
                rackPosition = position;
                break;
            }
        }

        // check errors
        if ( rackPosition == null ) {
            throw new NotValidRentException("No park position founded with the bike type requested or probably rack " + "positions are broken");
        }

        // start the rental
        Rent rent = new Rent();
        rent.createRent(rackPosition.getBike(), subscription);

        // start the subscription(if not already started)
        subscription.startSubscriptionNow();

        // unlock the rack position with the bike to be rented
        if ( !rackPosition.unlock() ) {
            throw new RackException("The rack position have some problem, retry");
        }

        // return the position where to take the bike
        return rackPosition.getID();
    }

    public void returnBike(String email, long rackPositionPlace) throws SQLException, RackException,
            InvalidSubscriptionException, PaymentException, InvalidCreditCardException, NotValidRentException {

        // get the user active rent
        Rent rent = User.haveUserActiveRent();
        if ( rent == null ) {
            throw new NotValidRentException("The user can't return a bike. There are not active rent");
        }

        // get the bike type used by the user
        BikeType bikeType = rent.getBike().getType();

        boolean found = false;

        // check if the rack position allow the bikeType
        for ( RackPosition position : rackPositionList ) {

            // find the rack position requested
            if ( position.getID() == rackPositionPlace ) {
                found = true;

                if ( position.isBroken() ) {
                    throw new RackException("The rack position selected is broken");
                } else if ( !position.getAcceptedBikeType().equals(bikeType) ) {
                    throw new RackException("The rack position selected is for bike of type: " + position.getAcceptedBikeType().getType() + " not for: " + bikeType.getType());
                }

                // unlock the rack position
                if ( !position.lock() ) {
                    throw new RackException("Something wrong trying to unlock the position requested on the rack");
                }
            }

        }

        if ( !found ) {
            throw new RackException("No position with this rack position number");
        }

        Subscription userSubscription = User.getValidSubscription();
        rent.endRent(User.getCreditCardValidForSubscription(userSubscription));

    }

    public long addNewRack(String address) throws SQLException {
        return totemRackDB.addNewRack(address);
    }

    public void deleteRack() throws SQLException {
        totemRackDB.delete(this.ID);
    }

    public List<TotemRack> getAllRacks() throws SQLException {
        return totemRackDB.getAllRacks();
    }

    public void getRackByID(long id) throws SQLException {
        TotemRack rack = totemRackDB.getRackByID(id);
        setID(rack.getID());
        setAddress(rack.getAddress());
        setRackPositionList(totemRackDB.getRackPositionsByRackID(this.ID));
    }

    public void updateRackAddress(String address) throws SQLException {
        totemRackDB.updateRackAddress(this.ID, address);
        setAddress(address);
    }

    public void addNewBike(BikeType bikeType) throws SQLException, RackException {

        // check if there is a free place for this type of bike
        RackPosition rp = isThereAFreePlaceForBikeType(bikeType);
        if ( rp == null ) {
            throw new RackException("There isn't place for selected bikeType");
        }

        // create the bike
        Bike bike = new Bike();
        bike.createNewBike(bikeType);

        // add the bike into this place
        rp.addBike(bike);
    }

    public RackPosition isThereAFreePlaceForBikeType(BikeType bikeType) {

        for ( RackPosition rp : rackPositionList ) {

            if ( rp.getBike() == null && rp.getAcceptedBikeType().getID() == bikeType.getID() ) {
                return rp;
            }
        }

        return null;
    }

    // GETTERS AND SETTERS

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RackPosition> getRackPositionList() {
        return rackPositionList;
    }

    public void setRackPositionList(List<RackPosition> parkPositionList) {
        this.rackPositionList = parkPositionList;
    }

}