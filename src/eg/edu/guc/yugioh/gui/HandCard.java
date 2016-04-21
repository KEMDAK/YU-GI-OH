package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.cards.Card;

public class HandCard extends JButton {

	public HandCard(Card c) {
		super();
		setName(c.getName());
		setPreferredSize(new Dimension(80, 113));
		String path = "Cards/" + c.getName() + ".jpg";
		setIcon(new ImageIcon(path));
	}

	public void hideCard(boolean flipped) {
		String path = "Cards/Hidden spell field";
		if (flipped)
			path += " flipped";
		setIcon(new ImageIcon(path + ".jpg"));
		updateUI();
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		return l;
	}

}
