package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InfoAreaActive extends JLabel {
	private JLabel playername, playerLifePoints;

	public JLabel getPlayername() {
		return playername;
	}

	public JLabel getPlayerlifepoints() {
		return playerLifePoints;
	}

	public InfoAreaActive() {
		super();
		setSize(new Dimension(320, 130));
		setIcon(new ImageIcon("Infoarea.jpg"));

		playername = new JLabel();
		add(playername);
		playername.setFont(new Font(null, 1, 40));
		playername.setForeground(Color.WHITE);
		playername.setBounds(0, 56, 320, 65);

		playerLifePoints = new JLabel("8000", SwingConstants.CENTER);
		add(playerLifePoints);
		playerLifePoints.setFont(new Font(null, 1, 50));
		playerLifePoints.setForeground(Color.WHITE);
		playerLifePoints.setBounds(0, 8, 320, 65);

	}

}
