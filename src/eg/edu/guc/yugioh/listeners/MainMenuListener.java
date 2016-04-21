package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eg.edu.guc.yugioh.gui.GUI;

public class MainMenuListener implements ActionListener {
	GUI gui;

	public MainMenuListener(GUI gui) {
		this.gui = gui;
		gui.getMainMenu().assignListeners(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gui.getMainMenu().getDuel()) {
			gui.getMainMenu().setVisible(false);
			gui.getEntername().setVisible(true);
			gui.getEntername().getPlayername().grabFocus();
		}
		// Other buttons
	}

}
