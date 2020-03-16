import pkg.*;
import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.ArrayList;

import java.io.FileNotFoundException;

public class ConfigManager {

	private String configFilepath;
	// Hashtable<String, String[]> keyMap;
	Wini configini;
	// Dictionary<String, ArrayList<String>> keyMap;
	// ArrayList<ArrayList<String>> charMap;

	public ConfigManager (String configFile)
	{
		configFilepath = configFile;
		assertConfig();
		createConfigIni();
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

	public void createConfigIni ()
	{
		try {
			configini = new Wini(new File(configFilepath));
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	// returns up, down, left, right
	public Hashtable<String, String[]> getMovementKeys ()
	{
		Hashtable<String, String[]> movementKeys = new Hashtable<String, String[]>();
		movementKeys.put("up_key", formatMovementKeys("up_key"));
		movementKeys.put("down_key", formatMovementKeys("down_key"));
		movementKeys.put("left_key", formatMovementKeys("left_key"));
		movementKeys.put("right_key", formatMovementKeys("right_key"));
		return movementKeys;
	}

	// formats the input values
	private String[] formatMovementKeys (String m)
	{
		String temp = configini.get("movement", m);
		String[] all = temp.split(",");
		for (int i = 0; i < all.length; i++) {
			all[i] = all[i].trim();
		}
		return all;
	}
}
