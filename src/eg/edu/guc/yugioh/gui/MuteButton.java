package eg.edu.guc.yugioh.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MuteButton extends JButton {

	public MuteButton() {
		super(new ImageIcon("unmute.png"));
		setSize(30, 30);
	}

	public void mute() {
		setIcon(new ImageIcon("mute.png"));
	}

	public void unmute() {
		setIcon(new ImageIcon("unmute.png"));
	}

}
