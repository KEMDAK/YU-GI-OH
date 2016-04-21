package eg.edu.guc.yugioh.board.player;

import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;

public class Field {

	private Phase phase;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpellCard> spellArea;
	private Deck deck;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;

	public Field() throws IOException, UnexpectedFormatException {
		monstersArea = new ArrayList<MonsterCard>(5);
		spellArea = new ArrayList<SpellCard>(5);
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new Deck();
		phase = Phase.MAIN1;
	}

	public void addMonsterToField(MonsterCard monster, Mode m, boolean isHidden) {
		monster.setMode(m);
		monster.setHidden(isHidden);
		monster.setLocation(Location.FIELD);
		hand.remove(monster);
		monstersArea.add(monster);
	}

	public void addMonsterToField(MonsterCard monster, Mode mode,
			ArrayList<MonsterCard> sacrifices) {
		switch (monster.getLevel()) {
		case 1:
			;
		case 2:
			;
		case 3:
			;
		case 4:
			if (sacrifices.size() == 0)
				addMonsterToField(monster, mode, (mode.equals("ATTACK") ? false
						: true));
			break;

		case 5:
			;
		case 6:
			if (sacrifices.size() == 1) {
				removeMonsterToGraveyard(sacrifices);
				addMonsterToField(monster, mode, (mode.equals("ATTACK") ? false
						: true));
			}
			;
			break;

		case 7:
			;
		case 8:
			if (sacrifices.size() == 2) {
				removeMonsterToGraveyard(sacrifices);
				addMonsterToField(monster, mode, (mode.equals("ATTACK") ? false
						: true));
			}
			;
			break;

		}

	}

	public void removeMonsterToGraveyard(MonsterCard monster) {
		if (monstersArea.contains(monster)) {
			if (monster != null) {
				monster.setLocation(Location.GRAVEYARD);
				monstersArea.remove(monster);
				graveyard.add(monster);
				monster.reset();
			}
		}
	}

	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters) {
		for (MonsterCard x : monsters)
			removeMonsterToGraveyard(x);
	}

	public void addSpellToField(SpellCard action, MonsterCard monster,
			boolean isHidden) {
		action.setHidden(isHidden);
		action.setLocation(Location.FIELD);
		spellArea.add(action);
		hand.remove(action);
		if (!isHidden)
			activateSetSpell(action, monster);
	}

	public void activateSetSpell(SpellCard action, MonsterCard monster) {
		if (spellArea.contains(action)) {
			action.action(monster);
			action.setHidden(false);
			removeSpellToGraveyard(action);
		}
	}

	public void removeSpellToGraveyard(SpellCard spell) {
		if (spellArea.contains(spell)) {
			if (spell != null) {
				spell.setLocation(Location.GRAVEYARD);
				spellArea.remove(spell);
				graveyard.add(spell);
			}
		}
	}

	public void resetCanAttack() {
		for (MonsterCard x : monstersArea)
			x.setCanIAttack(true);
	}

	public void resetCanSwitchMode() {
		for (MonsterCard x : monstersArea)
			x.setCanISwitchMode(true);
	}

	public void removeSpellToGraveyard(ArrayList<SpellCard> spells) {
		for (SpellCard x : spells)
			removeSpellToGraveyard(x);
	}

	public void addNCardsToHand(int n) {
		for (int i = 1; i <= n; i++)
			addCardToHand();
	}

	public void addCardToHand() {
		Card x = deck.drawOneCard();
		if (x != null) {
			hand.add(x);
			x.setLocation(Location.HAND);
		}
	}

	public boolean isThereMonster() {
		for (Card x : graveyard) {
			if (x instanceof MonsterCard)
				return true;
		}
		return false;
	}

	public Phase getPhase() {
		return phase;
	}

	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}

	public ArrayList<SpellCard> getSpellArea() {
		return spellArea;
	}

	public Deck getDeck() {
		return deck;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

}
