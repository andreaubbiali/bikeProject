package test.dataservice;

import bikeProject.dataservice.BikeType;
import bikeProject.dataservice.BikeTypeEnum;
import bikeProject.dataservice.Tariff;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Tariff_calculateTariffByBikeType_Test {

    private long idBikeType = 1;
    private long idTariff = 1;

    @Before
    public void loadTariffList() {
        List<Tariff> tariffList = new ArrayList<>();

        tariffList.add(createObjTariff(0, BikeTypeEnum.NORMAL, 30));
        tariffList.add(createObjTariff(0.25F, BikeTypeEnum.ELECTRIC, 30));
        tariffList.add(createObjTariff(0.50F, BikeTypeEnum.NORMAL, 60));
        tariffList.add(createObjTariff(0.80F, BikeTypeEnum.ELECTRIC, 60));
        tariffList.add(createObjTariff(0.50F, BikeTypeEnum.NORMAL, 120));
        tariffList.add(createObjTariff(1F, BikeTypeEnum.ELECTRIC, 120));

        Tariff.tariffInstance = tariffList;
    }

    public Tariff createObjTariff(float price, BikeTypeEnum btE, int passedTime) {
        Tariff t = new Tariff();
        t.setTariff(price);
        t.setBikeType(createObjBikeType(btE));
        t.setPassedTimeInMinutes(passedTime);
        t.setID(idTariff);
        idTariff++;
        return t;
    }

    public BikeType createObjBikeType(BikeTypeEnum btE) {
        BikeType b = new BikeType();
        b.setType(btE);
        b.setID(idBikeType);
        idBikeType++;
        b.setBabySeat(false);
        return b;
    }

    @Test
    public void calculateTariffByBikeType() {

        float res = Tariff.calculateTariffByBikeType(29, createObjBikeType(BikeTypeEnum.NORMAL));

        boolean ok = true;
        if ( res != 0F ) {
            ok = false;
        }

        assertTrue(ok);
    }

    @Test
    public void calculateTariffByBikeType1() {

        float res = Tariff.calculateTariffByBikeType(31, createObjBikeType(BikeTypeEnum.NORMAL));
        
        boolean ok = true;
        if ( res != 0.50F ) {
            ok = false;
        }

        assertTrue(ok);
    }

    @Test
    public void calculateTariffByBikeType2() {

        float res = Tariff.calculateTariffByBikeType(59, createObjBikeType(BikeTypeEnum.NORMAL));

        boolean ok = true;
        if ( res != 0.50F ) {
            ok = false;
        }

        assertTrue(ok);
    }

    @Test
    public void calculateTariffByBikeType3() {

        float res = Tariff.calculateTariffByBikeType(61, createObjBikeType(BikeTypeEnum.NORMAL));

        boolean ok = true;
        if ( res != 1F ) {
            ok = false;
        }

        assertTrue(ok);
    }
}