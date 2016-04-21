package eg.edu.guc.yugioh.cards.spells;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class HeavyStorm extends HarpieFeatherDuster {

	public HeavyStorm(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		super.action(null);
		ArrayList<SpellCard> x = (ArrayList<SpellCard>) getBoard()
				.getActivePlayer().getField().getSpellArea().clone();
		getBoard().getActivePlayer().getField().removeSpellToGraveyard(x);
	}

}
