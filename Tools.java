package InfoShark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Tools {

	static Properties prop = new Properties();
	
	public static void setLogger() {
	    FileHandler fh;

	    try {
	    	// This block configure the logger with handler and formatter
	    	fh = new FileHandler(InfoShark.LOG_FILE, true);
	    	InfoShark.logger.addHandler(fh);
	    	InfoShark.logger.setLevel(Level.ALL);
	    	SimpleFormatter formatter = new SimpleFormatter();
	    	fh.setFormatter(formatter);
	    } catch (SecurityException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void loadConfig() {
	    String fileName = InfoShark.CONFIG;
	    InfoShark.logger.log(Level.FINER, "Grabbing config from " + fileName);
	    InputStream is = null;
		try {
			is = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    //dump them for now
	    Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			InfoShark.logger.log(Level.FINER, "Key: " + key + ", Value: " + value);
		}
	 }
}
