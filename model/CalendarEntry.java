package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarEntry {

	private String description;
	private String date;			//: {DAY OF WEEK] [DATE] [MONTH]
	private String time;
	private String location;
	
	public CalendarEntry() {
		
		date = "";
		description = "";
		time = "";
		location = "";
		
	}
	
	public void setDay(String day) {
		if(day.equals("")){return;}
		String dayInput = day;
		dayInput = dayInput.substring(0, 1).toUpperCase() + dayInput.substring(1);
		date += dayInput;
	}
	
	public void setDate(String date) {
		if(date.equals("")){return;}
		this.date += " " + date;
	}
	
	public void setMonth(String month) {
		if(month.equals("")){return;}
		String monthInput = month;
		monthInput = monthInput.substring(0, 1).toUpperCase() + monthInput.substring(1);
		this.date += " " + monthInput;
	}
	
	public void setLocation(String location) {
		if(location.equals("")){return;}
		String locationInput = location;
		locationInput = locationInput.replace("at ", "");
		locationInput = locationInput.trim();
		locationInput = locationInput.substring(0, 1).toUpperCase() + locationInput.substring(1);
		
		this.location = locationInput;
	}
	
	public void setDescription(String text) {
		
		description = text;
		
	}
	
	public void setTomorrow() {
		
		String dayOfWeek = "";
		String monthString = "";
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {dayOfWeek = "Sunday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 2) {dayOfWeek = "Monday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 3) {dayOfWeek = "Tuesday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 4) {dayOfWeek = "Wednesday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 5) {dayOfWeek = "Thursday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 6) {dayOfWeek = "Friday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 7) {dayOfWeek = "Saturday";}
		
		String suffix = "";
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){ suffix = "st"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 2){ suffix = "nd"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 3){ suffix = "rd"; }
        else { suffix = "th"; }
		
        if(calendar.get(Calendar.MONTH) == 0) {monthString = "January";}
        else if(calendar.get(Calendar.MONTH) == 1) {monthString = "February";}
        else if(calendar.get(Calendar.MONTH) == 2) {monthString = "March";}
        else if(calendar.get(Calendar.MONTH) == 3) {monthString = "April";}
        else if(calendar.get(Calendar.MONTH) == 4) {monthString = "May";}
        else if(calendar.get(Calendar.MONTH) == 5) {monthString = "June";}
        else if(calendar.get(Calendar.MONTH) == 6) {monthString = "July";}
        else if(calendar.get(Calendar.MONTH) == 7) {monthString = "August";}
        else if(calendar.get(Calendar.MONTH) == 8) {monthString = "September";}
        else if(calendar.get(Calendar.MONTH) == 9) {monthString = "October";}
        else if(calendar.get(Calendar.MONTH) == 10) {monthString = "November";}
        else if(calendar.get(Calendar.MONTH) == 11) {monthString = "December";}
        
        
		date = dayOfWeek + " " + calendar.get(Calendar.DAY_OF_MONTH) + suffix + " " + monthString;
		
	}
	
	public void setFormattedDate(String date) {
		
		if(date.equals("")) {return;}
		
		String[] array = date.split("(/|\\.|-)");  
		
		String day = array[0];
		String month = array[1];
		String year = array[2];
		
		if(year.length() == 2){year = "20" + year;}
		
		int dayInt = Integer.parseInt(day);
		int monthInt = Integer.parseInt(month)-1;
		int yearInt = Integer.parseInt(year);
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_MONTH, dayInt);
		calendar.set(Calendar.MONTH, monthInt);
		calendar.set(Calendar.YEAR, yearInt);
		
		String dayOfWeek = "";
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {dayOfWeek = "Sunday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 2) {dayOfWeek = "Monday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 3) {dayOfWeek = "Tuesday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 4) {dayOfWeek = "Wednesday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 5) {dayOfWeek = "Thursday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 6) {dayOfWeek = "Friday";}
        else if(calendar.get(Calendar.DAY_OF_WEEK) == 7) {dayOfWeek = "Saturday";}
		
		String suffix = "";
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){ suffix = "st"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 2){ suffix = "nd"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 3){ suffix = "rd"; }
        else { suffix = "th"; }
		
        String monthString = "";
        
        if(calendar.get(Calendar.MONTH) == 0) {monthString = "January";}
        else if(calendar.get(Calendar.MONTH) == 1) {monthString = "February";}
        else if(calendar.get(Calendar.MONTH) == 2) {monthString = "March";}
        else if(calendar.get(Calendar.MONTH) == 3) {monthString = "April";}
        else if(calendar.get(Calendar.MONTH) == 4) {monthString = "May";}
        else if(calendar.get(Calendar.MONTH) == 5) {monthString = "June";}
        else if(calendar.get(Calendar.MONTH) == 6) {monthString = "July";}
        else if(calendar.get(Calendar.MONTH) == 7) {monthString = "August";}
        else if(calendar.get(Calendar.MONTH) == 8) {monthString = "September";}
        else if(calendar.get(Calendar.MONTH) == 9) {monthString = "October";}
        else if(calendar.get(Calendar.MONTH) == 10) {monthString = "November";}
        else if(calendar.get(Calendar.MONTH) == 11) {monthString = "December";}
		
		this.date = dayOfWeek + " " + calendar.get(Calendar.DAY_OF_MONTH) + suffix + " " + monthString;
        
	}
	
	public void setTime(String time) {
		if(time.equals("")){return;}
		this.time = time;
	}
	
	public void setAmPmTime(String time) {
		
		if(time.equals("")) {return;}
		
		String hour = "";
		int hourInt;
		String amPm = "";
		
		String hourRegex = ("(\\d?\\d)");
		Pattern hourPattern = Pattern.compile(hourRegex);
		
		Matcher hourMatcher = hourPattern.matcher(time);
        String hourInput = "";
        while (hourMatcher.find()) {
        	hourInput = hourMatcher.group();
        }

        hour = hourInput;
        hourInt = Integer.parseInt(hour);
        
        String amPmRegex = ("((A|a)m|(P|p)m)");
		Pattern amPmPattern = Pattern.compile(amPmRegex);
		
		Matcher amPmMatcher = amPmPattern.matcher(time);
        String amPmInput = "";
        while (amPmMatcher.find()) {
        	amPmInput = amPmMatcher.group();
        }

        amPm = amPmInput;
        
        if(amPm.equalsIgnoreCase("pm")) {
        	hourInt += 12;
        }
        
        hour = Integer.toString(hourInt);
        
        if(hour.length()==1) {
        	hour = "0" + hour;
        }
        
        this.time = hour + ":00";
        
	}
	
	public void setOnDay(String string) {
		
		if(string.equals("")){return;}
		
		String day = "";
		String month = "";
		int dayInt = 0;				//required day
		
		String dayRegex = ("(((M|m)on|(T|t)ues|(W|w)ednes|(T|t)hurs|(F|f)ri|(S|s)atur|(S|s)un)day)");
		Pattern dayPattern = Pattern.compile(dayRegex);
		
		Matcher dayMatcher = dayPattern.matcher(string);
        String dayInput = "";
        while (dayMatcher.find()) {
        	dayInput = dayMatcher.group();
        }
        
        day = dayInput;
        
        if(day.equalsIgnoreCase("monday")) {dayInt=2;}
        else if(day.equalsIgnoreCase("tuesday")) {dayInt=3;}
        else if(day.equalsIgnoreCase("wednesday")) {dayInt=4;}
        else if(day.equalsIgnoreCase("thursday")) {dayInt=5;}
        else if(day.equalsIgnoreCase("friday")) {dayInt=6;}
        else if(day.equalsIgnoreCase("saturday")) {dayInt=7;}
        else if(day.equalsIgnoreCase("sunday")) {dayInt=1;}
        
        
        
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        
        while(currentDay != dayInt){
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        }							
        
        if(currentDay == 1) {day = "Sunday";}
        else if(currentDay == 2) {day = "Monday";}
        else if(currentDay == 3) {day = "Tuesday";}
        else if(currentDay == 4) {day = "Wednesday";}
        else if(currentDay == 5) {day = "Thursday";}
        else if(currentDay == 6) {day = "Friday";}
        else if(currentDay == 7) {day = "Saturday";}
        
        if(calendar.get(Calendar.MONTH) == 0) {month = "January";}
        else if(calendar.get(Calendar.MONTH) == 1) {month = "February";}
        else if(calendar.get(Calendar.MONTH) == 2) {month = "March";}
        else if(calendar.get(Calendar.MONTH) == 3) {month = "April";}
        else if(calendar.get(Calendar.MONTH) == 4) {month = "May";}
        else if(calendar.get(Calendar.MONTH) == 5) {month = "June";}
        else if(calendar.get(Calendar.MONTH) == 6) {month = "July";}
        else if(calendar.get(Calendar.MONTH) == 7) {month = "August";}
        else if(calendar.get(Calendar.MONTH) == 8) {month = "September";}
        else if(calendar.get(Calendar.MONTH) == 9) {month = "October";}
        else if(calendar.get(Calendar.MONTH) == 10) {month = "November";}
        else if(calendar.get(Calendar.MONTH) == 11) {month = "December";}
        
        String suffix = "";
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){ suffix = "st"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 2){ suffix = "nd"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 3){ suffix = "rd"; }
        else { suffix = "th"; }
        
        dayInput = dayInput.substring(0, 1).toUpperCase() + dayInput.substring(1);
        
        date = dayInput + " " + calendar.get(Calendar.DAY_OF_MONTH) + suffix + " " + month;
        
		
	}
	
	public void setNextDay(String string) {
		
		if(string.equals("")){return;}
		
		String day = "";
		String month = "";
		int dayInt = 0;				//required day
		
		String dayRegex = ("(((M|m)on|(T|t)ues|(W|w)ednes|(T|t)hurs|(F|f)ri|(S|s)atur|(S|s)un)day)");
		Pattern dayPattern = Pattern.compile(dayRegex);
		
		Matcher dayMatcher = dayPattern.matcher(string);
        String dayInput = "";
        while (dayMatcher.find()) {
        	dayInput = dayMatcher.group();
        }
        
        day = dayInput;
        
        if(day.equalsIgnoreCase("monday")) {dayInt=2;}
        else if(day.equalsIgnoreCase("tuesday")) {dayInt=3;}
        else if(day.equalsIgnoreCase("wednesday")) {dayInt=4;}
        else if(day.equalsIgnoreCase("thursday")) {dayInt=5;}
        else if(day.equalsIgnoreCase("friday")) {dayInt=6;}
        else if(day.equalsIgnoreCase("saturday")) {dayInt=7;}
        else if(day.equalsIgnoreCase("sunday")) {dayInt=1;}
        
        
        
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        
        while(currentDay != dayInt){
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
        	currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        }							
        
        if(currentDay == 1) {day = "Sunday";}
        else if(currentDay == 2) {day = "Monday";}
        else if(currentDay == 3) {day = "Tuesday";}
        else if(currentDay == 4) {day = "Wednesday";}
        else if(currentDay == 5) {day = "Thursday";}
        else if(currentDay == 6) {day = "Friday";}
        else if(currentDay == 7) {day = "Saturday";}
        
        if(calendar.get(Calendar.MONTH) == 0) {month = "January";}
        else if(calendar.get(Calendar.MONTH) == 1) {month = "February";}
        else if(calendar.get(Calendar.MONTH) == 2) {month = "March";}
        else if(calendar.get(Calendar.MONTH) == 3) {month = "April";}
        else if(calendar.get(Calendar.MONTH) == 4) {month = "May";}
        else if(calendar.get(Calendar.MONTH) == 5) {month = "June";}
        else if(calendar.get(Calendar.MONTH) == 6) {month = "July";}
        else if(calendar.get(Calendar.MONTH) == 7) {month = "August";}
        else if(calendar.get(Calendar.MONTH) == 8) {month = "September";}
        else if(calendar.get(Calendar.MONTH) == 9) {month = "October";}
        else if(calendar.get(Calendar.MONTH) == 10) {month = "November";}
        else if(calendar.get(Calendar.MONTH) == 11) {month = "December";}
        
        String suffix = "";
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){ suffix = "st"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 2){ suffix = "nd"; }
        else if(calendar.get(Calendar.DAY_OF_MONTH) == 3){ suffix = "rd"; }
        else { suffix = "th"; }
        
        int dayPlus7 = calendar.get(Calendar.DAY_OF_MONTH) + 7;
        dayInput = dayInput.substring(0, 1).toUpperCase() + dayInput.substring(1);
        
        date = dayInput + " " + dayPlus7 + suffix + " " + month;
        
		
	}
	
	@Override
	public String toString() {
		if(description.equals("")){description = "-";}
		if(date.equals("")){date = "-";}
		if(time.equals("")){time = "-";}
		if(location.equals("")){location = "-";}
		return "Event: " + description + " | Date: " + date + " | Time: " + time + " | Location: " + location; 
	}
	
	
	
}
