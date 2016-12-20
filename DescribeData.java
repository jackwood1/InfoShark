package InfoShark;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DescribeData {
	static double percentile = 50;
	
	public static int defineDateMetadata (DataMetaData md, ArrayList<StockData> stocks) {
		int rc = 0;
		//hard coded to improve performance
		//can catch rc error TODO: Implmenent rc validation
	    boolean seriesInterval1 = diffDates (md, stocks.get(0).getStockDate(), stocks.get(1).getStockDate());
	    boolean seriesInterval2 = diffDates (md, stocks.get(1).getStockDate(), stocks.get(2).getStockDate());
	    
	    //is the date range the same
	    //TODO: add exception around not the same
	    if (seriesInterval1 == seriesInterval2) {
	    	System.out.println("All looks good and the boolean is " + seriesInterval2);
	    } else {
	    	return 0; //return code of 0 is error or noting
	    }	    
	    //look for gaps in the range
	    
	    int seconds = 0, minutes = 0, hours = 0, days = 0, weeks = 0, months = 0, years = 0;
	    int cur_month = 0;
	    int cur_year = 1900;
	    for (int i = 0; i< stocks.size(); i++){
	    	//loop and look at day, then week, then month, and finally year (++ each accordingly)
	    	//cheating because I know these are days, can do expensive math above
	    	Calendar c = Calendar.getInstance();
	    	c.setTime(stocks.get(i).getStockDate());
	    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	    	int temp_year = c.get(Calendar.YEAR);
	        int temp_month = c.get(Calendar.MONTH);
	        int temp_day = c.get(Calendar.DAY_OF_MONTH);
	        
	        days++;
	    	if (dayOfWeek == 5) {
	    		weeks++;
	    		//picking wednesday since usually no holidays
	    		//TODO: very hacky, find a better way
	    	}
	    	if (temp_month != cur_month) {
	    		months++;
	    		cur_month = temp_month;
	    	}
	    	if (temp_year != cur_year) {
	    		years++;
	    		cur_year = temp_year;
	    	}
	    }
	    
	    //now write this data to metadata struct
	    md.setSecondCount(seconds);
	    md.setMinuteCount(minutes);
	    md.setHourCount(hours);
	    md.setDayCount(days);
	    md.setWeekCount(weeks);
	    md.setMonthCount(months);
	    md.setYearCount(years);
	    
	    return rc;

	}
	
	private static boolean diffDates (DataMetaData md, Date date1, Date date2) {
		long epoch1 = date1.getTime();
	    long epoch2 = date2.getTime();
	    long epoch_diff = epoch2 - epoch1;
	    //epoch too granular so shaving a few seconds off
	    epoch_diff = epoch_diff/1000;
	    //assumes roundness catch not true statement	    
	    
	    boolean rc = true;
	    if (epoch_diff == 1) {
	    	md.setSecond(rc);
	    } else if (epoch_diff == 60) {
	    	md.setMinute(rc);
	    } else if (epoch_diff == 60 * 60) {
	    	md.setHour(rc);
	    } else if (epoch_diff == 60 * 60 * 24) {
	    	md.setDay(rc);
	    } else if (epoch_diff == 60 * 60 * 24 * 3 ) {
	    	//TODO: Test weekend
	    	//md.setWeekend(rc);
	    } else if (epoch_diff == 60 * 60 * 24 * 7 ) {
	    	md.setWeek(rc);
	    } else {
	    	//TODO: implement month (hard with variable days in month maybe range of days from 28-31)
	    	//TODO: implement year (check 364 or 365)
	    	rc = false;
	    }
	    return rc;
	}
	
	public static float getHighPrice (ArrayList<StockData> stocks) {
		float high = 0;
		
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getClosePrice() > high){
				high = stocks.get(i).getClosePrice();
			}
		}
		return high;
	}
	
	public static float getLowPrice (ArrayList<StockData> stocks) {
		float low = 99999999;		
		for (int i = 0; i < stocks.size(); i++ ) {
			if (stocks.get(i).getClosePrice() < low) {
				low = stocks.get(i).getClosePrice();
			}
		}
		return low;
	}
	
	public static Date getFirstDate (ArrayList<StockData> stocks) {
		return stocks.get(0).getStockDate();
	}
	
	public static Date getLastDate (ArrayList<StockData> stocks) {
		int lastIndex = stocks.size();
		return stocks.get(lastIndex-1).getStockDate();
	}
	
	public static int getSpanDays (Date start, Date end) {
	    long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

	    long diff[] = new long[] { 0, 0, 0, 0 };
	    /* sec */diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
	    /* min */diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
	    /* hours */diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
	    /* days */diff[0] = (diffInSeconds = (diffInSeconds / 24));
	    /*System.out.println(String.format(
	        "%d day%s, %d hour%s, %d minute%s, %d second%s ago",
	        diff[0],
	        diff[0] > 1 ? "s" : "",
	        diff[1],
	        diff[1] > 1 ? "s" : "",
	        diff[2],
	        diff[2] > 1 ? "s" : "",
	        diff[3],
	        diff[3] > 1 ? "s" : "")); 
	        */
	    return Math.round(diff[0]);
	}
	
	public static Date getDateWithPrice (float price, ArrayList<StockData> stocks) {
		Date tradingDate = null;
		//yes, I know there might be many,many dates matching a stock
		//FIX: Maybe return an array later. Capture multiple days with same close price
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getClosePrice() == price){
				tradingDate = stocks.get(i).getStockDate();
				break;
			}
		}
		return tradingDate;
	}
	
	public static int getNumTradingDays (ArrayList<StockData> stocks) {
		int count = stocks.size()-1;
		return count;
	}

	public static void startMathFuncs(ArrayList<StockData> stocks, ArrayList<SetData> weekSetData, ArrayList<SetData> monthSetData, ArrayList<SetData> yearSetData) {		
		DescriptiveStatistics weekStats = new DescriptiveStatistics();
		DescriptiveStatistics monthStats = new DescriptiveStatistics();
		DescriptiveStatistics yearStats = new DescriptiveStatistics();

		Frequency weekFreq 	= new Frequency();
		Frequency monthFreq	= new Frequency();
		Frequency yearFreq	= new Frequency();
		
		int seconds = 0, minutes = 0, hours = 0, days = 0, weeks = 0, months = 0, years = 0;
	    int cur_month = 0;
	    int cur_year = 1900;
	   
	    //use these value to track the first of each cat
	    Date startOfWeek = stocks.get(0).getStockDate();
	    Date startOfMonth = stocks.get(0).getStockDate();
	    Date startOfYear = stocks.get(0).getStockDate();
		
		for (int i = 0; i < stocks.size(); i++) {
			//need to add logic to track the bounds in here
			//need multiple versions of stats arrays
			//reset for week over week (52 in year, track more whole span), month over month (see notes for weeks), years (same here)
			//FIX: bug if there are two identical values....I think. This would screw up some math functions where thngs are weighted MEANS
			weekFreq.addValue(stocks.get(i).getClosePrice());
			monthFreq.addValue(stocks.get(i).getClosePrice());
			yearFreq.addValue(stocks.get(i).getClosePrice());
			
			weekStats.addValue(stocks.get(i).getClosePrice());
			monthStats.addValue(stocks.get(i).getClosePrice());
			yearStats.addValue(stocks.get(i).getClosePrice());

			Calendar c = Calendar.getInstance();
	    	c.setTime(stocks.get(i).getStockDate());
	    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	    	int temp_year = c.get(Calendar.YEAR);
	        int temp_month = c.get(Calendar.MONTH);
	        //int temp_day = c.get(Calendar.DAY_OF_MONTH);
	        
	        //set the dates if not define/null
	        if (startOfWeek == null) {
	        	startOfWeek = stocks.get(i).getStockDate();
	        }
	        if (startOfMonth == null) {
			    startOfMonth = stocks.get(i).getStockDate();	        	
	        }
	        if (startOfYear == null) {
			    startOfYear = stocks.get(i).getStockDate();	        	
	        }
	        
	        //TODO: rather than hacky calendar function just track start dates for week, month, and year. Reset like below.
	        
	        if (dayOfWeek == 5) {
	        	//picking wednesday since usually no holidays
	    		//TODO: very hacky, find a better way
	        	//here is where I need to compute weekly status (create new object to push into arraylist!!!!
	        	
	        	Date endDate = stocks.get(i).getStockDate();

	        	double mean = weekStats.getMean();
	        	double min = weekStats.getMin();
	        	double max = weekStats.getMax();
	        	double stdev = weekStats.getStandardDeviation();
	        	double quadmean = weekStats.getQuadraticMean();
	        	double skewness = weekStats.getSkewness();
	        	double growthRate = (max - min)/max;
	        	double growth = max - min;
	        	double percent = weekStats.getPercentile(percentile);
	        	
	        	weekSetData.add(weeks, new SetData("week", startOfWeek, endDate, mean, min, max, stdev, quadmean, skewness, growthRate, growth, percent) );
	        	weekStats.clear();
	        	weekFreq.clear();
	        	startOfWeek = null;
	        	weeks++;
	    	}
	    	if (temp_month != cur_month) {
	    		Date endDate = stocks.get(i).getStockDate();
	    		double mean = monthStats.getMean();
	        	double min = monthStats.getMin();
	        	double max = monthStats.getMax();
	        	double stdev = monthStats.getStandardDeviation();
	        	double quadmean = monthStats.getQuadraticMean();
	        	double skewness = monthStats.getSkewness();
	        	double growthRate = (max - min)/max;
	        	double growth = max - min;
	        	double percent = monthStats.getPercentile(percentile);
	        	
	        	//calculate growth by looking back in time (7 intervals), treat differently for first set. 
	        	// double growthRate, double growth, double percentile) {
	        	monthSetData.add(months, new SetData("month", startOfMonth, endDate, mean, min, max, stdev, quadmean, skewness, growthRate, growth, percent) );
	        	
	        	monthStats.clear();
	        	monthFreq.clear();
	        	startOfMonth = null;
	        	months++;
	    		cur_month = temp_month;
	    	}
	    	if (temp_year != cur_year) {
	    		Date endDate = stocks.get(i).getStockDate();
	    		double mean = yearStats.getMean();
	        	double min = yearStats.getMin();
	        	double max = yearStats.getMax();
	        	double stdev = yearStats.getStandardDeviation();
	        	double quadmean = yearStats.getQuadraticMean();
	        	double skewness = yearStats.getSkewness();
	        	double growthRate = (max - min)/max;
	        	double growth = max - min;
	        	double percent = yearStats.getPercentile(percentile);
	        	
	        	//calculate growth by looking back in time (7 intervals), treat differently for first set. 
	        	// double growthRate, double growth, double percentile) {
	        	yearSetData.add(years, new SetData("year", startOfMonth, endDate, mean, min, max, stdev, quadmean, skewness, growthRate, growth, percent) );
	        	
	        	yearStats.clear();
	        	yearFreq.clear();
	        	startOfYear = null;
	    		//BUG: Going to lose month and week stats at value reset. Overriding index written in other conditions above
	    		weeks = 0; //reset for the year
	    		months = 0; //reset for the year
	        	years++;
	    		cur_year = temp_year;
	        	//TODO: then reset array (double check I did this right) - Test it
	    		//BUG: There are a few years with NaN values. No immediate pattern
	    	}
		}
		//start the magic here
		System.out.println("Week blocks: " + weekSetData.size());
		System.out.println("Month blocks: " + monthSetData.size());
		System.out.println("Year blocks: " + yearSetData.size());
	}

	public static int lookForAnomalies (double threshold, ArrayList<SetData> dataPoints, ArrayList<StockData> stocks) {
		
		int counter = 0;
		double rangeStdev = 0;
		//good through each period of time and look at the count out of STD
		for (int i = 0; i < dataPoints.size(); i++) {
			Date startDate = dataPoints.get(i).getStartDate();
			Date endDate = dataPoints.get(i).getEndDate();
			//FIX: some of the start date values are null, skipping for now
			if (startDate == null || endDate == null) {
				continue;
			}
			
			rangeStdev 				= dataPoints.get(i).getStdev();
			double rangePercentile 	= dataPoints.get(i).getPercentile();
			double rangeMean		= dataPoints.get(i).getMean();
			
			for (int j = 0; j < stocks.size(); j ++) {
				Date stockDate = stocks.get(i).getStockDate();
				if (stockDate.after(startDate) && stockDate.before(endDate) ) {
					//System.out.println("Dates are: stockdate " + stockDate + ", before " + ", end" + endDate);
					float stockPrice = stocks.get(i).getClosePrice();
					//System.out.println("price: " + stockPrice + ", rangeMean: " + rangeMean + ", stdev: " + rangeStdev + ", threshold: " + threshold);
					if (stockPrice > (rangeMean + (rangeStdev + (rangeStdev * threshold))) || stockPrice < (rangeMean -(rangeStdev + rangeStdev * threshold))) {
						counter++;				
					}
				}
			}
			//TODO: I want to replace this with a decay double function
			// I broke the Machine Learning
			if (rangeStdev > (rangeStdev + (rangeStdev * threshold)) || rangeStdev < (rangeStdev + (rangeStdev * threshold))) {
				System.out.println("Cannot squeeze blood from a stone....window with STDev!");
				return 0;
			}
			if (threshold < .001 || threshold > 2) {
				System.out.println("Found the perfect range. Look at the output for anomalies.");
				System.exit(1);
			}
			if (dataPoints.size() < counter+100) {
				threshold *= 1.1; //10% size increase
				//FIX: Infinite loop possible if value too small. Int will force to same value
				System.out.println("Sample size too large (" + counter + "), adjusting threshold up: " + threshold );
				lookForAnomalies(threshold, dataPoints, stocks);
			}
			if (dataPoints.size() >counter-100) {
				threshold *= .9; //10% size decrease
				//add ceiling/floor function for rounding
				//FIX: Infinite loop possible if value too small. Int will force to same value
				System.out.println("Sample size too small (" + counter + "), adjusting threshold up: " + threshold );
				lookForAnomalies(threshold, dataPoints, stocks);
			}
			System.out.println("Found a good sample");
			//push the results into an array and move to next batch
		}
		return 0;
	}
}
