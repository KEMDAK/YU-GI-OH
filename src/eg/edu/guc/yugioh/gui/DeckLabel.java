package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DeckLabel extends JLabel {

	public DeckLabel() {
		super(new ImageIcon("Deck slot.jpg"));// han3'ayarha
		setSize(new Dimension(80, 113));
	}

}
