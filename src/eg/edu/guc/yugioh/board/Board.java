package eg.edu.guc.yugioh.board;

import eg.edu.guc.yugioh.board.player.Phase;
import eg.edu.guc.yugioh.board.player.Player;
import eg.edu.guc.yugioh.cards.Card;

public class Board {

	private Player activePlayer;
	private Player opponentPlayer;
	private Player winner;
	private boolean ContinuePlaying;

	public Player getActivePlayer() {
		return activePlayer;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public Player getWinner() {
		return winner;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public boolean getContinuePlaying() {
		return ContinuePlaying;
	}

	public void setContinuePlaying(boolean continuePlaying) {
		ContinuePlaying = continuePlaying;
	}

	public Board() {
		Card.setBoard(this);
		ContinuePlaying = true;
	}

	public void whoStarts(Player p1, Player p2) {
		int x = (int) (Math.random() * 2);
		if (x == 0) {
			activePlayer = p1;
			opponentPlayer = p2;
		} else {
			activePlayer = p2;
			opponentPlayer = p1;
		}
	}

	public void startGame(Player p1, Player p2) {
		p1.getField().addNCardsToHand(5);
		p2.getField().addNCardsToHand(5);
		whoStarts(p1, p2);
		activePlayer.getField().setPhase(Phase.MAIN1);
		Player.setCanIAddMonster(true);
		activePlayer.addCardToHand();
	}

	public void nextPlayer() {
		Player temp = activePlayer;
		activePlayer = opponentPlayer;
		opponentPlayer = temp;
		activePlayer.getField().setPhase(Phase.MAIN1);
		activePlayer.getField().resetCanAttack();
		activePlayer.getField().resetCanSwitchMode();
		Player.setCanIAddMonster(true);
		activePlayer.addCardToHand();

	}

}
