package eg.edu.guc.yugioh.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import eg.edu.guc.yugioh.listeners.GameListener;

public class Game extends JLabel {
	private GameBoard gameBoard;
	private InfoAreaActive infoAreaActive;
	private InfoAreaOpponent infoAreaOpponent;
	private CardOverview cardOverview;
	private HandArea handAreaActive, handAreaOpponent;
	private GraveyardButton graveyardButtonActive, graveyardButtonOpponent;
	private DeckLabel deckLabelActive, deckLabelOpponent;
	private PhasePanel activePhase, opponentPhase;
	private JLabel deckSizeActive, deckSizeOpponent;
	private JLabel ATKgain, DEFgain;
	private GameOver gameover;
	private HighButton high;
	private LowButton low;
	private MuteButton mute;

	public Game() {
		super(new ImageIcon("board image.jpg"));
		setSize(new Dimension(1280, 720));

		gameover = new GameOver();
		add(gameover);
		gameover.setSize(1280, 720);
		gameover.setLocation(0, 0);
		gameover.setVisible(false);

		gameBoard = new GameBoard();
		add(gameBoard);
		gameBoard.setLocation(510, 121);

		infoAreaActive = new InfoAreaActive();
		add(infoAreaActive);
		infoAreaActive.setLocation(5, 555);

		infoAreaOpponent = new InfoAreaOpponent();
		add(infoAreaOpponent);
		infoAreaOpponent.setLocation(5, 7);

		cardOverview = new CardOverview();
		add(cardOverview);
		cardOverview.setLocation(15, 140);

		handAreaActive = new HandArea();
		add(handAreaActive);
		handAreaActive.setLocation(500, 574);

		handAreaOpponent = new HandArea();
		add(handAreaOpponent);
		handAreaOpponent.setLocation(500, 0);

		graveyardButtonActive = new GraveyardButton();
		add(graveyardButtonActive);
		graveyardButtonActive.setLocation(1180, 355);

		graveyardButtonOpponent = new GraveyardButton();
		add(graveyardButtonOpponent);
		graveyardButtonOpponent.setLocation(410, 225);

		deckLabelActive = new DeckLabel();
		add(deckLabelActive);
		deckLabelActive.setLocation(1180, 475);

		deckLabelOpponent = new DeckLabel();
		add(deckLabelOpponent);
		deckLabelOpponent.setIcon(new ImageIcon("Deck slot flipped.jpg"));
		deckLabelOpponent.setLocation(410, 105);

		activePhase = new PhasePanel();
		add(activePhase);
		activePhase.setLocation(417, 360);

		opponentPhase = new PhasePanel();
		add(opponentPhase);
		opponentPhase.setLocation(1187, 125);

		JLabel deckSizeActiveBackground = new JLabel(new ImageIcon(
				"Deck size.jpg"));
		deckSizeActiveBackground.setSize(new Dimension(25, 25));
		deckSizeActiveBackground.setLocation(1208, 588);
		add(deckSizeActiveBackground);
		deckSizeActive = new JLabel("20", SwingConstants.CENTER);
		deckSizeActiveBackground.add(deckSizeActive);
		deckSizeActive.setSize(new Dimension(25, 25));
		deckSizeActive.setFont(new Font(null, 1, 20));
		deckSizeActive.setForeground(Color.WHITE);

		JLabel deckSizeOpponentBackground = new JLabel(new ImageIcon(
				"Deck size.jpg"));
		deckSizeOpponentBackground.setSize(new Dimension(25, 25));
		deckSizeOpponentBackground.setLocation(435, 80);
		add(deckSizeOpponentBackground);
		deckSizeOpponent = new JLabel("20", SwingConstants.CENTER);
		deckSizeOpponentBackground.add(deckSizeOpponent);
		deckSizeOpponent.setSize(new Dimension(25, 25));
		deckSizeOpponent.setFont(new Font(null, 1, 20));
		deckSizeOpponent.setForeground(Color.WHITE);

		ATKgain = new JLabel();
		ATKgain.setHorizontalAlignment(SwingConstants.CENTER);
		add(ATKgain);
		ATKgain.setSize(new Dimension(90, 150));
		ATKgain.setFont(new Font(null, 1, 30));
		ATKgain.setLocation(320, 100);
		ATKgain.setForeground(Color.GREEN);
		ATKgain.setVisible(false);
		ATKgain.setName("" + 0);

		DEFgain = new JLabel();
		DEFgain.setHorizontalAlignment(SwingConstants.CENTER);
		add(DEFgain);
		DEFgain.setSize(new Dimension(90, 150));
		DEFgain.setFont(new Font(null, 1, 30));
		DEFgain.setLocation(320, 430);
		DEFgain.setForeground(Color.BLACK);
		DEFgain.setVisible(false);
		DEFgain.setName("" + 0);

		mute = new MuteButton();
		add(mute);
		mute.setLocation(340, 640);

		high = new HighButton();
		add(high);
		high.setLocation(372, 640);

		low = new LowButton();
		add(low);
		low.setLocation(404, 640);

	}

	public GameOver getGameover() {
		return gameover;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public InfoAreaActive getInfoAreaActive() {
		return infoAreaActive;
	}

	public InfoAreaOpponent getInfoAreaOpponent() {
		return infoAreaOpponent;
	}

	public CardOverview getCardOverview() {
		return cardOverview;
	}

	public HandArea getHandAreaActive() {
		return handAreaActive;
	}

	public HandArea getHandAreaOpponent() {
		return handAreaOpponent;
	}

	public GraveyardButton getGraveyardButtonActive() {
		return graveyardButtonActive;
	}

	public GraveyardButton getGraveyardButtonOpponent() {
		return graveyardButtonOpponent;
	}

	public DeckLabel getDeckLabelActive() {
		return deckLabelActive;
	}

	public DeckLabel getDeckLabelOpponent() {
		return deckLabelOpponent;
	}

	public PhasePanel getActivePhase() {
		return activePhase;
	}

	public PhasePanel getOpponentPhase() {
		return opponentPhase;
	}

	public JLabel getDeckSizeActive() {
		return deckSizeActive;
	}

	public JLabel getDeckSizeOpponent() {
		return deckSizeOpponent;
	}

	public JLabel getATKgain() {
		return ATKgain;
	}

	public JLabel getDEFgain() {
		return DEFgain;
	}

	public HighButton getHigh() {
		return high;
	}

	public LowButton getLow() {
		return low;
	}

	public MuteButton getMute() {
		return mute;
	}

	public void setDEFgain(JLabel dEFgain) {
		DEFgain = dEFgain;
	}

	public void setATKgain(JLabel aTKgain) {
		ATKgain = aTKgain;
	}

	public void assignListeners(GameListener g) {
		graveyardButtonActive.addActionListener(g);
		graveyardButtonOpponent.addActionListener(g);
	}

	public void convertToLabels() {
		JLabel graveyardButtonActivelabel = buttonToLabel(graveyardButtonActive);
		remove(graveyardButtonActive);
		add(graveyardButtonActivelabel);

		JLabel graveyardButtonOpponentlabel = buttonToLabel(graveyardButtonOpponent);
		remove(graveyardButtonOpponent);
		add(graveyardButtonOpponentlabel);

		activePhase.convertTolabels();

		opponentPhase.convertTolabels();

		gameBoard.getPlayerFieldActive().convertToLabels();

		gameBoard.getPlayerFieldOpponent().convertToLabels();

		handAreaActive.convertToLabels();

		handAreaOpponent.convertToLabels();
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		l.setSize(b.getSize());
		return l;
	}

}
