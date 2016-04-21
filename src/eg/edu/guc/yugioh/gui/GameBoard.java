package eg.edu.guc.yugioh.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import eg.edu.guc.yugioh.listeners.FieldListener;

public class GameBoard extends JPanel {
	private PlayerFieldOpponent playerFieldOpponent;
	private PlayerFieldActive playerFieldActive;
	private int whoIsActive;

	public int getWhoIsActive() {
		return whoIsActive;
	}

	public void setWhoIsActive(int whoIsActive) {
		this.whoIsActive = whoIsActive;
	}

	public PlayerFieldOpponent getPlayerFieldOpponent() {
		return playerFieldOpponent;
	}

	public PlayerFieldActive getPlayerFieldActive() {
		return playerFieldActive;
	}

	public GameBoard() {
		super();
		setSize(650, 450);
		setLayout(new GridLayout(2, 1));

		whoIsActive = 0;
		playerFieldOpponent = new PlayerFieldOpponent();
		add(playerFieldOpponent);

		playerFieldActive = new PlayerFieldActive();
		add(playerFieldActive);
	}

	public void assignListeners(FieldListener g) {
		for (SpellSlot s : getPlayerFieldActive().getSpellSlots())
			s.setFieldlistener(g);

		for (SpellSlot s : getPlayerFieldOpponent().getSpellSlots())
			s.setFieldlistener(g);

		for (MonsterSlot s : getPlayerFieldActive().getMonsterSlots())
			s.setFieldlistener(g);

		for (MonsterSlot s : getPlayerFieldOpponent().getMonsterSlots())
			s.setFieldlistener(g);
	}

}
