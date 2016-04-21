package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.listeners.HandListener;

public class HandArea extends JScrollPane {
	private ArrayList<HandCard> handCards;
	private HandListener handListener;
	private JPanel cardPanel;

	public ArrayList<HandCard> getHandCards() {
		return handCards;
	}

	public HandListener getHandListener() {
		return handListener;
	}

	public void setHandListener(HandListener handListener) {
		this.handListener = handListener;
	}

	public HandArea() {
		super();
		handCards = new ArrayList<HandCard>();

		cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout());
		cardPanel.setOpaque(false);
		cardPanel.setSize(new Dimension(670, 111));

		setViewportView(cardPanel);
		getViewport().setOpaque(false);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		setSize(new Dimension(670, 111));
	}

	public void addCard(Card c) {
		HandCard card = new HandCard(c);
		card.addActionListener(handListener);
		card.addMouseListener(handListener);
		handCards.add(card);
		cardPanel.add(card);
		updateUI();
	}

	public void addNCards(ArrayList<Card> c) {
		for (Card x : c)
			addCard(x);
	}

	public void removeCard(JButton b) {
		cardPanel.remove(b);
		updateUI();
	}

	public void removeAllCards() {
		for (JButton b : handCards)
			removeCard(b);
		handCards.clear();
	}

	public void hideCards(boolean flipped) {
		for (HandCard n : handCards) {
			n.hideCard(flipped);
		}
	}

	public void convertToLabels() {
		for (HandCard h : handCards) {
			JLabel l = buttonToLabel(h);
			cardPanel.remove(h);
			cardPanel.add(l);
		}
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		return l;
	}

}
