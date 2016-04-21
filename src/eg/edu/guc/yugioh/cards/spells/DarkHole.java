package eg.edu.guc.yugioh.cards.spells;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class DarkHole extends Raigeki {

	public DarkHole(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		super.action(null);
		ArrayList<MonsterCard> x = (ArrayList<MonsterCard>) getBoard()
				.getActivePlayer().getField().getMonstersArea().clone();
		getBoard().getActivePlayer().getField().removeMonsterToGraveyard(x);
	}

}
