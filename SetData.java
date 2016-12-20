package InfoShark;

import java.util.Date;

public class SetData {
	private String type 	= null;
	private Date startDate 	= null;
	private Date endDate 	= null;
	private double mean 	= 0;
	private double min 		= 0;
	private double max 		= 0;
	private double stdev 	= 0;
	private double quadmean = 0;
	private double skewness = 0;
	private double growthRate = 0;
	private double growth = 0;
	private double percentile = 0;

	//constructor
	public SetData(String type, Date start, Date end, double mean, double min, double max, double stdev, double quadmean, double skewness, double growthRate, double growth, double percentile) {
		setType (type);
		setStartDate (start);
		setEndDate (end);
		setMean (mean);
		setMin (min);
		setMax (max);
		setStdev (stdev);
		setQuadmean (quadmean);
		setSkewness (skewness);
		setGrowthRate (growthRate);
		setGrowth (growth);
		setPercentile (percentile);
	}
	
	public void setType (String s) { this.type = s; }
	public void setStartDate (Date date) { this.startDate = date; }
	public void setEndDate (Date date) { this.endDate = date; }
	public void setMean (double d) {this.mean = d; }
	public void setMin (double d) {this.min = d; }
	public void setMax (double d) {this.max = d; }
	public void setStdev (double d) {this.stdev = d; }
	public void setQuadmean (double d) {this.quadmean = d; }
	public void setSkewness (double d) {this.skewness = d; }
	public void setGrowthRate (double d) {this.growthRate= d; }
	public void setGrowth (double d) {this.growth = d; }
	public void setPercentile (double d) {this.percentile = d; }
	

	public String getType () { return this.type; }
	public Date getStartDate () { return this.startDate; }
	public Date getEndDate () { return this.endDate; }
	public double getMean () {return this.mean; }
	public double getMin () {return this.min; }
	public double getMax () {return this.max; }
	public double getStdev () {return this.stdev; }
	public double getQuadmean () {return this.quadmean; }
	public double getSkewness () {return this.skewness; }
	public double getGrowthRate () { return this.growthRate; }
	public double getGrowth () { return this.growth; }
	public double getPercentile () { return this.percentile; }
	
}

