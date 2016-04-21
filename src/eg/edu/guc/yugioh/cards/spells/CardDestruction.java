package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class CardDestruction extends SpellCard {

	public CardDestruction(String name, String description) {
		super(name, description);
	}

	public void action(MonsterCard m) {
		int drawActive = getBoard().getActivePlayer().getField().getHand()
				.size();
		int drawOpp = getBoard().getOpponentPlayer().getField().getHand()
				.size();
		while (!getBoard().getActivePlayer().getField().getHand().isEmpty()) {
			getBoard()
					.getActivePlayer()
					.getField()
					.getGraveyard()
					.add(getBoard().getActivePlayer().getField().getHand()
							.get(0));
			getBoard().getActivePlayer().getField().getHand().get(0)
					.setLocation(Location.GRAVEYARD);
			getBoard().getActivePlayer().getField().getHand().remove(0);
		}

		while (!getBoard().getOpponentPlayer().getField().getHand().isEmpty()) {
			getBoard()
					.getOpponentPlayer()
					.getField()
					.getGraveyard()
					.add(getBoard().getOpponentPlayer().getField().getHand()
							.get(0));
			getBoard().getOpponentPlayer().getField().getHand().get(0)
					.setLocation(Location.GRAVEYARD);
			getBoard().getOpponentPlayer().getField().getHand().remove(0);
		}

		getBoard().getActivePlayer().getField().addNCardsToHand(drawActive);
		getBoard().getOpponentPlayer().getField().addNCardsToHand(drawOpp);
	}

}
