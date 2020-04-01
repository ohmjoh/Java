package application;


import org.json.simple.JSONObject;
/**
 * Min: An abstract class to specify the common behaviour concrete readers need to have
 *
 */
public abstract class Configuration {
	
	// Min: Instead of having both getTable() and getBall() which ended up with empty method in each class, 
	// only keep common method for ImageTable and ColorTable.
	abstract void readData(JSONObject jsonObject);
	
}
