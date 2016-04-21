package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import eg.edu.guc.yugioh.cards.Card;

public class GraveyardButton extends JButton {

	public GraveyardButton() {
		super();
		setIcon(new ImageIcon("Graveyard slot.jpg"));// han3'ayarha
		setName("cardback");
		setSize(new Dimension(80, 113));
		setLayout(new GridBagLayout());
	}

	public void addCard(Card c) {
		setName(c.getName());
		String path = "Cards/" + c.getName() + ".jpg";
		setIcon(new ImageIcon(path));
	}

}
