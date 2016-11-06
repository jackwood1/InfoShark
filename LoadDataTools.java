package InfoShark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadDataTools {
	public static void loadCsv(String csvFile, ArrayList<StockData> stocks) {
		String line = "";
        String cvsSplitBy = ",";
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        //String dateInString = "7-Jun-2013";
        
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			int counter = 0;
            while ((line = br.readLine()) != null) {

            	String[] field = line.split(cvsSplitBy);
            	
            	if (counter == 0) {
            		// header line
            		//TODO: do something with header
            	}
            	else {
            		StockData stock = new StockData();
            		
            		try {
						Date date = formatter.parse(field[1]);
						stock.setStockDate(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		stock.setCompany(field[0]);
            		stock.setOpenPrice(Float.parseFloat(field[2]));
            		stock.setHighPrice(Float.parseFloat(field[3]));
            		stock.setLowPrice(Float.parseFloat(field[4]));
            		stock.setClosePrice(Float.parseFloat(field[5]));
            		stock.setVolume(Float.parseFloat(field[6]));
            		stocks.add(stock);	
            	}
            	counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
