package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.listeners.MainMenuListener;

public class MainMenu extends JLabel {
	private JButton duel, howtoplay, options, credits;

	public MainMenu() {
		super(new ImageIcon("Background.jpg"));
		setSize(new Dimension(1280, 720));

		// Creating buttons

		// Duel button
		JPanel duelpanel = new JPanel(new GridLayout());
		duelpanel.setSize(new Dimension(300, 85));
		duelpanel.setLocation(820, 265);
		add(duelpanel);
		duel = new JButton("Duel");
		// duel.setPreferredSize(new Dimension(300, 85));
		duelpanel.add(duel);

		// How to play button
		JPanel howtoplaypanel = new JPanel(new GridLayout());
		howtoplaypanel.setSize(new Dimension(300, 85));
		howtoplaypanel.setLocation(820, 365);
		add(howtoplaypanel);
		howtoplay = new JButton("How To Play");
		// howtoplay.setPreferredSize(new Dimension(300,85));
		howtoplaypanel.add(howtoplay);

		// Options button
		JPanel optionspanel = new JPanel(new GridLayout());
		optionspanel.setSize(new Dimension(300, 85));
		optionspanel.setLocation(820, 465);
		add(optionspanel);
		options = new JButton("Options");
		// options.setPreferredSize(new Dimension(300,85));
		optionspanel.add(options);

		// Credits button
		JPanel creditpanel = new JPanel(new GridLayout());
		creditpanel.setSize(new Dimension(300, 85));
		creditpanel.setLocation(820, 565);
		add(creditpanel);
		credits = new JButton("Credits");
		// credits.setPreferredSize(new Dimension(300,85));
		creditpanel.add(credits);

	}

	public void assignListeners(MainMenuListener c) {
		duel.addActionListener(c);
		howtoplay.addActionListener(c);
		options.addActionListener(c);
		credits.addActionListener(c);
	}

	public JButton getDuel() {
		return duel;
	}

	public JButton getHowtoplay() {
		return howtoplay;
	}

	public JButton getOptions() {
		return options;
	}

	public JButton getCredits() {
		return credits;
	}

}
