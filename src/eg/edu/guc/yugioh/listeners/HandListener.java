package eg.edu.guc.yugioh.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.MonsterReborn;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.HandCard;

public class HandListener implements ActionListener, MouseListener {
	private GUI gui;
	private Board board;
	private boolean sacrificeMode;
	private boolean monsterMode;
	private int sacrifices;
	private MonsterCard sacrificeMonster;
	private ArrayList<MonsterCard> sacrificeMonsters;

	public HandListener(GUI gui, Board board) {
		this.gui = gui;
		this.board = board;
		sacrificeMonsters = new ArrayList<MonsterCard>();
		gui.getGame().getHandAreaActive().setHandListener(this);
		gui.getGame().getHandAreaOpponent().setHandListener(this);
	}

	public boolean isSacrificeMode() {
		return sacrificeMode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (board.getContinuePlaying()) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
					if (sacrificeMode == false) {
						if (gui.getGame().getHandAreaActive().getHandCards()
								.contains((HandCard) e.getSource())) {
							Card c = board
									.getActivePlayer()
									.getField()
									.getHand()
									.get(gui.getGame().getHandAreaActive()
											.getHandCards()
											.indexOf((HandCard) e.getSource()));
							if (c instanceof MonsterCard) {
								MonsterCard m = (MonsterCard) c;
								if (m.getLevel() < 5) {
									try {
										if (board.getActivePlayer()
												.summonMonster(m)) {
											gui.getGame().getGameBoard()
													.getPlayerFieldActive()
													.addToField(m);
										}
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								} else {
									if (m.getLevel() < 7) {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 1) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select a monster to sacrafice");

									} else {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 2) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select 2 monsters to sacrafice");
									}
									sacrificeMode = true;
									sacrifices = 0;
									sacrificeMonster = m;
									monsterMode = true;
								}
							} else {
								SpellCard s = (SpellCard) c;
								try {
									if (s instanceof ChangeOfHeart) {
										if (board.getOpponentPlayer()
												.getField().getMonstersArea()
												.isEmpty()) {
											JOptionPane
													.showMessageDialog(gui,
															"The opponent doesn't have any monsters.");
											return;
										}
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() == 5) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't add any more monsters.");
											return;
										}
									}

									if ((s instanceof MagePower && board
											.getActivePlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane.showMessageDialog(gui,
												"You don't have any monsters.");
										return;
									}
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
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() == 5) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't add any more monsters.");
											return;
										}
									}
									if (board.getActivePlayer().setSpell(s)) {
										int i = gui.getGame().getGameBoard()
												.getPlayerFieldActive()
												.freeSlotSpells();
										gui.getGame().getGameBoard()
												.getPlayerFieldActive()
												.addToField(s);
										JButton b = ((JButton) gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.getSpellSlots().get(i)
												.getSpellPanel()
												.getComponents()[0]);
										MouseEvent m = new MouseEvent(b,
												e.getID(), e.getWhen(),
												e.getModifiers(), b.getX(),
												b.getY(), b.getX(), b.getY(),
												e.getClickCount(),
												e.isPopupTrigger(),
												e.getButton());
										gui.getGame().getGameBoard()
												.getPlayerFieldActive()
												.getMonsterSlots().get(0)
												.getFieldlistener()
												.mouseClicked(m);
									}
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						}
					} else {
						if (sacrifices == 0) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								sacrificeMonsters.add(m);
								if (sacrificeMonster.getLevel() < 7) {
									try {
										board.getActivePlayer().summonMonster(
												sacrificeMonster,
												sacrificeMonsters);
										if (monsterMode)
											sacrificeMonster
													.setMode(Mode.ATTACK);
										else
											sacrificeMonster
													.setMode(Mode.DEFENSE);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
									sacrifices = 0;
									sacrificeMode = false;
									sacrificeMonsters.clear();
								} else
									sacrifices = 1;

							}
						} else if (sacrifices == 1) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (sacrificeMonsters.contains(m)) {
									JOptionPane
											.showMessageDialog(gui,
													"You can't sacrifice the same monster twice.");
									return;
								}
								sacrificeMonsters.add(m);
								try {
									board.getActivePlayer()
											.summonMonster(sacrificeMonster,
													sacrificeMonsters);
									if (monsterMode)
										sacrificeMonster.setMode(Mode.ATTACK);
									else
										sacrificeMonster.setMode(Mode.DEFENSE);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
								sacrifices = 0;
								sacrificeMode = false;
								sacrificeMonsters.clear();
							}
						}
					}
				} else if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
					if (sacrificeMode == false) {
						if (gui.getGame().getHandAreaOpponent().getHandCards()
								.contains((HandCard) e.getSource())) {
							Card c = board
									.getActivePlayer()
									.getField()
									.getHand()
									.get(gui.getGame().getHandAreaOpponent()
											.getHandCards()
											.indexOf((HandCard) e.getSource()));
							if (c instanceof MonsterCard) {
								MonsterCard m = (MonsterCard) c;
								if (m.getLevel() < 5) {
									try {
										if (board.getActivePlayer()
												.summonMonster(m)) {
											gui.getGame().getGameBoard()
													.getPlayerFieldOpponent()
													.addToField(m);
										}
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								} else {
									if (m.getLevel() < 7) {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 1) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select a monster to sacrafice");

									} else {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 2) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select 2 monsters to sacrafice");
									}
									sacrificeMode = true;
									sacrifices = 0;
									sacrificeMonster = m;
									monsterMode = true;
								}
							} else {
								SpellCard s = (SpellCard) c;
								try {
									if (s instanceof ChangeOfHeart) {
										if (board.getOpponentPlayer()
												.getField().getMonstersArea()
												.isEmpty()) {
											JOptionPane
													.showMessageDialog(gui,
															"The opponent doesn't have any monsters.");
											return;
										}
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() == 5) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't add any more monsters.");
											return;
										}

									}

									if ((s instanceof MagePower && board
											.getActivePlayer().getField()
											.getMonstersArea().isEmpty())) {
										JOptionPane.showMessageDialog(gui,
												"You don't have any monsters.");
										return;
									}

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
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() == 5) {
											JOptionPane
													.showMessageDialog(gui,
															"You can't add any more monsters.");
											return;
										}
									}

									if (board.getActivePlayer().setSpell(s)) {
										int i = gui.getGame().getGameBoard()
												.getPlayerFieldOpponent()
												.freeSlotSpells();
										gui.getGame().getGameBoard()
												.getPlayerFieldOpponent()
												.addToField(s);
										JButton b = ((JButton) gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.getSpellSlots().get(i)
												.getSpellPanel()
												.getComponents()[0]);
										MouseEvent m = new MouseEvent(b,
												e.getID(), e.getWhen(),
												e.getModifiers(), b.getX(),
												b.getY(), b.getX(), b.getY(),
												e.getClickCount(),
												e.isPopupTrigger(),
												e.getButton());
										gui.getGame().getGameBoard()
												.getPlayerFieldOpponent()
												.getMonsterSlots().get(0)
												.getFieldlistener()
												.mouseClicked(m);
									}
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						}
					} else {
						if (sacrifices == 0) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								sacrificeMonsters.add(m);
								if (sacrificeMonster.getLevel() < 7) {
									try {
										board.getActivePlayer().summonMonster(
												sacrificeMonster,
												sacrificeMonsters);
										if (monsterMode)
											sacrificeMonster
													.setMode(Mode.ATTACK);
										else
											sacrificeMonster
													.setMode(Mode.DEFENSE);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
									sacrifices = 0;
									sacrificeMode = false;
									sacrificeMonsters.clear();
								} else
									sacrifices = 1;
							}
						} else if (sacrifices == 1) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (sacrificeMonsters.contains(m)) {
									JOptionPane
											.showMessageDialog(gui,
													"You can't sacrifice the same monster twice.");
									return;
								}
								sacrificeMonsters.add(m);
								try {
									board.getActivePlayer()
											.summonMonster(sacrificeMonster,
													sacrificeMonsters);
									if (monsterMode)
										sacrificeMonster.setMode(Mode.ATTACK);
									else
										sacrificeMonster.setMode(Mode.DEFENSE);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
								sacrifices = 0;
								sacrificeMode = false;
								sacrificeMonsters.clear();
							}
						}
					}
				}
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
					if (sacrificeMode == false) {
						if (gui.getGame().getHandAreaActive().getHandCards()
								.contains((HandCard) e.getSource())) {
							Card c = board
									.getActivePlayer()
									.getField()
									.getHand()
									.get(gui.getGame().getHandAreaActive()
											.getHandCards()
											.indexOf((HandCard) e.getSource()));
							if (c instanceof MonsterCard) {
								MonsterCard m = (MonsterCard) c;
								if (m.getLevel() < 5) {
									try {
										if (board.getActivePlayer().setMonster(
												m)) {
											gui.getGame().getGameBoard()
													.getPlayerFieldActive()
													.addToField(m);
										}
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								} else {
									if (m.getLevel() < 7) {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 1) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select a monster to sacrafice");

									} else {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 2) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select 2 monsters to sacrafice");
									}
									sacrificeMode = true;
									sacrifices = 0;
									sacrificeMonster = m;
									monsterMode = false;
								}
							} else {
								SpellCard s = (SpellCard) c;
								try {
									if (board.getActivePlayer().setSpell(s)) {
										gui.getGame().getGameBoard()
												.getPlayerFieldActive()
												.addToField(s);
									}
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						}
					} else {
						if (sacrifices == 0) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								sacrificeMonsters.add(m);
								if (sacrificeMonster.getLevel() < 7) {
									try {
										board.getActivePlayer().setMonster(
												sacrificeMonster,
												sacrificeMonsters);
										if (monsterMode)
											sacrificeMonster
													.setMode(Mode.ATTACK);
										else
											sacrificeMonster
													.setMode(Mode.DEFENSE);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
									sacrifices = 0;
									sacrificeMode = false;
									sacrificeMonsters.clear();
								} else
									sacrifices = 1;

							}
						} else if (sacrifices == 1) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldActive()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldActive()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (sacrificeMonsters.contains(m)) {
									JOptionPane
											.showMessageDialog(gui,
													"You can't sacrifice the same monster twice.");
									return;
								}
								sacrificeMonsters.add(m);
								try {
									board.getActivePlayer()
											.setMonster(sacrificeMonster,
													sacrificeMonsters);
									if (monsterMode)
										sacrificeMonster.setMode(Mode.ATTACK);
									else
										sacrificeMonster.setMode(Mode.DEFENSE);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
								sacrifices = 0;
								sacrificeMode = false;
								sacrificeMonsters.clear();
							}
						}
					}
				}

				else if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
					if (sacrificeMode == false) {
						if (gui.getGame().getHandAreaOpponent().getHandCards()
								.contains((HandCard) e.getSource())) {
							Card c = board
									.getActivePlayer()
									.getField()
									.getHand()
									.get(gui.getGame().getHandAreaOpponent()
											.getHandCards()
											.indexOf((HandCard) e.getSource()));
							if (c instanceof MonsterCard) {
								MonsterCard m = (MonsterCard) c;
								if (m.getLevel() < 5) {
									try {
										if (board.getActivePlayer().setMonster(
												m)) {
											gui.getGame().getGameBoard()
													.getPlayerFieldOpponent()
													.addToField(m);
										}
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
								} else {
									if (m.getLevel() < 7) {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 1) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select a monster to sacrafice");

									} else {
										if (board.getActivePlayer().getField()
												.getMonstersArea().size() < 2) {
											JOptionPane
													.showMessageDialog(gui,
															"You don't have enough monsters to sacrafice");
											return;
										}
										JOptionPane
												.showMessageDialog(gui,
														"Please select 2 monsters to sacrafice");
									}
									sacrificeMode = true;
									sacrifices = 0;
									sacrificeMonster = m;
									monsterMode = false;
								}
							} else {
								SpellCard s = (SpellCard) c;
								try {
									if (board.getActivePlayer().setSpell(s)) {
										gui.getGame().getGameBoard()
												.getPlayerFieldOpponent()
												.addToField(s);
									}
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
							}
						}
					} else {
						if (sacrifices == 0) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								sacrificeMonsters.add(m);
								if (sacrificeMonster.getLevel() < 7) {
									try {
										board.getActivePlayer().setMonster(
												sacrificeMonster,
												sacrificeMonsters);
										if (monsterMode)
											sacrificeMonster
													.setMode(Mode.ATTACK);
										else
											sacrificeMonster
													.setMode(Mode.DEFENSE);
									} catch (RuntimeException e1) {
										JOptionPane.showMessageDialog(gui,
												e1.getMessage());
									}
									sacrifices = 0;
									sacrificeMode = false;
									sacrificeMonsters.clear();
								} else
									sacrifices = 1;
							}
						} else if (sacrifices == 1) {
							if (gui.getGame().getGameBoard()
									.getPlayerFieldOpponent()
									.containsMonster((JButton) e.getSource())) {
								MonsterCard m = board
										.getActivePlayer()
										.getField()
										.getMonstersArea()
										.get(gui.getGame()
												.getGameBoard()
												.getPlayerFieldOpponent()
												.indexOfMonster(
														(JButton) e.getSource()));
								if (sacrificeMonsters.contains(m)) {
									JOptionPane
											.showMessageDialog(gui,
													"You can't sacrifice the same monster twice.");
									return;
								}
								sacrificeMonsters.add(m);
								try {
									board.getActivePlayer()
											.setMonster(sacrificeMonster,
													sacrificeMonsters);
									if (monsterMode)
										sacrificeMonster.setMode(Mode.ATTACK);
									else
										sacrificeMonster.setMode(Mode.DEFENSE);
								} catch (RuntimeException e1) {
									JOptionPane.showMessageDialog(gui,
											e1.getMessage());
								}
								sacrifices = 0;
								sacrificeMode = false;
								sacrificeMonsters.clear();
							}
						}
					}
				}
			}
			if (gui.getGame().getGameBoard().getWhoIsActive() == 0)
				updateHand(false);
			else
				updateHand(true);
			gui.getGame().grabFocus();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (gui.getGame().getGameBoard().getWhoIsActive() == 0) {
			if (gui.getGame().getHandAreaActive().getHandCards()
					.contains((JButton) e.getSource()))
				gui.getGame().getCardOverview()
						.setImageIcon((JButton) e.getSource());
		} else {
			if (gui.getGame().getGameBoard().getWhoIsActive() == 1) {
				if (gui.getGame().getHandAreaOpponent().getHandCards()
						.contains((JButton) e.getSource()))
					gui.getGame().getCardOverview()
							.setImageIcon((JButton) e.getSource());
			}
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

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void updateHand(boolean afterEndTurn) {
		gui.getGame().getHandAreaActive().removeAllCards();
		gui.getGame().getHandAreaOpponent().removeAllCards();
		if (afterEndTurn) {
			gui.getGame().getHandAreaOpponent()
					.addNCards(board.getActivePlayer().getField().getHand());
			gui.getGame().getHandAreaActive()
					.addNCards(board.getOpponentPlayer().getField().getHand());
			gui.getGame()
					.getDeckSizeActive()
					.setText(
							board.getOpponentPlayer().getField().getDeck()
									.getDeck().size()
									+ "");
			gui.getGame()
					.getDeckSizeOpponent()
					.setText(
							board.getActivePlayer().getField().getDeck()
									.getDeck().size()
									+ "");
		} else {
			gui.getGame().getHandAreaActive()
					.addNCards(board.getActivePlayer().getField().getHand());
			gui.getGame().getHandAreaOpponent()
					.addNCards(board.getOpponentPlayer().getField().getHand());
			gui.getGame()
					.getDeckSizeActive()
					.setText(
							board.getActivePlayer().getField().getDeck()
									.getDeck().size()
									+ "");
			gui.getGame()
					.getDeckSizeOpponent()
					.setText(
							board.getOpponentPlayer().getField().getDeck()
									.getDeck().size()
									+ "");
		}
		if (gui.getGame().getGameBoard().getWhoIsActive() == 0)
			gui.getGame().getHandAreaOpponent().hideCards(true);
		else if (gui.getGame().getGameBoard().getWhoIsActive() == 1)
			gui.getGame().getHandAreaActive().hideCards(false);

		if (board.getContinuePlaying() == false)
			gui.getGame().getGameBoard().getPlayerFieldActive()
					.getMonsterSlots().get(0).getFieldlistener().endGame();

		gui.getGame().updateUI();

	}

	public void reset() {
		sacrifices = 0;
		sacrificeMode = false;
		sacrificeMonsters.clear();
	}
}
