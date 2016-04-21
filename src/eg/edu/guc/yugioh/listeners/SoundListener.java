package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eg.edu.guc.yugioh.gui.Audio;
import eg.edu.guc.yugioh.gui.GUI;

public class SoundListener implements ActionListener {
	private GUI gui;
	private Audio audio;
	private boolean muted;

	public SoundListener(GUI gui, Audio audio) {
		this.gui = gui;
		this.audio = audio;
		muted = false;

		gui.getGame().getMute().addActionListener(this);
		gui.getGame().getHigh().addActionListener(this);
		gui.getGame().getLow().addActionListener(this);
	}

	public GUI getGui() {
		return gui;
	}

	public Audio getAudio() {
		return audio;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == gui.getGame().getMute()) {
				if (muted) {
					gui.getGame().getMute().unmute();
					muted = false;
					audio.unmuteVolume();
				} else {
					gui.getGame().getMute().mute();
					muted = true;
					audio.muteVolume();
				}
			}
			if (e.getSource() == gui.getGame().getHigh()) {
				audio.raiseVolume();
			}

			if (e.getSource() == gui.getGame().getLow()) {
				audio.lowerVolume();
			}
		} catch (IllegalArgumentException e1) {

		}

	}

}
