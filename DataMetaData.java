package InfoShark;

import java.util.Date;

public class DataMetaData {
	private String classType; //timeseries is only option TODO: add more options
	private boolean second;
	private boolean minute;
	private boolean hour;
	private boolean day;
	private boolean week;
	private boolean month;
	private boolean year;
	private int second_count;
	private int minute_count;
	private int hour_count;
	private int day_count;
	private int week_count;
	private int month_count;
	private int year_count;
	
	public void setSecond (boolean b) { this.second = b; }
	public void setMinute (boolean b) { this.minute = b; }
	public void setHour (boolean b) { this.hour = b; }
	public void setDay (boolean b) { this.day = b; }
	public void setWeek (boolean b) { this.week = b; }
	public void setMonth (boolean b) { this.month = b; }
	public void setYear (boolean b) { this.year = b; }
	public void setSecondCount (int c) { this.second_count = c; }
	public void setMinuteCount (int c) { this.minute_count = c; }
	public void setHourCount (int c) { this.hour_count = c; }
	public void setDayCount (int c) { this.day_count = c; }
	public void setWeekCount (int c) { this.week_count = c; }
	public void setMonthCount (int c) { this.month_count = c; }
	public void setYearCount (int c) { this.year_count = c; }
	
	public boolean isSecond () { return this.second; }
	public boolean isMinute () { return this.minute; }
	public boolean isHour () { return this.hour; }
	public boolean isDay () { return this.day; }
	public boolean isWeek () { return this.week; }
	public boolean isMonth () { return this.month; }
	public boolean isYear () { return this.year; }
	public int getSecondCount () {return this.second_count; }
	public int getMinuteCount () {return this.minute_count; }
	public int getHourCount () {return this.hour_count; }
	public int getDayCount () {return this.day_count; }
	public int getWeekCount () {return this.week_count; }
	public int getMonthCount () {return this.month_count; }
	public int getYearCount () {return this.year_count; }
	
}
