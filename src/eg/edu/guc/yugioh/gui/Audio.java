package eg.edu.guc.yugioh.gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	private static Clip sound;
	private FloatControl f;
	private float value;

	public Audio() {
		AudioInputStream stream;
		try {
			stream = AudioSystem.getAudioInputStream(new File(ChooseSong()));
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			sound = (Clip) AudioSystem.getLine(info);
			sound.open(stream);
			sound.loop(Clip.LOOP_CONTINUOUSLY);
			f = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (UnsupportedAudioFileException e) {

		} catch (IOException e) {

		} catch (LineUnavailableException e) {

		}
	}

	public String ChooseSong() {
		int r = (int) (Math.random() * 2);
		String ret;
		if (r == 0)
			ret = "Duel Theme.wav";
		else
			ret = "Match Duel.wav";

		return ret;
	}

	public void raiseVolume() {
		f.setValue(f.getValue() + 3);
	}

	public void lowerVolume() {
		f.setValue(f.getValue() - 3);
	}

	public void muteVolume() {
		value = f.getValue();
		f.setValue(f.getValue() + (f.getMinimum() - f.getValue()));
	}

	public void unmuteVolume() {
		f.setValue(value);
	}

	public static void stop() {
		sound.stop();
	}

	public static void endMusic() {
		AudioInputStream stream;
		try {
			stream = AudioSystem.getAudioInputStream(new File("End Theme.wav"));
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			sound = (Clip) AudioSystem.getLine(info);
			sound.open(stream);
			sound.start();
		} catch (UnsupportedAudioFileException e) {

		} catch (IOException e) {

		} catch (LineUnavailableException e) {

		}

	}

	public Clip getSound() {
		return sound;
	}

	public void setSound(Clip sound) {
		Audio.sound = sound;
	}

}
