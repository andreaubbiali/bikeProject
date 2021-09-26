package bikeProject.dataservice;

import bikeProject.exception.*;

import java.sql.SQLException;
import java.util.List;

public class TotemRack implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String address;
    private /* @ not_null @ */ List<RackPosition> rackPositionList;

    public long rentBike(String email, String password, BikeType bikeType) throws SQLException, NotValidRentException
            , InvalidSubscriptionException, WrongPasswordException, AccessDeniedException {

        // check login user
        User.login(email, password);

        // get valid subscription of the user
        Subscription subscription = User.getValidSubscription();

        // check positions of the rack
        if ( rackPositionList.size() == 0 ) {
            throw new NotValidRentException("The rack has no position");
        }

        // find the parkPosition requested
        RackPosition rackPosition = new RackPosition();
        for ( RackPosition position : rackPositionList ) {

            if ( position.isBikeTypeRentable(bikeType) ) {
                rackPosition = position;
                break;
            }
        }

        // check errors
        if ( rackPosition != null ) {
            throw new NotValidRentException("No park position founded with the bike type requested or probably rack " + "positions are broken");
        }

        // start the rental
        Rent rent = new Rent();
        rent.createRent(User.getInstance(), rackPosition.getBike());

        // start the subscription(if not already started)
        subscription.startSubscriptionNow();

        // return the position where to take the bike
        return rackPosition.getID();
    }

    public void returnBike(String email, long rackPositionPlace) throws SQLException, RackException,
            InvalidSubscriptionException, PaymentException, InvalidCreditCardException {

        // get the bike type used by the user
        Rent rent = rentDB.getRentByEmail(email);
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

        Subscription userSubscription = rent.getUser().getValidSubscription();
        rent.endRent(User.getCreditCardValidForSubscription(userSubscription));

    }

    public long addNewRack(String address) throws SQLException {
        return totemRackDB.addNewRack(address);
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