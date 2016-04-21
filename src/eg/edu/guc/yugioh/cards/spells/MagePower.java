package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class MagePower extends SpellCard {

	public MagePower(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		int gain = getBoard().getActivePlayer().getField().getSpellArea()
				.size() * 500;
		m.setAttackPoints(m.getAttackPoints() + gain);
		m.setATKgain(m.getATKgain() + gain);
		m.setDefensePoints(m.getDefensePoints() + gain);
		m.setDEFgain(m.getDEFgain() + gain);
	}

}
