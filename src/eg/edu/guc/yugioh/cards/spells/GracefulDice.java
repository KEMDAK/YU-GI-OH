package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class GracefulDice extends SpellCard {

	public GracefulDice(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		int gain = ((int) (Math.random() * 10) + 1) * 100;
		for (MonsterCard x : getBoard().getActivePlayer().getField()
				.getMonstersArea()) {
			x.setAttackPoints(x.getAttackPoints() + gain);
			x.setATKgain(x.getATKgain() + gain);
			x.setDefensePoints(x.getDefensePoints() + gain);
			x.setDEFgain(x.getDEFgain() + gain);
		}
	}

}
