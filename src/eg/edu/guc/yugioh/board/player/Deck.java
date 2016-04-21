package eg.edu.guc.yugioh.board.player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.CardDestruction;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.DarkHole;
import eg.edu.guc.yugioh.cards.spells.GracefulDice;
import eg.edu.guc.yugioh.cards.spells.HarpieFeatherDuster;
import eg.edu.guc.yugioh.cards.spells.HeavyStorm;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.MonsterReborn;
import eg.edu.guc.yugioh.cards.spells.PotOfGreed;
import eg.edu.guc.yugioh.cards.spells.Raigeki;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.EmptyFieldException;
import eg.edu.guc.yugioh.exceptions.MissingFieldException;
import eg.edu.guc.yugioh.exceptions.UnexpectedFormatException;
import eg.edu.guc.yugioh.exceptions.UnknownCardTypeException;
import eg.edu.guc.yugioh.exceptions.UnknownSpellCardException;

public class Deck {

	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;
	private ArrayList<Card> deck;
	private static int tries;
	private BufferedReader br;
	private static String monstersPath = "Database-Monsters.csv";
	private static String spellsPath = "Database-Spells.csv";

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public void setMonsters(ArrayList<Card> monsters) {
		Deck.monsters = monsters;
	}

	public void setSpells(ArrayList<Card> spells) {
		Deck.spells = spells;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Deck() throws IOException, UnexpectedFormatException {
		tries = 1;
		monsters = new ArrayList<Card>(30);
		spells = new ArrayList<Card>(10);
		deck = new ArrayList<Card>(20);
		br = new BufferedReader(new InputStreamReader(System.in));
		monsters = loadCardsFromFile(monstersPath);
		spells = loadCardsFromFile(spellsPath);
		buildDeck();
		shuffleDeck();
	}

	public ArrayList<Card> loadCardsFromFile(String path) throws IOException,
			UnexpectedFormatException {
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			ArrayList<Card> res = new ArrayList<Card>();
			String line = "";
			int lineNumber = 1;
			while ((line = in.readLine()) != null) {
				String[] card = line.split(",");
				if (card[0].equals("Monster")) {
					if (card.length < 6) {
						in.close();
						throw new MissingFieldException(path, lineNumber);
					}
					for (int i = 0; i < card.length; i++) {
						boolean empty = true;
						for (int j = 0; j < card[i].length() && empty; j++) {
							if (card[i].charAt(j) != ' ')
								empty = false;
						}
						if (empty) {
							in.close();
							throw new EmptyFieldException(path, lineNumber,
									i + 1);
						}
					}
					res.add(new MonsterCard(card[1], card[2], Integer
							.parseInt(card[5]), Integer.parseInt(card[3]),
							Integer.parseInt(card[4])));
				} else if (card[0].equals("Spell")) {
					if (card.length < 3) {
						in.close();
						throw new MissingFieldException(path, lineNumber);
					}
					for (int i = 0; i < card.length; i++) {
						boolean empty = true;
						for (int j = 0; j < card[i].length() && empty; j++) {
							if (card[i].charAt(j) != ' ')
								empty = false;
						}
						if (empty) {
							in.close();
							throw new EmptyFieldException(path, lineNumber,
									i + 1);
						}
					}
					switch (card[1]) {
					case "Card Destruction":
						res.add(new CardDestruction(card[1], card[2]));
						break;
					case "Change Of Heart":
						res.add(new ChangeOfHeart(card[1], card[2]));
						break;
					case "Dark Hole":
						res.add(new DarkHole(card[1], card[2]));
						break;
					case "Graceful Dice":
						res.add(new GracefulDice(card[1], card[2]));
						break;
					case "Harpie's Feather Duster":
						res.add(new HarpieFeatherDuster(card[1], card[2]));
						break;
					case "Heavy Storm":
						res.add(new HeavyStorm(card[1], card[2]));
						break;
					case "Mage Power":
						res.add(new MagePower(card[1], card[2]));
						break;
					case "Monster Reborn":
						res.add(new MonsterReborn(card[1], card[2]));
						break;
					case "Pot of Greed":
						res.add(new PotOfGreed(card[1], card[2]));
						break;
					case "Raigeki":
						res.add(new Raigeki(card[1], card[2]));
						break;
					default:
						in.close();
						throw new UnknownSpellCardException(path, lineNumber,
								card[1]);
					}
				} else {
					in.close();
					throw new UnknownCardTypeException(path, lineNumber,
							card[0]);
				}
				lineNumber++;
			}
			in.close();
			return res;
		} catch (FileNotFoundException e) {
			if (tries <= 3) {
				tries++;
				System.out.println("The file was not found");
				System.out.println("Please enter a correct path: ");
				String newPath = br.readLine();
				if (path == monstersPath) {
					monstersPath = newPath;
				} else {
					spellsPath = newPath;
				}
				return loadCardsFromFile(newPath);
			} else {
				tries = 1;
				throw e;
			}
		} catch (UnexpectedFormatException e) {
			if (tries <= 3) {
				tries++;
				System.out.println(e.getMessage());
				System.out.println("Please enter a correct path: ");
				String newPath = br.readLine();
				if (path == monstersPath) {
					monstersPath = newPath;
				} else {
					spellsPath = newPath;
				}
				return loadCardsFromFile(newPath);
			} else {
				tries = 1;
				throw e;
			}
		}

	}

	private void buildDeck() {
		for (int i = 1; i <= 15; i++) {
			int index = (int) (Math.random() * monsters.size());
			MonsterCard x = (MonsterCard) monsters.get(index);
			MonsterCard res = new MonsterCard(x.getName(), x.getDescription(),
					x.getLevel(), x.getAttackPoints(), x.getDefensePoints());
			res.setLocation(Location.DECK);
			deck.add(res);
		}
		for (int i = 1; i <= 5; i++) {
			int index = (int) (Math.random() * spells.size());
			SpellCard x = (SpellCard) spells.get(index);
			switch (x.getName()) {
			case "Card Destruction":
				CardDestruction res = new CardDestruction(x.getName(),
						x.getDescription());
				res.setLocation(Location.DECK);
				deck.add(res);
				break;
			case "Change Of Heart":
				ChangeOfHeart res1 = new ChangeOfHeart(x.getName(),
						x.getDescription());
				res1.setLocation(Location.DECK);
				deck.add(res1);
				break;
			case "Dark Hole":
				DarkHole res2 = new DarkHole(x.getName(), x.getDescription());
				res2.setLocation(Location.DECK);
				deck.add(res2);
				break;
			case "Graceful Dice":
				GracefulDice res3 = new GracefulDice(x.getName(),
						x.getDescription());
				res3.setLocation(Location.DECK);
				deck.add(res3);
				break;
			case "Harpie's Feather Duster":
				HarpieFeatherDuster res4 = new HarpieFeatherDuster(x.getName(),
						x.getDescription());
				res4.setLocation(Location.DECK);
				deck.add(res4);
				break;
			case "Heavy Storm":
				HeavyStorm res5 = new HeavyStorm(x.getName(),
						x.getDescription());
				res5.setLocation(Location.DECK);
				deck.add(res5);
				break;
			case "Mage Power":
				MagePower res6 = new MagePower(x.getName(), x.getDescription());
				res6.setLocation(Location.DECK);
				deck.add(res6);
				break;
			case "Monster Reborn":
				MonsterReborn res7 = new MonsterReborn(x.getName(),
						x.getDescription());
				res7.setLocation(Location.DECK);
				deck.add(res7);
				break;
			case "Pot of Greed":
				PotOfGreed res8 = new PotOfGreed(x.getName(),
						x.getDescription());
				res8.setLocation(Location.DECK);
				deck.add(res8);
				break;
			case "Raigeki":
				Raigeki res9 = new Raigeki(x.getName(), x.getDescription());
				res9.setLocation(Location.DECK);
				deck.add(res9);
				break;
			}
		}
	}

	private void shuffleDeck() {
		ArrayList<Card> temp = new ArrayList<>();
		for (int i = 20; i >= 1; i--) {
			temp.add(deck.remove(((int) (Math.random() * i))));
		}
		deck = temp;
	}

	public ArrayList<Card> drawNCards(int n) {
		ArrayList<Card> res = new ArrayList<Card>();
		for (int i = 0; i < n; i++) {
			res.add(drawOneCard());
		}
		return res;
	}

	public Card drawOneCard() {
		if (!deck.isEmpty())
			return deck.remove(0);
		else {
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			Card.getBoard().setContinuePlaying(false);
			return null;
		}

	}

	public static void main(String[] args) {
		int x = 0;
		try {
			x = 2;
		} catch (Exception e) {
			System.out.println(x);
		}
	}
}
