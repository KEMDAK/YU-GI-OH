package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.gui.GUI;

public class CentralControlUnit implements ActionListener {
	private GUI gui;
	private Board board;

	public CentralControlUnit(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		new MainMenuListener(gui);
		new EnterNameListener(gui, board);
	}

	public void actionPerformed(ActionEvent e) {

	}

	public GUI getGui() {
		return gui;
	}

	public Board getBoard() {
		return board;
	}

}
