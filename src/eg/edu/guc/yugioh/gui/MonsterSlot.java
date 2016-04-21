package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.listeners.FieldListener;

public class MonsterSlot extends JLabel {
	private JPanel monsterPanel;
	private FieldListener fieldlistener;
	private MonsterCard card;

	public MonsterCard getCard() {
		return card;
	}

	public void setCard(MonsterCard card) {
		this.card = card;
	}

	public FieldListener getFieldlistener() {
		return fieldlistener;
	}

	public void setFieldlistener(FieldListener fieldlistener) {
		this.fieldlistener = fieldlistener;
	}

	public JPanel getMonsterPanel() {
		return monsterPanel;
	}

	public MonsterSlot() {
		super(new ImageIcon("Card slot.jpg"));
		setPreferredSize(new Dimension(130, 115));
		setLayout(new GridBagLayout());
	}

	public void addMonster(MonsterCard c, boolean mirrored) {
		monsterPanel = new JPanel();
		JButton m = new JButton();
		card = c;
		String path = "";
		if (mirrored)
			path += " flipped";
		if (c.getMode() == Mode.ATTACK) {
			m.setPreferredSize(new Dimension(80, 112));
			path = "Cards/" + c.getName() + path + ".jpg";
			m.setIcon(new ImageIcon(path));
		} else {
			m.setPreferredSize(new Dimension(112, 80));
			path = "Cards/Hidden monster field" + path + ".jpg";
			m.setIcon(new ImageIcon(path));
		}
		monsterPanel.setLayout(new BorderLayout());
		add(monsterPanel);
		m.setName(c.getName());
		m.addActionListener(fieldlistener);
		m.addMouseListener(fieldlistener);
		monsterPanel.add(m, BorderLayout.CENTER);
	}

	public void removeMonster() {
		remove(monsterPanel);
		monsterPanel = null;
		card = null;
	}

	public boolean isEmpty() {
		return monsterPanel == null;
	}

	public void convertToLabel() {
		JLabel l = buttonToLabel((JButton) monsterPanel.getComponents()[0]);
		removeMonster();
		add(l);
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		return l;
	}

}
