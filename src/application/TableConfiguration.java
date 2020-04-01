package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.paint.Paint;

/**
 * This class takes a jsonObject of the table as a parameter and constructs Table objects and an ArrayList of Pocket objects.
 *
 */
public class TableConfiguration extends Configuration{
	private JSONObject jsonTable;
	private Table table;
	private ArrayList<Pocket> pocketList = new ArrayList<Pocket>();
	
	
	//Min: added a constructor
	/**
	 * Constructor of the TableConfiguration class
	 * @param jsonTable
	 */
	TableConfiguration(JSONObject jsonTable){
		this.jsonTable = jsonTable;
		readData(jsonTable);
	}
	
	/**
	 * Read values from table jsonObject.
	 * @param jsonObject for the table that was read in from ConfigurationProducer
	 */
	@Override
	void readData(JSONObject jsonTable) {
		
		Pocket pocket;
		String tableColour;
		String imgPath;
		Long offsetX;
		Long offsetY;
		Long imgSizeX;
		Long imgSizeY;
		double pocketPosX;
		double pocketPosY;
		double pocketRadius;
		
		// reading values from the JSON table object
		
		Long tableX = (Long) ((JSONObject) jsonTable.get("size")).get("x");
		System.out.println("Table width is " + tableX);
		Long tableY = (Long) ((JSONObject) jsonTable.get("size")).get("y");
		System.out.println("Table height is " + tableY);
		Double tableFriction = (Double) jsonTable.get("friction");
		System.out.println("Table friction is " + tableFriction);
		
		
		//Min: read pocket data and put pockets in the pocket list.
		JSONArray jsonPockets = (JSONArray) jsonTable.get("pockets");
		for (Object obj : jsonPockets) {
			JSONObject jsonPocket = (JSONObject) obj;
			pocketPosX = (double) ((JSONObject)jsonPocket.get("position")).get("x");
			pocketPosY = (double) ((JSONObject)jsonPocket.get("position")).get("y");
			pocketRadius = (double) jsonPocket.get("radius");
			pocket = new Pocket(pocketPosX, pocketPosY, pocketRadius);
			
			// Add a new pocket in the pocket list only when there are less than six pockets in the list
			if (pocketList.size() < 6) { 
				pocketList.add(pocket);
			}
			
		}
		
		try {
			tableColour = (String) jsonTable.get("colour");
			System.out.println("Table Color is " + tableColour);
		} catch (Exception e1) {
			tableColour = null;
		}
		
		
		try {
			imgPath = (String) ((JSONObject) jsonTable.get("image")).get("path");
			System.out.println("Table image path is : " + imgPath);
			offsetX = (Long) ((JSONObject) ((JSONObject) jsonTable.get("image")).get("offset")).get("x");
			System.out.println("Table offsetX is: " + offsetX);
			offsetY = (Long) ((JSONObject) ((JSONObject) jsonTable.get("image")).get("offset")).get("y");
			System.out.println("Table offsetY is: " + offsetY);
			imgSizeX = (Long) ((JSONObject) ((JSONObject) jsonTable.get("image")).get("size")).get("x");
			imgSizeY = (Long) ((JSONObject) ((JSONObject) jsonTable.get("image")).get("size")).get("y");
		} catch (Exception e) {
			imgPath = null;
			offsetX = (Long) null;
			offsetY = (Long) null;
			imgSizeX = (Long)null;
			imgSizeY = (Long)null;
			
		}

		
		if (tableColour == null) {
			table = new ImageTable(new BasicTable(tableFriction, tableX, tableY), imgPath, tableX + offsetX * 2, tableY + offsetY * 2, offsetX, offsetY);
		} else {
			table = new ColorTable(new BasicTable(tableFriction, tableX, tableY), tableColour);
		}
		
	}
	
	
	/**
	 * Get the Table object
	 * @return Table object
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Get the ArrayList of Pocket objects
	 * @return pocketList
	 */
	ArrayList<Pocket> getPockets() {
		return pocketList;
	}


	
}
