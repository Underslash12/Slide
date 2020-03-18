import pkg.*;
import org.ini4j.Ini;
import org.ini4j.Wini;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import static java.nio.file.StandardCopyOption.*;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.ArrayList;

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
		File f = new File("/defaults/" + "config.ini");
		System.out.println(f.length());
		assertFile("config.ini");
		assertFile("colors.txt");
	}

	public void assertFile (String filename)
	{
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
				copy(getClass().getResourceAsStream("/defaults/" + filename), getCWD() + filename);
			} catch (IOException ex) {
				System.out.println("IO Error");
				ex.printStackTrace();
			}
		}
	}

	public void copy (InputStream source, String dest)
	{
		System.out.println("Copying ->\n\t" + source + "\nto ->\n\t" + dest);
		try {
			Files.copy(source, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex){
			System.out.println("IO Error");
			ex.printStackTrace();
		}
	}

	public String getCWD ()
	{
		String s = "Error";
		try {
			s = new File("").getAbsolutePath() + "\\";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
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

	public String getColor ()
	{
		return configini.get("color-scheme", "color").trim();
	}
}
