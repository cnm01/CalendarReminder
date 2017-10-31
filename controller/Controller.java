package controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import model.CalendarEntry;
import model.Model;
import model.ReminderEntry;
import view.MainFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {

	private MainFrame view;
	private Model model;
	private File reminderFile;
	private File calendarFile;
	
	public Controller(MainFrame view, Model model) {
		
		this.view = view;
		this.model = model;
		view.addListenerToCalenderTextField(new CalendarTextListener());
		view.addListenerToReminderTextField(new ReminderTextListener());
		reminderFile = new File("src/resources/reminderEntries.txt");
		calendarFile = new File("src/resources/calendarEntries.txt");
		view.loadCalendar(new CalendarSelectionListener());
		view.loadReminders(new ReminderSelectionListener());
		view.pack();
		
	}
	
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public class ReminderTextListener extends AbstractAction {
		
		private String reminderRegex = ("((R|r)emind\\s*me\\s*to)");
		private Pattern reminderPattern = Pattern.compile(reminderRegex);
		
		public void removeRemindMeTo() {
	        Matcher reminderMatcher = reminderPattern.matcher(view.getTextReminder());
	        String reminderInput = "";
	        while (reminderMatcher.find()) {
	        	reminderInput = reminderMatcher.group();
	        }
	        
	        view.setReminderText(view.getTextReminder().replace(reminderInput, ""));

		}
		
		public String getText() {
			
			String message = view.getTextReminder();
			message = message.trim();
			return message;
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(view.getTextReminder().equals("")) {return;}
			ReminderEntry entry = new ReminderEntry();
			removeRemindMeTo();
			entry.setMessage(getText());
			view.appendReminder(entry.toString(), new ReminderSelectionListener());
			view.setReminderText("");

//			try {
//				FileWriter fileWriter = new FileWriter(reminderFile, true);
//				fileWriter.append(entry.toString()+"\n");
//				fileWriter.flush();
//				fileWriter.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
			
			System.out.println(entry);
			
		}
		
		
	}
	
	public class CalendarTextListener extends AbstractAction {

		//day of week
		private String dayRegex = ("(((M|m)on|(T|t)ues|(W|w)ednes|(T|t)hurs|(F|f)ri|(S|s)atur|(S|s)un)day)");
		private Pattern dayPattern = Pattern.compile(dayRegex);
		
		//day of month
		private String dateRegex = ("(\\d?\\d(st|nd|rd|th))");
		private Pattern datePattern = Pattern.compile(dateRegex);
		
		//month
		private String monthRegex = ("((J|j)anuary|(F|f)ebruary|(M|m)arch|(A|a)pril|(M|m)ay|(J|j)une|(J|j)uly|(A|a)ugust|(S|s)eptember|(O|o)ctober|(N|n)ovember|(D|d)ecember)");
		private Pattern monthPattern = Pattern.compile(monthRegex);
		
		//(16/4/2017)
		private String formattedDateRegex = ("(((\\d?\\d)(-|/|.)){2}(\\d\\d(\\d\\d)?))");
		private Pattern formattedDatePattern = Pattern.compile(formattedDateRegex);
		
		//on/this wednesday
		private String onDayRegex = ("(((O|o)n|(T|t)his)\\s+((M|m)on|(T|t)ues|(W|w)ednes|(T|t)hurs|(F|f)ri|(S|s)atur|(S|s)un)day)");
		private Pattern onDayPattern = Pattern.compile(onDayRegex);
		
		//13:00
		private String formattedTimeRegex = ("((A|a)t\\s*[0-2]\\d:[0-5]\\d)");	
		private Pattern formattedTimePattern = Pattern.compile(formattedTimeRegex);
		
		//8pm
		private String ampmTimeRegex = ("((A|a)t\\s*(\\d){1,2}\\s*((A|a)m|(P|p)m))");
		private Pattern ampmTimePattern = Pattern.compile(ampmTimeRegex);
		
		//Evening
		private String eveningRegex = ("(((I|i)n\\s*the)?\\s*(E|e)vening)");
		private Pattern eveningPattern = Pattern.compile(eveningRegex);
		private Boolean evening;
		
		//Morning
		private String morningRegex = ("(((I|i)n\\s*the)?\\s*(M|m)orning)");
		private Pattern morningPattern = Pattern.compile(morningRegex);
		private Boolean morning;
		
		//Next wednesday
		private String nextDayRegex = ("((N|n)ext\\s+((M|m)on|(T|t)ues|(W|w)ednes|(T|t)hurs|(F|f)ri|(S|s)atur|(S|s)un)day)");
		private Pattern nextDayPattern = Pattern.compile(nextDayRegex);
		
		//Location
		private String locationRegex = ("((A|a)t\\s+(\\D)*)");
		private Pattern locationPattern = Pattern.compile(locationRegex);
		
		//Tomorrow
		private String tomorrowRegex = ("((T|t)omorrow)");
		private Pattern tomorrowPattern = Pattern.compile(tomorrowRegex);
		
		public String getDescription() {
			
			String text = view.getTextCalendar();
			
			text = text.replace("on", "");
			text = text.substring(0, 1).toUpperCase() + text.substring(1);
			text = text.trim();
			view.setCalendarText("");
			return text;
			
		}
		
		public Boolean getTomorrow() {
			
			Boolean value = false;
	        Matcher tomorrowMatcher = tomorrowPattern.matcher(view.getTextCalendar());
	        String tomorrowInput = "";
	        while (tomorrowMatcher.find()) {
	        	tomorrowInput = tomorrowMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(tomorrowInput, ""));
	        tomorrowInput = tomorrowInput.replace("on", "");
	        
	        if(!tomorrowInput.equals("")){value = true;}
	        
	        return value;
		}
		
		public String getLocation() {
	        Matcher locationMatcher = locationPattern.matcher(view.getTextCalendar());
	        String locationInput = "";
	        while (locationMatcher.find()) {
	        	locationInput = locationMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(locationInput, ""));
	        locationInput = locationInput.replace("on", "");
	        
	        return locationInput;
		}
		
		public String getNextDay() {
	        Matcher nextDayMatcher = nextDayPattern.matcher(view.getTextCalendar());
	        String nextDayInput = "";
	        while (nextDayMatcher.find()) {
	        	nextDayInput = nextDayMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(nextDayInput, ""));
	        
	        return nextDayInput;
		}
		
		public Boolean getMorning() {
			morning = false;
	        Matcher morningMatcher = morningPattern.matcher(view.getTextCalendar());
	        String morningInput = "";
	        while (morningMatcher.find()) {
	        	morningInput = morningMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(morningInput, ""));
	        if(!morningInput.equals("")) {
	        	morning = true;
	        }
	        return morning;
		}
		
		public Boolean getEvening() {
			evening = false;
	        Matcher eveningMatcher = eveningPattern.matcher(view.getTextCalendar());
	        String eveningInput = "";
	        while (eveningMatcher.find()) {
	        	eveningInput = eveningMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(eveningInput, ""));
	        if(!eveningInput.equals("")) {
	        	evening = true;
	        }
	        return evening;
		}
		
		public String getOnDay() {
	        Matcher onDayMatcher = onDayPattern.matcher(view.getTextCalendar());
	        String onDayInput = "";
	        while (onDayMatcher.find()) {
	        	onDayInput = onDayMatcher.group();
	        }
	        view.setCalendarText(view.getTextCalendar().replace(onDayInput, ""));
	        
	        return onDayInput;
		}
		
		public String getAmPmTime() {
	        Matcher ampmTimeMatcher = ampmTimePattern.matcher(view.getTextCalendar());
	        String ampmTimeInput = "";
	        while (ampmTimeMatcher.find()) {
	        	ampmTimeInput = ampmTimeMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(ampmTimeInput, ""));
	        
	        return ampmTimeInput;
		}
		
		public String getFormattedTime() {
	        Matcher formattedTimeMatcher = formattedTimePattern.matcher(view.getTextCalendar());
	        String formattedTimeInput = "";
	        while (formattedTimeMatcher.find()) {
	        	formattedTimeInput = formattedTimeMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(formattedTimeInput, ""));
	        
	        return formattedTimeInput;
		}
		
		public String getDay() {
	        Matcher dayMatcher = dayPattern.matcher(view.getTextCalendar());
	        String dayInput = "";
	        while (dayMatcher.find()) {
	        	dayInput = dayMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(dayInput, ""));
	        
	        return dayInput;
		}
		
		public String getDate() {
			Matcher dateMatcher = datePattern.matcher(view.getTextCalendar());
	        String dateInput = "";
	        while (dateMatcher.find()) {
	        	dateInput = dateMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(dateInput, ""));
	        
	        return dateInput;
		}
		
		public String getMonth() {
			Matcher monthMatcher = monthPattern.matcher(view.getTextCalendar());
	        String monthInput = "";
	        while (monthMatcher.find()) {
	        	monthInput = monthMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(monthInput, ""));
	        
	        return monthInput;
		}
		
		public String getFormattedDate() {
			Matcher formattedDateMatcher = formattedDatePattern.matcher(view.getTextCalendar());
	        String formattedDateInput = "";
	        while (formattedDateMatcher.find()) {
	        	formattedDateInput = formattedDateMatcher.group();
	        }
	        
	        view.setCalendarText(view.getTextCalendar().replace(formattedDateInput, ""));
	        
	        return formattedDateInput;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(view.getTextCalendar().equals("")) {return;}
			CalendarEntry entry = new CalendarEntry();
			if(getEvening()){entry.setTime("20:00");}
			if(getMorning()){entry.setTime("09:00");}
			entry.setNextDay(getNextDay());
			entry.setOnDay(getOnDay());
			entry.setDay(getDay());
			entry.setDate(getDate());
			entry.setMonth(getMonth());
			entry.setFormattedDate(getFormattedDate());
			entry.setTime(getFormattedTime());
			entry.setAmPmTime(getAmPmTime());
			
			if(getTomorrow()){entry.setTomorrow();}
			entry.setLocation(getLocation());
			entry.setDescription(getDescription());
			view.appendCalender(entry.toString(), new CalendarSelectionListener());
			
			System.out.println(entry);
			
		}
		
	}
	

	public class CalendarSelectionListener implements ListSelectionListener {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			if (e.getValueIsAdjusting() == false) {
	
		        if (view.getCalendarList().getSelectedIndex() != -1) {
			        	
		        	view.removeFromCalendar(view.getCalendarList().getSelectedIndex());
		        	
//		        	BufferedReader reader = new BufferedReader();
		        	
		        	
//		        	String calendar = calendarFile.toString();
//		        	String calendar = "";
//		        	
//					try {
//						calendar = readFile("src/resources/reminderEntries.txt", StandardCharsets.UTF_8);
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//					
//		        	calendar.replace(view.removeFromCalendar(view.getCalendarList().getSelectedIndex()), "");
//		        	
//		        	System.out.println(calendar);
//		        	
//		        	try {
//						FileWriter fileWriter = new FileWriter(calendarFile);
//						fileWriter.write(calendar);
//						fileWriter.flush();
//						fileWriter.close();
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}

		        }
		    }
		}
		
	}
	
	public class ReminderSelectionListener implements ListSelectionListener {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			if (e.getValueIsAdjusting() == false) {
	
		        if (view.getReminderList().getSelectedIndex() != -1) {
			        	
		        	view.removeFromReminder(view.getReminderList().getSelectedIndex());

		        }
		    }
		}
		
	}
	
}
