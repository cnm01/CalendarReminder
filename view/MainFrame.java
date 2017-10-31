package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import controller.Controller.CalendarSelectionListener;
import controller.Controller.ReminderSelectionListener;

public class MainFrame extends JFrame {
	
	private JTabbedPane tabbedPane;
	private JList<String> calendarList;					//panel that displays the text
	private DefaultListModel<String> calendarModel;
	private JList<String> reminderList;					//panel that displays the text
	private DefaultListModel<String> reminderModel;
	private JScrollPane calendarScrollPane;			//wraps the text panel to allow scroll
	private JScrollPane reminderScrollPane;			//wraps the text panel to allow scroll
	private JTextField calendarTextField;			//allows text input
	private JTextField reminderTextField;			//allows text input
	private JPanel calendarPane;					//holds the calendar panel and calendar text field
	private JPanel reminderPane;					//holds the reminder panel and reminder text field
	private ImageIcon calendarIcon;					//icon image for reminder tab
	private ImageIcon reminderIcon;					//icon image for the calendar tab
	private File reminderFile;
	private File calendarFile;
	private String calendarEntries;
	private String reminderEntries;
	
	public MainFrame() {
		
		initialise();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setMinimumSize(new Dimension(500, 300));
		super.pack();
		super.setVisible(true);
		reminderFile = new File("src/resources/reminderEntries.txt");
		calendarFile = new File("src/resources/calendarEntries.txt");
		
		
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	try {
					FileWriter fileWriter = new FileWriter(calendarFile);
					fileWriter.write(calendarModel.toString());
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		    	
		    	try {
					FileWriter fileWriter = new FileWriter(reminderFile);
					fileWriter.write(reminderModel.toString());
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		    	
		    	
		    }
		});
		
	}
	
	public void loadCalendar(CalendarSelectionListener listener) {
		
		try {
			String calendarEntries = new Scanner(calendarFile).useDelimiter("\\Z").next();
			String[] array = calendarEntries.split(",\\s");
			ArrayList<String> arraylist = new ArrayList<String>(Arrays.asList(array));
			String first = arraylist.get(0).substring(2);		///
			String last = arraylist.get(arraylist.size()-1).substring(0, arraylist.get(arraylist.size()-1).length()-1);
			if(last.equals("[")){last = "";}
			arraylist.set(0, first);
			arraylist.set(arraylist.size()-1, last);
			
			for(int i = 0; i < array.length; i++) {
				appendCalender(arraylist.get(i), listener);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadReminders(ReminderSelectionListener listener) {
		
		try {
			String reminderEntries = new Scanner(reminderFile).useDelimiter("\\Z").next();
			String[] array = reminderEntries.split(",\\s");
			ArrayList<String> arraylist = new ArrayList<String>(Arrays.asList(array));
			
			String first = arraylist.get(0).substring(1);
			String last = arraylist.get(arraylist.size()-1).substring(0, arraylist.get(arraylist.size()-1).length()-1);
			if(last.equals("[")){last = "";}
			arraylist.set(0, first);
			arraylist.set(arraylist.size()-1, last);
			
			for(int i = 0; i < array.length; i++) {
				appendReminder(arraylist.get(i), listener);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void initialise() {
		
		tabbedPane = new JTabbedPane();
		
		calendarModel = new DefaultListModel<String>();
		reminderModel = new DefaultListModel<String>();
		calendarList = new JList<String>(calendarModel);
		calendarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reminderList = new JList<String>(reminderModel);
		reminderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calendarTextField = new JTextField();
		reminderTextField = new JTextField();
		calendarPane = new JPanel();
		reminderPane = new JPanel();
		calendarIcon = new ImageIcon("src/resources/calendaricon.png");
		reminderIcon = new ImageIcon("src/resources/remindericon.png");
		reminderScrollPane = new JScrollPane(reminderList);
		reminderScrollPane.createHorizontalScrollBar();
		reminderScrollPane.createVerticalScrollBar();
		calendarScrollPane = new JScrollPane(calendarList);
		calendarScrollPane.createHorizontalScrollBar();
		calendarScrollPane.createVerticalScrollBar();
		
		calendarPane.setLayout(new BorderLayout());
		calendarPane.add(calendarScrollPane, BorderLayout.CENTER);
		calendarPane.add(calendarTextField, BorderLayout.SOUTH);
		
		reminderPane.setLayout(new BorderLayout());
		reminderPane.add(reminderScrollPane, BorderLayout.CENTER);
		reminderPane.add(reminderTextField, BorderLayout.SOUTH);
		
		tabbedPane.addTab("Calendar", calendarIcon, calendarPane, "Lists calendar events");
		tabbedPane.addTab("Reminders", reminderIcon, reminderPane, "Lists reminders");
		
		super.setLayout(new BorderLayout());
		super.add(tabbedPane, BorderLayout.CENTER);
		
		
	}
	
	public void appendCalender(String string, CalendarSelectionListener calendarSelectionListener) {
		
		calendarModel.addElement(string);
		calendarList.addListSelectionListener(calendarSelectionListener);
		
	}
	
	public void appendReminder(String string, ReminderSelectionListener listener) {
		
		reminderModel.addElement(string);
		reminderList.addListSelectionListener(listener);
		
	}
	
	public String getTextCalendar() {
		return calendarTextField.getText();
	}
	
	public String getTextReminder() {
		return reminderTextField.getText();
	}
	
	public JList getCalendarList() {
		return calendarList;
	}
	
	public DefaultListModel getCalendarModel() {
		return calendarModel;
	}
	
	public DefaultListModel getReminderModel() {
		return reminderModel;
	}
	
	public JList getReminderList() {
		return reminderList;
	}
	
	public void removeFromCalendar(int i) {
		calendarModel.remove(i);
	}
	
	public void removeFromReminder(int i) {
		reminderModel.remove(i);
	}
 	
	public void setCalendarText(String text) {
		calendarTextField.setText(text);
	}
	
	public void setReminderText(String text) {
		reminderTextField.setText(text);
	}
	
	public void addListenerToCalenderTextField(AbstractAction listener) {
		calendarTextField.addActionListener(listener);
	}
	
	public void addListenerToReminderTextField(AbstractAction listener) {
		reminderTextField.addActionListener(listener);
	}

}
