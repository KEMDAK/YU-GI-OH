package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.gui.Audio;
import eg.edu.guc.yugioh.gui.GUI;

public class EnterNameListener implements ActionListener, KeyListener {
	private GUI gui;
	private Board board;
	private String player1;
	private String player2;
	private Audio audio;

	public EnterNameListener(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		gui.getEntername().assignListeners(this);
		gui.getEntername().getPlayer().setText("Player 1");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gui.getEntername().getReady()) {
			if (gui.getEntername().getCount() == 1) {
				if (!gui.getEntername().getPlayername().getText().equals("")) {
					gui.getEntername().getPlayer().setText("Player 2");
					player1 = gui.getEntername().getPlayername().getText();
					gui.getEntername().setCount(2);
					gui.getEntername().getPlayername().setText("");
				} else
					JOptionPane.showMessageDialog(gui, "Please enter a name");
			} else {
				if (!gui.getEntername().getPlayername().getText().equals("")) {
					player2 = gui.getEntername().getPlayername().getText();
					gui.getEntername().setCount(1);
					try {
						board.startGame(new Player(player1),
								new Player(player2));
						gui.getGame().getInfoAreaActive().getPlayername()
								.setText(board.getActivePlayer().getName());
						gui.getGame().getInfoAreaActive().getPlayername()
								.setHorizontalAlignment(SwingConstants.CENTER);
						gui.getGame().getInfoAreaOpponent().getPlayername()
								.setText(board.getOpponentPlayer().getName());
						gui.getGame().getInfoAreaOpponent().getPlayername()
								.setHorizontalAlignment(SwingConstants.CENTER);
						gui.getEntername().setVisible(false);
						new GameListener(gui, board);
						gui.getGame().setVisible(true);
						audio = new Audio();
						new SoundListener(gui, audio);
					} catch (IOException | UnexpectedFormatException e1) {
						JOptionPane.showMessageDialog(gui,
								"The File is Corrupted");
					}
				} else
					JOptionPane.showMessageDialog(gui, "Please enter a name");
			}
		}
	}

	public void keyTyped(KeyEvent k) {

	}

	@Override
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_ENTER)
			gui.getEntername().getReady().doClick();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
