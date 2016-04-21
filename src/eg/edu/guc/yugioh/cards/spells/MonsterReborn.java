package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;

public class MonsterReborn extends SpellCard {

	public MonsterReborn(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {
		if (getBoard().getActivePlayer().getField().getMonstersArea().size() >= 5)
			throw new NoMonsterSpaceException();

		MonsterCard maxAtt = new MonsterCard("Kareem", "gamed moot", 1000, 0, 0);
		for (Card x : getBoard().getActivePlayer().getField().getGraveyard()) {
			if (x instanceof MonsterCard) {
				if (((MonsterCard) x).getAttackPoints() > maxAtt
						.getAttackPoints())
					maxAtt = (MonsterCard) x;
			}
		}

		boolean flag = false;

		for (Card x : getBoard().getOpponentPlayer().getField().getGraveyard()) {
			if (x instanceof MonsterCard) {
				if (((MonsterCard) x).getAttackPoints() > maxAtt
						.getAttackPoints()) {
					maxAtt = (MonsterCard) x;
					flag = true;
				}
			}
		}
		if (!flag)
			getBoard().getActivePlayer().getField().getGraveyard()
					.remove(maxAtt);
		else
			getBoard().getOpponentPlayer().getField().getGraveyard()
					.remove(maxAtt);
		getBoard().getActivePlayer().getField()
				.addMonsterToField(maxAtt, Mode.ATTACK, false);

	}
}
