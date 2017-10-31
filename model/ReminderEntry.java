package model;

public class ReminderEntry {
	
	private String message;
	
	public void setMessage(String text) {
		String string = text;
		string = string.substring(0, 1).toUpperCase() + string.substring(1);
		message = string;
	}
	
	@Override
	public String toString() {
		return message; 
	}
}
