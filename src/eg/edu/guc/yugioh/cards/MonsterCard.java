package eg.edu.guc.yugioh.cards;

public class MonsterCard extends Card {

	private int level, defensePoints, attackPoints;
	private Mode mode;
	private boolean CanIAttack;
	private boolean CanISwitchMode;
	int ATKgain, DEFgain;

	public boolean getCanISwitchMode() {
		return CanISwitchMode;
	}

	public void setCanISwitchMode(boolean canISwitchMode) {
		CanISwitchMode = canISwitchMode;
	}

	public boolean getCanIAttack() {
		return CanIAttack;
	}

	public void setCanIAttack(boolean canIAttack) {
		CanIAttack = canIAttack;
	}

	public int getLevel() {
		return level;
	}

	public int getDefensePoints() {
		return defensePoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public Mode getMode() {
		return mode;
	}

	public void setDefensePoints(int defensePoints) {
		this.defensePoints = defensePoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public MonsterCard(String name, String description, int level, int attack,
			int defense) {
		super(name, description);
		this.level = level;
		attackPoints = attack;
		defensePoints = defense;
		mode = Mode.DEFENSE;
		CanIAttack = true;
		CanISwitchMode = true;
		ATKgain = 0;
		DEFgain = 0;
	}

	public int getATKgain() {
		return ATKgain;
	}

	public int getDEFgain() {
		return DEFgain;
	}

	public void setATKgain(int aTKgain) {
		ATKgain = aTKgain;
	}

	public void setDEFgain(int dEFgain) {
		DEFgain = dEFgain;
	}

	public void action(MonsterCard m) {
		if (m.mode == Mode.ATTACK) {
			if (getAttackPoints() > m.getAttackPoints()) {
				int diff = getAttackPoints() - m.getAttackPoints();
				getBoard().getOpponentPlayer().setLifePoints(
						getBoard().getOpponentPlayer().getLifePoints() - diff);
				getBoard().getOpponentPlayer().getField()
						.removeMonsterToGraveyard(m);
			} else if (getAttackPoints() == m.getAttackPoints()) {
				getBoard().getOpponentPlayer().getField()
						.removeMonsterToGraveyard(m);
				getBoard().getActivePlayer().getField()
						.removeMonsterToGraveyard(this);
			} else {
				int diff = m.getAttackPoints() - getAttackPoints();
				getBoard().getActivePlayer().setLifePoints(
						getBoard().getActivePlayer().getLifePoints() - diff);
				getBoard().getActivePlayer().getField()
						.removeMonsterToGraveyard(this);
			}
		} else {
			if (getAttackPoints() > m.getDefensePoints())
				getBoard().getOpponentPlayer().getField()
						.removeMonsterToGraveyard(m);

			else if (getAttackPoints() < m.getDefensePoints()) {
				int diff = m.getDefensePoints() - getAttackPoints();
				getBoard().getActivePlayer().setLifePoints(
						getBoard().getActivePlayer().getLifePoints() - diff);
			}
		}
	}

	public void action() {
		getBoard().getOpponentPlayer().setLifePoints(
				getBoard().getOpponentPlayer().getLifePoints()
						- getAttackPoints());
	}

	public void reset() {
		CanIAttack = true;
		CanISwitchMode = true;
	}
}
