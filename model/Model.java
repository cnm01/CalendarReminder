package model;

import java.util.ArrayList;

public class Model {
	
	ArrayList<CalendarEntry> calendarEntries;
	ArrayList<ReminderEntry> reminderEntries;
	
	public Model() {
		
		calendarEntries = new ArrayList<CalendarEntry>();
		reminderEntries = new ArrayList<ReminderEntry>();
		
	}

	
	
}
