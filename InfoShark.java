package InfoShark;

import java.util.ArrayList;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoShark {
	
	public static final String CONFIG = "config.properties";
	public static final String LOG_FILE = "MyLogFile.log";	
	static ArrayList<StockData> stockDataList = new ArrayList<StockData>();
	static ArrayList<SetData> weekSetData = new ArrayList<SetData>();
	static ArrayList<SetData> monthSetData = new ArrayList<SetData>();
	static ArrayList<SetData> yearSetData = new ArrayList<SetData>();
	//static DataMetaData md;
	static DataMetaData md = new DataMetaData();
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

		DataWorkerThread cnt = new DataWorkerThread();
		try {
			while(cnt.mythread.isAlive()) {
				System.out.println("Main thread, will run to completion");
				//remove sleep for better performance
				Thread.sleep(1500);
			}
		} catch(InterruptedException e) {
			System.out.println("Main thread interrupted");
		}
		System.out.println("Main thread run is over" );
	    
		//take a pass at defining the metadata of the input file
		logger.log(Level.INFO, "Beginning to describe the input data");
		DescribeData.defineDateMetadata(md, stockDataList);
		logger.log(Level.INFO, "Timeseries data: seconds " + md.getSecondCount() + ", minutes " 
				+ md.getMinuteCount() + ", hours " + md.getHourCount() + ", days " 
				+ md.getDayCount()  + ", weeks "+ md.getWeekCount() + ", months " 
				+ md.getMonthCount() + ", years " + md.getYearCount());
		
		//stop to describe the data		
		float highPrice = DescribeData.getHighPrice(stockDataList);
		logger.log(Level.INFO, "Highest stock close is " + highPrice);
		float lowPrice = DescribeData.getLowPrice(stockDataList);
		logger.log(Level.INFO, "Lowest stock close is " + lowPrice);
		
		//date range - consider replacing with new stats functions (performance reasons)
		Date firstTradingdate = DescribeData.getFirstDate(stockDataList);
		Date lastTradingdate = DescribeData.getLastDate(stockDataList);
		int tradingSpanDays = DescribeData.getSpanDays(firstTradingdate, lastTradingdate);
		logger.log(Level.INFO, "Number of days stock has been traded " + tradingSpanDays);
		int tradingDays = DescribeData.getNumTradingDays(stockDataList);
		logger.log(Level.INFO, "Number of trading sessions  " + tradingDays);
		
		//start to carve up the data and look for patterns
		DescribeData.startMathFuncs(stockDataList, weekSetData, monthSetData, yearSetData);
		//start the interesting stuff here
		double initialThreshold = 0.10; //10% fed into STD functions (+/- 10% STD)
		int sampleSize = 0;
		
		//TODO: use the sample size to determine of there are any next steps. 
		//TODO: Example, loop there results array looking for additional data
		sampleSize = DescribeData.lookForAnomalies(initialThreshold, weekSetData, stockDataList);
		sampleSize = DescribeData.lookForAnomalies(initialThreshold, monthSetData, stockDataList);
		sampleSize = DescribeData.lookForAnomalies(initialThreshold, yearSetData, stockDataList);
		
		//TODO: Make this part of the functionality multi-threaded
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time (ms): " + (endTime - startTime) );
		System.out.println("Finished running job");
		
		//add code for JFreeChart (https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm)
		
	}	
	//build filter list; math functions
	//first pass to analyze where to start: high level trend
}


