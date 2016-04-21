package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class CardOverview extends JLabel {
	private JLabel card;

	public JLabel getCard() {
		return card;
	}

	public CardOverview() {
		super(new ImageIcon("Cards/cardback large.jpg"));
		setSize(new Dimension(300, 410));
	}

	public void setImageIcon(JButton b) {
		String path = "Cards/" + b.getName() + " large.jpg";
		setIcon(new ImageIcon(path));
		updateUI();
	}

	public void resetImageIcon() {
		setIcon(new ImageIcon("Cards/cardback large.jpg"));
		updateUI();
	}
}
