package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.MonsterReborn;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.gui.Audio;
import eg.edu.guc.yugioh.gui.GUI;

public class FieldListener implements ActionListener, MouseListener {
	private GUI gui;
	private Board board;
	private MonsterCard attacker;
	private boolean attackMode;

	public boolean isAttackMode() {
		return attackMode;
	}

	public boolean isActivationMode() {
		return activationMode;
	}

	private boolean activationMode;
	private SpellCard activatedSpell;

	public GUI getGui() {
		return gui;
	}

	public Board getBoard() {
		return board;
	}

	public FieldListener(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		gui.getGame().getGameBoard().assignListeners(this);

	}

	public void updateField(boolean exchange) {
		gui.getGame().getGameBoard().getPlayerFieldActive().removeAllCards();
		gui.getGame().getGameBoard().getPlayerFieldOpponent().removeAllCards();

		if (exchange == false) {
			for (int i = 0; i < board.getActivePlayer().getField()
					.getMonstersArea().size(); i++) {
				MonsterCard m = board.getActivePlayer().getField()
						.getMonstersArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldActive()
						.getMonsterSlots().get(i).addMonster(m, false);
			}

			for (int i = 0; i < board.getActivePlayer().getField()
					.getSpellArea().size(); i++) {
				SpellCard s = board.getActivePlayer().getField().getSpellArea()
						.get(i);
				gui.getGame().getGameBoard().getPlayerFieldActive()
						.getSpellSlots().get(i).addSpell(s, false);
			}

			for (int i = 0; i < board.getOpponentPlayer().getField()
					.getMonstersArea().size(); i++) {
				MonsterCard m = board.getOpponentPlayer().getField()
						.getMonstersArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldOpponent()
						.getMonsterSlots().get(i).addMonster(m, true);
			}

			for (int i = 0; i < board.getOpponentPlayer().getField()
					.getSpellArea().size(); i++) {
				SpellCard s = board.getOpponentPlayer().getField()
						.getSpellArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldOpponent()
						.getSpellSlots().get(i).addSpell(s, true);
			}
			if (!board.getActivePlayer().getField().getGraveyard().isEmpty()) {
				gui.getGame()
						.getGraveyardButtonActive()
						.addCard(
								board.getActivePlayer()
										.getField()
										.getGraveyard()
										.get(board.getActivePlayer().getField()
												.getGraveyard().size() - 1));
			}
			if (!board.getOpponentPlayer().getField().getGraveyard().isEmpty()) {
				gui.getGame()
						.getGraveyardButtonOpponent()
						.addCard(
								board.getOpponentPlayer()
										.getField()
										.getGraveyard()
										.get(board.getOpponentPlayer()
												.getField().getGraveyard()
												.size() - 1));
			}
		} else {
			for (int i = 0; i < board.getActivePlayer().getField()
					.getMonstersArea().size(); i++) {
				MonsterCard m = board.getActivePlayer().getField()
						.getMonstersArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldOpponent()
						.getMonsterSlots().get(i).addMonster(m, true);
			}

			for (int i = 0; i < board.getActivePlayer().getField()
					.getSpellArea().size(); i++) {
				SpellCard s = board.getActivePlayer().getField().getSpellArea()
						.get(i);
				gui.getGame().getGameBoard().getPlayerFieldOpponent()
						.getSpellSlots().get(i).addSpell(s, true);
			}

			for (int i = 0; i < board.getOpponentPlayer().getField()
					.getMonstersArea().size(); i++) {
				MonsterCard m = board.getOpponentPlayer().getField()
						.getMonstersArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldActive()
						.getMonsterSlots().get(i).addMonster(m, false);
			}

			for (int i = 0; i < board.getOpponentPlayer().getField()
					.getSpellArea().size(); i++) {
				SpellCard s = board.getOpponentPlayer().getField()
						.getSpellArea().get(i);
				gui.getGame().getGameBoard().getPlayerFieldActive()
						.getSpellSlots().get(i).addSpell(s, false);
			}
			if (!board.getActivePlayer().getField().getGraveyard().isEmpty()) {
				gui.getGame()
						.getGraveyardButtonOpponent()
						.addCard(
								board.getActivePlayer()
										.getField()
										.getGraveyard()
										.get(board.getActivePlayer().getField()
												.getGraveyard().size() - 1));
			}
			if (!board.getOpponentPlayer().getField().getGraveyard().isEmpty()) {
				gui.getGame()
						.getGraveyardButtonActive()
						.addCard(
								board.getOpponentPlayer()
										.getField()
										.getGraveyard()
										.get(board.getOpponentPlayer()
												.getField().getGraveyard()
												.size() - 1));
			}
		}
		if (board.getContinuePlaying() == false)
			endGame();
		gui.getGame().getGameBoard().updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (board.getContinuePlaying()) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
					if (activationMode == false) {
						if (attackMode == false) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								if (isSacrificeMode()) {
									gui.getGame().getHandAreaActive()
											.getHandListener().mouseClicked(e);
									updateField(false);
									return;
								}
								MonsterCard m = (MonsterCard) board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (board.getActivePlayer().getField()
										.getPhase() == Phase.BATTLE) {
									if (board.getOpponentPlayer().getField()
											.getMonstersArea().isEmpty()) {
										try {
											board.getActivePlayer()
													.declareAttack(m);
										} catch (RuntimeException e1) {
											JOptionPane.showMessageDialog(gui,
													e1.getMessage());
										}
									} else {
										if (m.getMode() == Mode.DEFENSE) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't attack in defense mode.");
											return;
										}
										attackMode = true;
										attacker = m;
										JOptionPane
												.showMessageDialog(gui,
														"Please select the monster that you want to attack.");
									}
								} else {
									try {
										board.getActivePlayer()
												.switchMonsterMode(m);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								}
							} else if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsSpell((JButton) e.getSource())
									&& board.getActivePlayer().getField()
											.getPhase() != Phase.BATTLE) {
								SpellCard s = (SpellCard) board
										.getActivePlayer()
										.getField()
										.getSpellArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfSpell(
														(JButton) e.getSource()));
								try {
									if (s instanceof MonsterReborn) {
										if (!(board.getActivePlayer()
												.getField().isThereMonster() || board
												.getOpponentPlayer().getField()
												.isThereMonster())) {
											JOptionPane
													.showMessageDialog(gui,
															"There are no monsters in any graveyard");
											return;
										}
									}
									board.getActivePlayer().activateSpell(s,
											null);
									gui.getGame().getHandAreaActive()
											.getHandListener()
											.updateHand(false);
								} catch (NullPointerException e1) {
									if ((s instanceof ChangeOfHeart && board
											.getOpponentPlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane
												.showMessageDialog(gui,
														"The opponent doesn't have any monsters.");
										return;
									}

									if ((s instanceof MagePower && board
											.getActivePlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane.showMessageDialog(gui,
												"You don't have any monsters.");
										return;
									}

									JOptionPane.showMessageDialog(gui,
											"Please select a monster.");
									activationMode = true;
									activatedSpell = s;
								} catch (NoMonsterSpaceException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						} else {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getOpponentPlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								try {
									board.getActivePlayer().declareAttack(
											attacker, m);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
								attackMode = false;
							}
						}

					} else {
						if (activatedSpell instanceof ChangeOfHeart) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getOpponentPlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								board.getActivePlayer().activateSpell(
										activatedSpell, m);
								activationMode = false;
							}
						} else {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								board.getActivePlayer().activateSpell(
										activatedSpell, m);
								activationMode = false;
							}
						}
					}
					if (board.getActivePlayer().getLifePoints() < 0)
						gui.getGame().getInfoAreaActive().getPlayerlifepoints()
								.setText("0");
					else
						gui.getGame()
								.getInfoAreaOpponent()
								.getPlayerlifepoints()
								.setText(
										board.getOpponentPlayer()
												.getLifePoints() + "");
					if (board.getOpponentPlayer().getLifePoints() < 0)
						gui.getGame().getInfoAreaOpponent()
								.getPlayerlifepoints().setText("0");
					else
						gui.getGame()
								.getInfoAreaActive()
								.getPlayerlifepoints()
								.setText(
										board.getActivePlayer().getLifePoints()
												+ "");
					updateField(false);
				} else if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
					if (activationMode == false) {
						if (attackMode == false) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								if (isSacrificeMode()) {
									gui.getGame().getHandAreaOpponent()
											.getHandListener().mouseClicked(e);
									updateField(true);
									return;
								}
								MonsterCard m = (MonsterCard) board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (board.getActivePlayer().getField()
										.getPhase() == Phase.BATTLE) {
									if (board.getOpponentPlayer().getField()
											.getMonstersArea().isEmpty()) {
										try {
											board.getActivePlayer()
													.declareAttack(m);
										} catch (RuntimeException e1) {
											JOptionPane.showMessageDialog(gui,
													e1.getMessage());
										}
									} else {
										if (m.getMode() == Mode.DEFENSE) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't attack in defense mode.");
											return;
										}
										attackMode = true;
										attacker = m;
										JOptionPane
												.showMessageDialog(gui,
														"Please select the monster that you want to attack.");
									}
								} else {
									try {
										board.getActivePlayer()
												.switchMonsterMode(m);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								}
							} else if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsSpell((JButton) e.getSource())
									&& board.getActivePlayer().getField()
											.getPhase() != Phase.BATTLE) {
								SpellCard s = (SpellCard) board
										.getActivePlayer()
										.getField()
										.getSpellArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfSpell(
														(JButton) e.getSource()));
								try {
									if (s instanceof MonsterReborn) {
										if (!(board.getActivePlayer()
												.getField().isThereMonster() || board
												.getOpponentPlayer().getField()
												.isThereMonster())) {
											JOptionPane
													.showMessageDialog(gui,
															"There are no monsters in any graveyard");
											return;
										}
									}
									board.getActivePlayer().activateSpell(s,
											null);
									gui.getGame().getHandAreaOpponent()
											.getHandListener().updateHand(true);
								} catch (NullPointerException e1) {
									if ((s instanceof ChangeOfHeart && board
											.getOpponentPlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane
												.showMessageDialog(gui,
														"The opponent doesn't have any monsters.");
										return;
									}

									if ((s instanceof MagePower && board
											.getActivePlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane.showMessageDialog(gui,
												"You don't have any monsters.");
										return;
									}

									JOptionPane.showMessageDialog(gui,
											"Please select a monster.");
									activationMode = true;
									activatedSpell = s;
								} catch (NoMonsterSpaceException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						} else {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getOpponentPlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								try {
									board.getActivePlayer().declareAttack(
											attacker, m);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}

								attackMode = false;
							}
						}
					} else {
						if (activatedSpell instanceof MagePower) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								board.getActivePlayer().activateSpell(
										activatedSpell, m);
								activationMode = false;
							}
						} else {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = (MonsterCard) board
										.getOpponentPlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								board.getActivePlayer().activateSpell(
										activatedSpell, m);
								activationMode = false;
							}
						}
					}
					if (board.getOpponentPlayer().getLifePoints() < 0)
						gui.getGame().getInfoAreaActive().getPlayerlifepoints()
								.setText("0");
					else
						gui.getGame()
								.getInfoAreaActive()
								.getPlayerlifepoints()
								.setText(
										board.getOpponentPlayer()
												.getLifePoints() + "");
					if (board.getActivePlayer().getLifePoints() < 0)
						gui.getGame().getInfoAreaOpponent()
								.getPlayerlifepoints().setText("0");
					else
						gui.getGame()
								.getInfoAreaOpponent()
								.getPlayerlifepoints()
								.setText(
										board.getActivePlayer().getLifePoints()
												+ "");
					updateField(true);
				}

			}
			gui.getGame().grabFocus();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (gui.getGame().getGameBoard().getPlayerFieldActive()
				.containsMonster((JButton) e.getSource()))
			if (((JButton) e.getSource()).getSize().getWidth() == 80.0) {
				gui.getGame().getCardOverview()
						.setImageIcon((JButton) e.getSource());
				gui.getGame()
						.getATKgain()
						.setText(
								"<html>ATK<br>+"
										+ gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.getMonsterSlots()
												.get(gui.getGame()
														.getGameBoard()
														.getPlayerFieldActive()
														.indexOfMonster(
																(JButton) e
																		.getSource()))
												.getCard().getATKgain()
										+ "</html>");
				gui.getGame()
						.getDEFgain()
						.setText(
								"<html>DEF<br>+"
										+ gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.getMonsterSlots()
												.get(gui.getGame()
														.getGameBoard()
														.getPlayerFieldActive()
														.indexOfMonster(
																(JButton) e
																		.getSource()))
												.getCard().getDEFgain()
										+ "</html>");
				if (gui.getGame()
						.getGameBoard()
						.getPlayerFieldActive()
						.getMonsterSlots()
						.get(gui.getGame().getGameBoard()
								.getPlayerFieldActive()
								.indexOfMonster((JButton) e.getSource()))
						.getCard().getATKgain() != 0)
					gui.getGame().getATKgain().setVisible(true);
				if (gui.getGame()
						.getGameBoard()
						.getPlayerFieldActive()
						.getMonsterSlots()
						.get(gui.getGame().getGameBoard()
								.getPlayerFieldActive()
								.indexOfMonster((JButton) e.getSource()))
						.getCard().getDEFgain() != 0)
					gui.getGame().getDEFgain().setVisible(true);

			}

		if (gui.getGame().getGameBoard().getPlayerFieldOpponent()
				.containsMonster((JButton) e.getSource()))
			if (((JButton) e.getSource()).getSize().getWidth() == 80.0) {
				gui.getGame().getCardOverview()
						.setImageIcon((JButton) e.getSource());
				gui.getGame()
						.getATKgain()
						.setText(
								"<html>ATK<br>+"
										+ gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.getMonsterSlots()
												.get(gui.getGame()
														.getGameBoard()
														.getPlayerFieldOpponent()
														.indexOfMonster(
																(JButton) e
																		.getSource()))
												.getCard().getATKgain()
										+ "</html>");
				gui.getGame()
						.getDEFgain()
						.setText(
								"<html>DEF<br>+"
										+ gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.getMonsterSlots()
												.get(gui.getGame()
														.getGameBoard()
														.getPlayerFieldOpponent()
														.indexOfMonster(
																(JButton) e
																		.getSource()))
												.getCard().getDEFgain()
										+ "</html>");
				if (gui.getGame()
						.getGameBoard()
						.getPlayerFieldOpponent()
						.getMonsterSlots()
						.get(gui.getGame().getGameBoard()
								.getPlayerFieldOpponent()
								.indexOfMonster((JButton) e.getSource()))
						.getCard().getATKgain() != 0)
					gui.getGame().getATKgain().setVisible(true);
				if (gui.getGame()
						.getGameBoard()
						.getPlayerFieldOpponent()
						.getMonsterSlots()
						.get(gui.getGame().getGameBoard()
								.getPlayerFieldOpponent()
								.indexOfMonster((JButton) e.getSource()))
						.getCard().getDEFgain() != 0)
					gui.getGame().getDEFgain().setVisible(true);
			}

		if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
			if (gui.getGame().getGameBoard().getPlayerFieldActive()
					.containsMonster((JButton) e.getSource())
					|| gui.getGame().getGameBoard().getPlayerFieldActive()
							.containsSpell((JButton) e.getSource())) {
				gui.getGame().getCardOverview()
						.setImageIcon((JButton) e.getSource());
				if (gui.getGame().getGameBoard().getPlayerFieldActive()
						.containsMonster((JButton) e.getSource())) {
					gui.getGame()
							.getATKgain()
							.setText(
									"<html>ATK<br>+"
											+ gui.getGame()
													.getGameBoard()
													.getPlayerFieldActive()
													.getMonsterSlots()
													.get(gui.getGame()
															.getGameBoard()
															.getPlayerFieldActive()
															.indexOfMonster(
																	(JButton) e
																			.getSource()))
													.getCard().getATKgain()
											+ "</html>");
					gui.getGame()
							.getDEFgain()
							.setText(
									"<html>DEF<br>+"
											+ gui.getGame()
													.getGameBoard()
													.getPlayerFieldActive()
													.getMonsterSlots()
													.get(gui.getGame()
															.getGameBoard()
															.getPlayerFieldActive()
															.indexOfMonster(
																	(JButton) e
																			.getSource()))
													.getCard().getDEFgain()
											+ "</html>");
					if (gui.getGame()
							.getGameBoard()
							.getPlayerFieldActive()
							.getMonsterSlots()
							.get(gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.indexOfMonster((JButton) e.getSource()))
							.getCard().getATKgain() != 0)
						gui.getGame().getATKgain().setVisible(true);
					if (gui.getGame()
							.getGameBoard()
							.getPlayerFieldActive()
							.getMonsterSlots()
							.get(gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.indexOfMonster((JButton) e.getSource()))
							.getCard().getDEFgain() != 0)
						gui.getGame().getDEFgain().setVisible(true);
				}
			}
		} else {
			if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
				if (gui.getGame().getGameBoard().getPlayerFieldOpponent()
						.containsMonster((JButton) e.getSource())
						|| gui.getGame().getGameBoard()
								.getPlayerFieldOpponent()
								.containsSpell((JButton) e.getSource())) {
					gui.getGame().getCardOverview()
							.setImageIcon((JButton) e.getSource());
					if (gui.getGame().getGameBoard().getPlayerFieldOpponent()
							.containsMonster((JButton) e.getSource())) {
						gui.getGame()
								.getATKgain()
								.setText(
										"<html>ATK<br>+"
												+ gui.getGame()
														.getGameBoard()
														.getPlayerFieldOpponent()
														.getMonsterSlots()
														.get(gui.getGame()
																.getGameBoard()
																.getPlayerFieldOpponent()
																.indexOfMonster(
																		(JButton) e
																				.getSource()))
														.getCard().getATKgain()
												+ "</html>");
						gui.getGame()
								.getDEFgain()
								.setText(
										"<html>DEF<br>+"
												+ gui.getGame()
														.getGameBoard()
														.getPlayerFieldOpponent()
														.getMonsterSlots()
														.get(gui.getGame()
																.getGameBoard()
																.getPlayerFieldOpponent()
																.indexOfMonster(
																		(JButton) e
																				.getSource()))
														.getCard().getDEFgain()
												+ "</html>");
						if (gui.getGame()
								.getGameBoard()
								.getPlayerFieldOpponent()
								.getMonsterSlots()
								.get(gui.getGame()
										.getGameBoard()
										.getPlayerFieldOpponent()
										.indexOfMonster((JButton) e.getSource()))
								.getCard().getATKgain() != 0)
							gui.getGame().getATKgain().setVisible(true);
						if (gui.getGame()
								.getGameBoard()
								.getPlayerFieldOpponent()
								.getMonsterSlots()
								.get(gui.getGame()
										.getGameBoard()
										.getPlayerFieldOpponent()
										.indexOfMonster((JButton) e.getSource()))
								.getCard().getDEFgain() != 0)
							gui.getGame().getDEFgain().setVisible(true);
					}
				}
			}
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		gui.getGame().getCardOverview().resetImageIcon();
		gui.getGame().getATKgain().setVisible(false);
		gui.getGame().getDEFgain().setVisible(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void endGame() {
		gui.getGame().getGameover().setWinner(board.getWinner().getName());
		gui.getGame().getGameover().setVisible(true);
		gui.getGame().convertToLabels();
		Audio.stop();
		Audio.endMusic();
	}

	public boolean isSacrificeMode() {
		return gui.getGame().getHandAreaActive().getHandListener()
				.isSacrificeMode();
	}

	public void reset() {
		attackMode = false;
	}
}
