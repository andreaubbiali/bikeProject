package test.dataservice;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.RackPosition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class isFreeAndAccessibleForBikeTypeTest {

    RackPosition rackPosition = new RackPosition();
    BikeType bikeType = new BikeType();

    @Before
    public void setupBikeType() {
        BikeType bt = new BikeType();
        bt.setID(1);
        rackPosition.setAcceptedBikeType(bt);
    }

    // there is a bike into position
    @Test
    public void isFreeAndAccessibleForBikeType_False_Occupied() {
        Bike bike = new Bike();
        rackPosition.setBike(bike);

        boolean res = rackPosition.isFreeAndAccessibleForBikeType(bikeType);
        assertFalse(res);
    }

    // the rack is broken
    @Test
    public void isFreeAndAccessibleForBikeType_False_RackBroken() {
        rackPosition.setIsBroken(true);

        boolean res = rackPosition.isFreeAndAccessibleForBikeType(bikeType);
        assertFalse(res);
    }

    // bikeType different
    @Test
    public void isFreeAndAccessibleForBikeType_False_DifferentType() {
        rackPosition.setIsBroken(false);

        bikeType.setID(2);

        boolean res = rackPosition.isFreeAndAccessibleForBikeType(bikeType);
        assertFalse(res);
    }

    // all ok
    @Test
    public void isFreeAndAccessibleForBikeType_True() {
        rackPosition.setIsBroken(false);

        bikeType.setID(1);

        boolean res = rackPosition.isFreeAndAccessibleForBikeType(bikeType);
        assertTrue(res);
    }
}