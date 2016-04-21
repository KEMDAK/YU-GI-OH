package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;

public class PlayerFieldActive extends JPanel {
	private ArrayList<MonsterSlot> monsterSlots;
	private ArrayList<SpellSlot> spellSlots;

	public ArrayList<MonsterSlot> getMonsterSlots() {
		return monsterSlots;
	}

	public ArrayList<SpellSlot> getSpellSlots() {
		return spellSlots;
	}

	public PlayerFieldActive() {
		super();
		setPreferredSize(new Dimension(820, 280));
		setLayout(new GridLayout(2, 5));

		monsterSlots = new ArrayList<MonsterSlot>();
		spellSlots = new ArrayList<SpellSlot>();

		for (int i = 0; i < 5; i++) {
			MonsterSlot m = new MonsterSlot();
			monsterSlots.add(m);
			add(m);
		}

		for (int i = 0; i < 5; i++) {
			SpellSlot s = new SpellSlot();
			spellSlots.add(s);
			add(s);
		}

	}

	public void addToField(Card c) {
		if (c instanceof MonsterCard) {
			MonsterCard m = (MonsterCard) c;
			monsterSlots.get(freeSlotMonsters()).addMonster(m, false);
		} else if (c instanceof SpellCard) {
			SpellCard s = (SpellCard) c;
			spellSlots.get(freeSlotSpells()).addSpell(s, false);

		}
	}

	public void removeFromField(Card c, int index) {
		if (c instanceof MonsterCard) {
			monsterSlots.get(index).removeMonster();
		} else if (c instanceof SpellCard) {
			spellSlots.get(index).removeSpell();
		}
	}

	public int freeSlotMonsters() {
		for (int i = 0; i < monsterSlots.size(); i++)
			if (monsterSlots.get(i).isEmpty())
				return i;
		return -1;
	}

	public int freeSlotSpells() {
		for (int i = 0; i < spellSlots.size(); i++)
			if (spellSlots.get(i).isEmpty())
				return i;
		return -1;
	}

	public boolean containsMonster(JButton b) {
		for (MonsterSlot m : monsterSlots)
			if (m.getMonsterPanel() != null)
				if (m.getMonsterPanel().getComponents()[0] == b)
					return true;
				else
					continue;
		return false;
	}

	public boolean containsSpell(JButton b) {
		for (SpellSlot s : spellSlots)
			if (s.getSpellPanel() != null)
				if (s.getSpellPanel().getComponents()[0] == b)
					return true;
				else
					continue;
		return false;
	}

	public int indexOfMonster(JButton b) {
		for (int i = 0; i < monsterSlots.size(); i++) {
			if (monsterSlots.get(i).getMonsterPanel().getComponents()[0] == b)
				return i;
			else
				continue;
		}
		return -1;
	}

	public int indexOfSpell(JButton b) {
		for (int i = 0; i < spellSlots.size(); i++) {
			if (spellSlots.get(i).getSpellPanel().getComponents()[0] == b)
				return i;
			else
				continue;
		}
		return -1;
	}

	public void removeAllCards() {
		for (int i = 0; i < 5; i++) {
			if (!monsterSlots.get(i).isEmpty())
				monsterSlots.get(i).removeMonster();
			if (!spellSlots.get(i).isEmpty())
				spellSlots.get(i).removeSpell();
		}
	}

	public void convertToLabels() {
		for (MonsterSlot m : monsterSlots) {
			if (!m.isEmpty())
				m.convertToLabel();
		}

		for (SpellSlot s : spellSlots) {
			if (!s.isEmpty())
				s.convertToLabel();
		}
	}
}
