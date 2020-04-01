package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Min: This class contains the method to read the configuration file
 *
 */
public class ConfigurationProducer {
	
	//Min: private attributes
	private JSONObject jsonTable;
	private JSONObject jsonBalls;
	
	/**
	 * Min: Constructor of ConfigurationProducer class
	 * @param filePath
	 * ConfigurationProducer object is created in GameEngine with the file path passed into it.
	 * 
	 */
	public ConfigurationProducer(String filePath) {
		readConfig(filePath);
	}
	
	// Min: removed singleton for extensibility -> multiple config.
	
	
	//MJ: Moved the readConfig method from gameEngine to here to follow the single responsibility principle 
	/**
	 * Read configuration from the given filepath and load it into GameEngine class.
	 * The actual reading of the data is delegated to respective reader and producer classes
	 * @param filePath: location of the file
	 */
	private void readConfig(String filePath) {
		
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader(filePath));
			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;
			// reading the Table section:
			jsonTable = (JSONObject) jsonObject.get("Table");
			// reading the "Balls" section:
			jsonBalls = (JSONObject) jsonObject.get("Balls");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}
	
	///Min: create getters
	/**
	 * Get JSONObject for table
	 * @return jsonTalbe
	 */
	JSONObject getJsonTable() {
		return jsonTable;
	}
	
	/**
	 * Get JSONObject for balls
	 * @return jsonBalls
	 */
	JSONObject getJsonBalls() {
		return jsonBalls;
	}
	
	
}
