package InfoShark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
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
	
	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
	    //TODO: This does not consider holidays. Add soemthing better later
		Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);

	    int workDays = 0;

	    //Return 0 if start and end are the same
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 0;
	    }

	    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	    }

	    do {
	       //excluding start date
	        startCal.add(Calendar.DAY_OF_MONTH, 1);
	        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	            ++workDays;
	        }
	    } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
	    
	    return workDays;
	}
	
	public static Date getPreviousYear(Date endDate) {
		//TODO: need more elegant way later to look back one year
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, -365);
		Date startDate = cal.getTime();
		int lookBackDays = getWorkingDaysBetweenTwoDates(startDate, endDate);
		
		cal.setTime(endDate);
		//make lookback negative
		lookBackDays *= -1;
		
		cal.add(Calendar.DATE, lookBackDays);
		startDate = cal.getTime();
		
		return startDate;
	}
	
	//TODO:Add moving average functions smooth the curve when STDev values
}
