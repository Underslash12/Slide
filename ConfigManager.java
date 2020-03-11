import pkg.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.ArrayList;

import java.io.FileNotFoundException;

public class ConfigManager {

	private String configFilepath;
	Hashtable<String, String[]> keyMap;
	// Dictionary<String, ArrayList<String>> keyMap;
	// ArrayList<ArrayList<String>> charMap;

	public ConfigManager (String configFile)
	{
		configFilepath = configFile;
		assertConfig();
		createKeyMap();
	}

	public void assertConfig ()
	{
		File config = new File(configFilepath);
		if (!config.exists()) {
			try {
				config.createNewFile();
				initEmpty();
			} catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
	}

	public void initEmpty ()
	{
		try {
	      FileWriter configWriter = new FileWriter(configFilepath);
	      configWriter.write(
		  	"[seperate multiple keys with commas]\n" +
		  	"upKey: w\n" +
			"downKey: s\n" +
			"leftKey: a\n" +
			"rightKey: d\n"
		  );
	      configWriter.close();
	  } catch (Exception e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	}

	public void createKeyMap ()
	{
		try {
			keyMap = new Hashtable<String, String[]>();
			File config = new File(configFilepath);
			Scanner configReader = new Scanner(config);
			while (configReader.hasNextLine()) {
				String line = configReader.nextLine();
				System.out.println(line);
				if (!line.substring(0, 1).equals("[")) {
					int tempIndex = line.indexOf(":");
					String k = line.substring(0, tempIndex);
					// ArrayList<String> keys = new ArrayList<String>(Arrays.asList());
					String[] v = line.substring(tempIndex + 2, line.length()).split(",");
					keyMap.put(k, v);
				}
			}
			System.out.println(keyMap);
			configReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// returns up, down, left, right
	// public String[][] getMovementKeys ()
	// {
	// 	return {keyMap.get("upKey"), keyMap.get("downKey"), keyMap.get("leftKey"), keyMap.get("rightKey")};
	// }
	public Hashtable<String, String[]> getMovementKeys ()
	{
		Hashtable<String, String[]> movementKeys = new Hashtable<String, String[]>();
		movementKeys.put("upKey", keyMap.get("upKey"));
		movementKeys.put("downKey", keyMap.get("downKey"));
		movementKeys.put("leftKey", keyMap.get("leftKey"));
		movementKeys.put("rightKey", keyMap.get("rightKey"));
		return movementKeys;
	}
}
