package InfoShark;

import java.util.Date;

public class StockData {
	private Date date = null;
	private String company = null;
	private String symbol = null;
	private float open;
	private float close;
	private float high;
	private float low;
	private float volume;
	
	public void setStockDate (Date date) { this.date = date; }
	public void setCompany (String name) { this.company = name; }
	public void setSymbol (String name) { this.symbol = name; }
	public void setOpenPrice (float price) { this.open = price; }
	public void setClosePrice (float price) { this.close = price; }
	public void setHighPrice (float price) { this.high = price; }
	public void setLowPrice (float price) { this.low = price; }
	public void setVolume (float volume) {this.volume = volume; }

	public Date getStockDate () { return this.date; }
	public String getCompany () { return this.company; }
	public String getSymbol () { return this.symbol; }
	public float getOpenPrice () { return this.open; }
	public float getClosePrice () { return this.close; }
	public float getHighPrice () { return this.high; }
	public float getLowPrice () { return this.low; }
	public float getVolume () { return this.volume; }
	

}
