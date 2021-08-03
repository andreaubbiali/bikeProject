package bikeProject.config;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {

    private static Config instance = null;
    private static boolean productionMode;
    private static boolean universityMockResponse;
    private static boolean bankMockResponse;

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
        productionMode = (boolean) jo.get("productionMode");
        universityMockResponse = (boolean) jo.get("universityMockResponse");
        bankMockResponse = (boolean) jo.get("bankMockResponse");

        return inst;
    }

    public boolean getUniversityMockResponse() {

        return universityMockResponse;
    }

    public boolean IsProductionMode() {

        return productionMode;
    }

    public boolean getBankMockResponse() {
        return bankMockResponse;
    }

}