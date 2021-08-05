package bikeProject.dataservice;

import bikeProject.exception.NotValidRentException;
import bikeProject.exception.RackException;
import bikeProject.exception.UserNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class TotemRack implements DataserviceInterface {

    private /* @ not_null @ */ long ID;
    private /* @ not_null @ */ String address;
    private /* @ not_null @ */ List<RackPosition> rackPositionList;

    public long rentBike(String email, String password, BikeType bikeType) throws SQLException, UserNotFoundException, NotValidRentException {

        // check login user
        User user = userDB.login(email, password);
        if ( user == null ) {
            throw new UserNotFoundException("No user founded with this credentials");
        }

        // check a valid subscription of the user
        List<Subscription> userSubscription = user.getSubscription();
        Subscription subscription = new Subscription();
        boolean found = false;
        for ( int i = userSubscription.size() - 1; i >= 0; i-- ) {
            subscription = userSubscription.get(i);

            // check if the subscription is valid
            if ( subscription.isValid() ) {
                found = true;
                break;
            }
        }

        // no subscription valid founded
        if ( !found ) {
            throw new NotValidRentException("No valid subscription founded for the user");
        }

        // check positions of the rack
        if ( rackPositionList.size() == 0 ) {
            throw new NotValidRentException("The rack has no position");
        }

        // find the parkPosition requested
        RackPosition rackPosition = new RackPosition();
        found = false;
        for ( RackPosition position : rackPositionList ) {
            rackPosition = position;

            // check all requisites: a bike into the rack position, bikeType requested by the user, bike not in maintenance and a not broken position
            if ( rackPosition.getBike() != null && rackPosition.getBike().getType().equals(bikeType) && !rackPosition.getBike().isInMaintenance() && !rackPosition.isBroken() ) {

                // try to unlock the rack
                if ( rackPosition.unlock() ) {
                    found = true;
                    break;
                }

            }
        }

        // check errors
        if ( !found ) {
            throw new NotValidRentException("No park position founded with the bike type requested or probably rack positions are broken");
        }

        // start the rental
        Rent rent = new Rent();
        rent.createRent(user, rackPosition.getBike());

        // start the subscription
        subscription.startSubscriptionNow();

        // return the position where to take the bike
        return rackPosition.getID();
    }

    public void returnBike(String email, long rackPositionPlace) throws SQLException, RackException {

        // get the bike type used by the user
        BikeType bikeType = rentDB.getBikeTypeByUserEmail(email);

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