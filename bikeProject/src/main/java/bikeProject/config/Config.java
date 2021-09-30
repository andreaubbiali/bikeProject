package bikeProject.config;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {

    private static Config instance = null;
    private static boolean productionMode;
    private static boolean universityMockResponse;
    private static boolean bankMockResponse;
    private static boolean rackMockResponse;
    private static int maximumRentMinutes;
    private static Float tariffExceed24Hours;

    private Config() {
        // Exists only to defeat instantiation.
    }

    public static Config getInstance() {
        if ( instance == null ) {
            instance = readConfig();
        }
        return instance;
    }

    public static Config readConfig() {
        Config inst = new Config();

        System.out.println("Reading the configuration file");

        // parsing file "config.json"
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("src/main/java/config.json"));
        } catch ( Exception e ) {
            e.printStackTrace();
            return inst;
        }

        // cast obj to jsonobject
        JSONObject jo = (JSONObject) obj;

        // getting variables
        /* value used to know if use test value or do specific request like in production*/
        productionMode = (boolean) jo.get("productionMode");
        /* mocked responses used when the preductionMode is false*/
        universityMockResponse = (boolean) jo.get("universityMockResponse");
        bankMockResponse = (boolean) jo.get("bankMockResponse");
        rackMockResponse = (boolean) jo.get("rackMockResponse");
        /* 2 hours, maximum rental duration*/
        maximumRentMinutes = ((Number) jo.get("maximumRentMinutes")).intValue();

        /* tariff paid by user if exceeds the maximum rent time*/
        tariffExceed24Hours = ((Number) jo.get("tariffExceed24Hours")).floatValue();

        return inst;
    }

    public boolean getUniversityMockResponse() {

        return universityMockResponse;
    }

    public boolean isProductionMode() {

        return productionMode;
    }

    public boolean getBankMockResponse() {

        return bankMockResponse;
    }

    public boolean getRackMockResponse() {
        return rackMockResponse;
    }

    public int getMaximumRentMinutes() {
        return maximumRentMinutes;
    }

    public Float getTariffExceed24Hours() {
        return tariffExceed24Hours;
    }

}