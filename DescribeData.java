package InfoShark;

import java.util.ArrayList;
import java.util.Date;

public class DescribeData {
	
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
}
