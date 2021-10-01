package test.dataservice;

import bikeProject.dataservice.Bike;
import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.BikeTypeEnum;
import bikeProject.dataservice.RackPosition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsBikeTypeRentableTest {

    // bici non rotta, bici esiste, tipo di bici uguale, rack non rotta

    RackPosition rackPosition = new RackPosition();
    BikeType bikeType = new BikeType();

    @Before
    public void setupBikeType() {
        bikeType.setType(BikeTypeEnum.NORMAL);
    }

    // no bike into rack position
    @Test
    public void isBikeTypeRentable_False_NoBike() {

        boolean res = rackPosition.isBikeTypeRentable(bikeType);
        assertFalse(res);
    }

    // bike in maintenance
    @Test
    public void isBikeTypeRentable_False_BikeInMaintenance() {
        Bike bike = new Bike();
        bike.setIsInMaintenance(true);
        rackPosition.setBike(bike);

        boolean res = rackPosition.isBikeTypeRentable(bikeType);
        assertFalse(res);
    }

    // bike ok but different type
    @Test
    public void isBikeTypeRentable_False_BikeDifferentType() {
        Bike bike = new Bike();
        bike.setIsInMaintenance(false);

        BikeType bt = new BikeType();
        bt.setType(BikeTypeEnum.ELECTRIC);

        bike.setType(bt);
        rackPosition.setBike(bike);

        boolean res = rackPosition.isBikeTypeRentable(bikeType);
        assertFalse(res);
    }

    // bike ok, equal type but rack broken
    @Test
    public void isBikeTypeRentable_False_BrokenRack() {
        Bike bike = new Bike();
        bike.setIsInMaintenance(false);

        BikeType bt = new BikeType();
        bt.setType(BikeTypeEnum.NORMAL);

        bike.setType(bt);
        rackPosition.setBike(bike);

        rackPosition.setIsBroken(true);

        boolean res = rackPosition.isBikeTypeRentable(bikeType);
        assertFalse(res);
    }

    // all ok
    @Test
    public void isBikeTypeRentable_True() {
        Bike bike = new Bike();
        bike.setIsInMaintenance(false);

        BikeType bt = new BikeType();
        bt.setType(BikeTypeEnum.NORMAL);

        bike.setType(bt);
        rackPosition.setBike(bike);

        rackPosition.setIsBroken(false);

        boolean res = rackPosition.isBikeTypeRentable(bikeType);
        assertTrue(res);
    }
}