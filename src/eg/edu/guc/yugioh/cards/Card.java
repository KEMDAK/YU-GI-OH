package eg.edu.guc.yugioh.cards;

import eg.edu.guc.yugioh.board.Board;

abstract public class Card {

	private String name, description;
	private boolean isHidden;
	private Location location;
	private static Board board = new Board();

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public Location getLocation() {
		return location;
	}

	public static Board getBoard() {
		return board;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public static void setBoard(Board boar) {
		board = boar;
	}

	public Card(String name, String description) {
		this.name = name;
		this.description = description;
		isHidden = true;
		location = Location.DECK;
	}

	abstract public void action(MonsterCard m);

}
