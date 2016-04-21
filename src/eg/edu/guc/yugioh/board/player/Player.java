package eg.edu.guc.yugioh.board.player;

import java.io.IOException;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.MultipleSwitchingModeException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class Player implements Duelist {

	private String name;
	private int lifePoints;
	private Field field;
	private static boolean CanIAddMonster;

	public Player(String name) throws IOException, UnexpectedFormatException {
		this.name = name;
		lifePoints = 8000;
		field = new Field();
		CanIAddMonster = true;
	}

	public static boolean getCanIAddMonster() {
		return CanIAddMonster;
	}

	public static void setCanIAddMonster(boolean canIAddMonster) {
		CanIAddMonster = canIAddMonster;
	}

	public String getName() {
		return name;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public Field getField() {
		return field;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public boolean summonMonster(MonsterCard monster) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (monster.getLocation() == Location.HAND) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (getField().getMonstersArea().size() >= 5)
						throw new NoMonsterSpaceException();
					if (!CanIAddMonster)
						throw new MultipleMonsterAdditionException();

					getField().addMonsterToField(monster, Mode.ATTACK, false);
					CanIAddMonster = false;
					return true;
				}
		return false;

	}

	public boolean summonMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (monster.getLocation() == Location.HAND) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (!CanIAddMonster)
						throw new MultipleMonsterAdditionException();
					// if (getField().getMonstersArea().size() >= 5)
					// throw new NoMonsterSpaceException();
					if (getField().getMonstersArea().size() == 5
							&& sacrifices.size() == 0)
						throw new NoMonsterSpaceException();

					getField().addMonsterToField(monster, Mode.ATTACK,
							sacrifices);
					CanIAddMonster = false;

				}
		if (monster.getLocation() == Location.FIELD)
			return true;
		else
			return false;

	}

	public boolean setMonster(MonsterCard monster) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (monster.getLocation() == Location.HAND) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (getField().getMonstersArea().size() >= 5)
						throw new NoMonsterSpaceException();
					if (!CanIAddMonster)
						throw new MultipleMonsterAdditionException();

					getField().addMonsterToField(monster, Mode.DEFENSE, true);
					CanIAddMonster = false;
					return true;
				}
		return false;
	}

	public boolean setMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (monster.getLocation() == Location.HAND) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (!CanIAddMonster)
						throw new MultipleMonsterAdditionException();
					// if (getField().getMonstersArea().size() >= 5)
					// throw new NoMonsterSpaceException();
					if (getField().getMonstersArea().size() == 5
							&& sacrifices.size() == 0)
						throw new NoMonsterSpaceException();

					getField().addMonsterToField(monster, Mode.DEFENSE,
							sacrifices);
					CanIAddMonster = false;

				}
		if (monster.getLocation() == Location.FIELD)
			return true;
		else
			return false;
	}

	public boolean setSpell(SpellCard spell) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (spell.getLocation() == Location.HAND) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (getField().getSpellArea().size() >= 5)
						throw new NoSpellSpaceException();

					getField().addSpellToField(spell, null, true);
					return true;

				}
		return false;
	}

	public boolean activateSpell(SpellCard spell, MonsterCard monster)
			throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this) {
				if (getField().getPhase() == Phase.BATTLE)
					throw new WrongPhaseException();
				if (spell.getLocation() == Location.FIELD) {
					getField().activateSetSpell(spell, monster);
					return true;
				} else if (spell.getLocation() == Location.HAND) {
					if (getField().getSpellArea().size() >= 5)
						throw new NoSpellSpaceException();

					getField().addSpellToField(spell, monster, false);
					return true;

				}
			}
		return false;
	}

	public boolean declareAttack(MonsterCard activeMonster,
			MonsterCard opponentMonster) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (activeMonster.getLocation() == Location.FIELD)
					if (opponentMonster.getLocation() == Location.FIELD) {
						if (getField().getPhase() != Phase.BATTLE)
							throw new WrongPhaseException();
						if (!activeMonster.getCanIAttack())
							throw new MonsterMultipleAttackException();
						if (activeMonster.getMode() == Mode.DEFENSE)
							throw new DefenseMonsterAttackException();

						activeMonster.action(opponentMonster);
						activeMonster.setCanIAttack(false);
						if (Card.getBoard().getOpponentPlayer().getLifePoints() <= 0) {
							Card.getBoard().setWinner(
									Card.getBoard().getActivePlayer());
							Card.getBoard().setContinuePlaying(false);
						}
						if (Card.getBoard().getActivePlayer().getLifePoints() <= 0) {
							Card.getBoard().setWinner(
									Card.getBoard().getOpponentPlayer());
							Card.getBoard().setContinuePlaying(false);
						}
						return true;

					}
		return false;
	}

	public boolean declareAttack(MonsterCard activeMonster)
			throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (activeMonster.getLocation() == Location.FIELD)
					if (Card.getBoard().getOpponentPlayer().getField()
							.getMonstersArea().isEmpty()) {
						if (getField().getPhase() != Phase.BATTLE)
							throw new WrongPhaseException();
						if (!activeMonster.getCanIAttack())
							throw new MonsterMultipleAttackException();
						if (activeMonster.getMode() == Mode.DEFENSE)
							throw new DefenseMonsterAttackException();

						activeMonster.action();
						activeMonster.setCanIAttack(false);
						if (Card.getBoard().getOpponentPlayer().getLifePoints() <= 0) {
							Card.getBoard().setWinner(
									Card.getBoard().getActivePlayer());
							Card.getBoard().setContinuePlaying(false);
						}
						return true;
					}
		return false;
	}

	public void addCardToHand() throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (getField().getPhase() == Phase.BATTLE)
					throw new WrongPhaseException();

		getField().addCardToHand();
	}

	public void addNCardsToHand(int n) throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (getField().getPhase() == Phase.BATTLE)
					throw new WrongPhaseException();

		getField().addNCardsToHand(n);
	}

	public void endPhase() {
		if (Card.getBoard().getContinuePlaying()
				&& Card.getBoard().getActivePlayer() == this) {
			if (getField().getPhase() == Phase.MAIN1)
				getField().setPhase(Phase.BATTLE);
			else if (getField().getPhase() == Phase.BATTLE)
				getField().setPhase(Phase.MAIN2);
			else if (getField().getPhase() == Phase.MAIN2)
				endTurn();
		}
	}

	public boolean endTurn() {
		if (Card.getBoard().getContinuePlaying()
				&& Card.getBoard().getActivePlayer() == this) {
			Card.getBoard().nextPlayer();
			return true;
		}
		return false;
	}

	public boolean switchMonsterMode(MonsterCard monster)
			throws RuntimeException {
		if (Card.getBoard().getContinuePlaying())
			if (Card.getBoard().getActivePlayer() == this)
				if (monster.getLocation() == Location.FIELD) {
					if (getField().getPhase() == Phase.BATTLE)
						throw new WrongPhaseException();
					if (!monster.getCanISwitchMode())
						throw new MultipleSwitchingModeException();

					if (monster.getMode() == Mode.ATTACK)
						monster.setMode(Mode.DEFENSE);
					else {
						if (monster.isHidden() == true)
							monster.setHidden(false);
						monster.setMode(Mode.ATTACK);
					}
					monster.setCanISwitchMode(false);
					return true;

				}
		return false;
	}
	
	public static void main(String[] args) {
		MonsterCard x = new MonsterCard("kareem", "", 4, 4, 4);
		Card y =   x;
		
	}

}
