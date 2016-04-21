package eg.edu.guc.yugioh.cards.spells;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.MonsterCard;

public class HarpieFeatherDuster extends SpellCard {

	public HarpieFeatherDuster(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {

		ArrayList<SpellCard> x = (ArrayList<SpellCard>) getBoard()
				.getOpponentPlayer().getField().getSpellArea().clone();
		getBoard().getOpponentPlayer().getField().removeSpellToGraveyard(x);
	}

}
