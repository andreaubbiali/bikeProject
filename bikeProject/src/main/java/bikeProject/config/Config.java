package bikeProject.config;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {

	private static Config instance = null;
	private static boolean universityMockResponse;

	private Config() {
		// Exists only to defeat instantiation.
	}

	public static Config getInstance() {
		if (instance == null) {
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
		} catch (Exception e) {
			e.printStackTrace();
			return inst;
		}

		// cast obj to jsonobject
		JSONObject jo = (JSONObject) obj;


		// getting variables
		universityMockResponse = (boolean) jo.get("universityMockResponse");

		return inst;
	}

	public boolean getUniversityMockResponse() {
		return universityMockResponse;
	}

}