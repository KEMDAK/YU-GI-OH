package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.gui.EnterName;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.Game;

public class GameListener implements ActionListener, MouseListener, KeyListener {
	private GUI gui;
	private Board board;
	private FieldListener fieldListener;
	private HandListener handListener;

	public GUI getGui() {
		return gui;
	}

	public Board getBoard() {
		return board;
	}

	public FieldListener getFieldListener() {
		return fieldListener;
	}

	public HandListener getHandListener() {
		return handListener;
	}

	public GameListener(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		handListener = new HandListener(gui, board);
		fieldListener = new FieldListener(gui, board);
		gui.getGame().getActivePhase().assignListeners(this);
		gui.getGame().getOpponentPhase().assignListeners(this);
		gui.getGame().getGraveyardButtonActive().addMouseListener(this);
		gui.getGame().getGraveyardButtonOpponent().addMouseListener(this);
		gui.getGame().getGameover().getPlayagain().addActionListener(this);
		gui.getGame().addKeyListener(this);
		updateGame();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (board.getContinuePlaying()) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (!fieldListener.isActivationMode()) {
					if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
						if (e.getSource() == gui.getGame().getActivePhase()
								.getEndphase()) {
							if (board.getActivePlayer().getField().getPhase()
									.name().equals("MAIN2")) {
								gui.getGame().getGameBoard().setWhoIsActive(1);
								board.getActivePlayer().endPhase();
								handListener.updateHand(true);
							} else {
								board.getActivePlayer().endPhase();
								handListener.updateHand(false);
							}

						} else if (e.getSource() == gui.getGame()
								.getActivePhase().getEndTurn()) {
							board.getActivePlayer().endTurn();
							gui.getGame().getGameBoard().setWhoIsActive(1);
							handListener.updateHand(true);
						}
					} else if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
						if (e.getSource() == gui.getGame().getOpponentPhase()
								.getEndphase()) {
							if (board.getActivePlayer().getField().getPhase()
									.name().equals("MAIN2")) {
								gui.getGame().getGameBoard().setWhoIsActive(0);
								board.getActivePlayer().endPhase();
								handListener.updateHand(false);
							} else {
								board.getActivePlayer().endPhase();
								handListener.updateHand(true);
							}
						} else if (e.getSource() == gui.getGame()
								.getOpponentPhase().getEndTurn()) {
							board.getActivePlayer().endTurn();
							gui.getGame().getGameBoard().setWhoIsActive(0);
							handListener.updateHand(false);
						}
					}
					updatePhase();
				} else
					JOptionPane.showMessageDialog(gui,
							"Select a monster to activate your spell.");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if ((JButton) e.getSource() != gui.getGame().getActivePhase()
				.getEndphase()
				&& (JButton) e.getSource() != gui.getGame().getActivePhase()
						.getEndTurn())
			if ((JButton) e.getSource() != gui.getGame().getOpponentPhase()
					.getEndphase()
					&& (JButton) e.getSource() != gui.getGame()
							.getOpponentPhase().getEndTurn()) {
				gui.getGame().getCardOverview()
						.setImageIcon((JButton) e.getSource());
				if (Integer.parseInt(gui.getGame().getATKgain().getName()) != 0)
					gui.getGame().getATKgain().setVisible(true);
				if (Integer.parseInt(gui.getGame().getDEFgain().getName()) != 0)
					gui.getGame().getDEFgain().setVisible(true);
			}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		gui.getGame().getCardOverview().resetImageIcon();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		EnterName newEntername = new EnterName();
		gui.setEnterName(newEntername);
		Game newGame = new Game();
		gui.setGame(newGame);
		Board newBoard = new Board();
		new CentralControlUnit(gui, newBoard);
		gui.getGame().setVisible(false);
		gui.getEntername().setVisible(true);
	}

	public void updateGame() {
		fieldListener.updateField(false);
		handListener.updateHand(false);
		updatePhase();
		gui.getGame().updateUI();
	}

	public void updatePhase() {
		if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
			gui.getGame().getOpponentPhase().getPhaselabel()
					.setIcon(new ImageIcon("Inactive phase.png"));
			gui.getGame()
					.getActivePhase()
					.getPhaselabel()
					.setIcon(
							new ImageIcon(board.getActivePlayer().getField()
									.getPhase().name()
									+ ".png"));
		} else if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
			gui.getGame().getActivePhase().getPhaselabel()
					.setIcon(new ImageIcon("Inactive phase.png"));
			gui.getGame()
					.getOpponentPhase()
					.getPhaselabel()
					.setIcon(
							new ImageIcon(board.getActivePlayer().getField()
									.getPhase().name()
									+ ".png"));
		}
		reset();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			fieldListener.reset();
			handListener.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		handListener.reset();
		fieldListener.reset();
	}
}
