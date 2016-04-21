package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameOver extends JLabel {
	private JButton playagain;
	private JLabel winner;

	public GameOver() {
		super();
		JLabel over = new JLabel("GAME OVER");
		over.setFont(new Font(null, 1, 70));
		over.setForeground(Color.BLUE);
		add(over);
		over.setBounds(430, 200, 430, 60);
		playagain = new JButton("Play again");
		add(playagain);
		playagain.setBounds(590, 450, 100, 100);
	}

	public void setWinner(String s) {
		winner = new JLabel(s + " Wins");
		winner.setHorizontalAlignment(SwingConstants.CENTER);
		winner.setFont(new Font(null, 1, 50));
		winner.setForeground(Color.BLUE);
		add(winner);
		winner.setBounds(0, 350, 1280, 80);
	}

	public JButton getPlayagain() {
		return playagain;
	}
}
