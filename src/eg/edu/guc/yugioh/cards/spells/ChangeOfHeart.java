package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;

public class ChangeOfHeart extends SpellCard {

	public ChangeOfHeart(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {
		if (getBoard().getActivePlayer().getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();

		getBoard().getOpponentPlayer().getField().getMonstersArea().remove(m);
		getBoard().getActivePlayer().getField()
				.addMonsterToField(m, m.getMode(), m.isHidden());
		m.setCanIAttack(true);
		m.setCanISwitchMode(true);
	}

}
