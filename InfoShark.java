package InfoShark;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoShark {
	
	public static final String CONFIG = "config.properties";
	public static final String LOG_FILE = "MyLogFile.log";
	static ArrayList<StockData> stockDataList = new ArrayList<StockData>();
	public static Logger logger = Logger.getLogger("MyLog");

	public static void main(String[] args) {
		
		//capture some initial timing
		System.out.println("Started running the job");
		final long startTime = System.currentTimeMillis();
		
		//Initialize
		Tools.setLogger(); 
		Tools.loadConfig();

		//Grab the input file
		System.out.println("datafile " + Tools.prop.getProperty("dataFile"));
		String dataFile = Tools.prop.getProperty("dataFile");
		logger.log(Level.INFO, "Working Directory = " + System.getProperty("user.dir"));
		logger.log(Level.INFO,"Loading a data file " + dataFile);
		
		LoadDataTools.loadCsv(dataFile, stockDataList);
		logger.log(Level.INFO, "Size of the loaded array is " + stockDataList.size());
		
		//stop to describe the data		
		float highPrice = DescribeData.getHighPrice(stockDataList);
		logger.log(Level.INFO, "Highest stock close is " + highPrice);
		float lowPrice = DescribeData.getLowPrice(stockDataList);
		logger.log(Level.INFO, "Lowest stock close is " + lowPrice);
		
		//date range
		Date firstTradingdate = DescribeData.getFirstDate(stockDataList);
		Date lastTradingdate = DescribeData.getLastDate(stockDataList);
		int tradingSpanDays = DescribeData.getSpanDays(firstTradingdate, lastTradingdate);
		logger.log(Level.INFO, "Number of days stock has been traded " + tradingSpanDays);
		int tradingDays = DescribeData.getNumTradingDays(stockDataList);
		logger.log(Level.INFO, "Number of trading sessions  " + tradingDays);
		Date lowPriceDate = DescribeData.getDateWithPrice(lowPrice, stockDataList);
		Date highPriceDate = DescribeData.getDateWithPrice(highPrice, stockDataList);
		logger.log(Level.INFO, "Date of lowest price  " + lowPriceDate);
		logger.log(Level.INFO, "Date of highest price  " + highPriceDate);
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time (ms): " + (endTime - startTime) );
		System.out.println("Finished running job");
		
	}	
	//build filter list; math functions
	//first pass to analyze where to start: high level trend
}


