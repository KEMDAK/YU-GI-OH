package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GUI extends JFrame {
	private MainMenu mainMenu;
	private EnterName enterName;

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setEnterName(EnterName enterName) {
		remove(this.enterName);
		this.enterName = enterName;
		add(this.enterName);
	}

	public void setGame(Game game) {
		remove(this.game);
		this.game = game;
		add(this.game);
	}

	private Game game;

	public EnterName getEnterName() {
		return enterName;
	}

	public Game getGame() {
		return game;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public EnterName getEntername() {
		return enterName;
	}

	public GUI() {
		super();
		setSize(new Dimension(1280, 720));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("YU-GI-OH!! v1.0");
		setIconImage((new ImageIcon("Icon.png")).getImage());

		mainMenu = new MainMenu();
		add(mainMenu);
		mainMenu.setVisible(true);

		enterName = new EnterName();
		add(enterName);
		enterName.setVisible(false);

		game = new Game();
		add(game);
		game.setVisible(false);

		setVisible(true);
	}

}
