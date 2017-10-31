package controller;

import model.Model;
import view.MainFrame;

public class RunProgram {

	public static void main(String[] args) {

		MainFrame view = new MainFrame();
		Model model = new Model();
		Controller controller = new Controller(view, model);
		
	}

}
