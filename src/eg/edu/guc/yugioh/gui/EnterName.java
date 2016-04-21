package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eg.edu.guc.yugioh.listeners.EnterNameListener;

public class EnterName extends JLabel {
	private JLabel player;
	private JTextField playername;
	private JButton ready;
	private int count;

	public JButton getReady() {
		return ready;
	}

	public JLabel getPlayer() {
		return player;
	}

	public JTextField getPlayername() {
		return playername;
	}

	public EnterName() {
		super(new ImageIcon("Background.jpg"));
		setSize(new Dimension(1280, 720));

		player = new JLabel();
		player.setSize(new Dimension(300, 85));
		player.setLocation(870, 250);
		player.setFont(new Font(null, 0, 50));
		add(player);
		player.setVisible(true);

		playername = new JTextField();
		playername.setSize(new Dimension(200, 25));
		playername.setLocation(870, 325);
		add(playername);
		playername.setVisible(true);

		JPanel readypanel = new JPanel(new GridLayout());
		ready = new JButton("READY!");
		add(readypanel);
		readypanel.setSize(new Dimension(100, 45));
		readypanel.setLocation(870, 360);
		readypanel.add(ready);

		count = 1;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void assignListeners(EnterNameListener c) {
		ready.addActionListener(c);
		playername.addKeyListener(c);
	}

}
