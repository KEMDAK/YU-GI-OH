package eg.edu.guc.yugioh.tests;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.listeners.CentralControlUnit;

public class Main {

	public Main() {
		GUI game = new GUI();
		Board board = new Board();
		new CentralControlUnit(game, board);
	}

	public static void main(String[] args) {
		new Main();
	}

}
