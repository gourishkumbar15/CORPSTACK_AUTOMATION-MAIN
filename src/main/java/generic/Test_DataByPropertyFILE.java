package generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Test_DataByPropertyFILE implements Framework_Constants
{
	public static FileInputStream fis;
	
	public static String getData(String key)
	{
		try {
			String projectPath = System.getProperty("user.dir");
			fis = new FileInputStream(projectPath + "/" + PROPERTY_PATH);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Could not find properties file at: " + System.getProperty("user.dir") + "/" + PROPERTY_PATH);
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			System.err.println("Error: Could not load properties file");
			e.printStackTrace();
		}
		String value = prop.getProperty(key);
		if (value == null) {
			System.err.println("Warning: Property '" + key + "' not found in properties file");
		}
		return value;
	}
}
