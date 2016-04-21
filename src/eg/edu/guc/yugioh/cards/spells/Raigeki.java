package eg.edu.guc.yugioh.cards.spells;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class Raigeki extends SpellCard {

	public Raigeki(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		ArrayList<MonsterCard> x = (ArrayList<MonsterCard>) getBoard()
				.getOpponentPlayer().getField().getMonstersArea().clone();
		getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(x);
	}

}
