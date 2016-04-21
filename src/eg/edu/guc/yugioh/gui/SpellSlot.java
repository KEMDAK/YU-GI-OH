package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.listeners.FieldListener;

public class SpellSlot extends JLabel {
	private JPanel spellPanel;
	private FieldListener fieldlistener;

	public FieldListener getFieldlistener() {
		return fieldlistener;
	}

	public void setFieldlistener(FieldListener fieldlistener) {
		this.fieldlistener = fieldlistener;
	}

	public SpellSlot() {
		super(new ImageIcon("Card slot.jpg"));
		setPreferredSize(new Dimension(130, 115));
		setLayout(new GridBagLayout());
	}

	public void addSpell(SpellCard c, boolean mirrored) {
		spellPanel = new JPanel();
		JButton s = new JButton();
		String path = "";
		if (mirrored)
			path += " flipped";
		if (c.isHidden()) {
			path = "Cards/Hidden spell field" + path + ".jpg";
			s.setIcon(new ImageIcon(path));
		} else {
			path = "Cards/" + c.getName() + path + ".jpg";
			s.setIcon(new ImageIcon(path));
		}
		spellPanel.setLayout(new BorderLayout());
		s.setPreferredSize(new Dimension(80, 112));
		add(spellPanel);
		s.setName(c.getName());
		s.addActionListener(fieldlistener);
		s.addMouseListener(fieldlistener);
		spellPanel.add(s, BorderLayout.CENTER);
	}

	public void removeSpell() {
		remove(spellPanel);
		spellPanel = null;
	}

	public JPanel getSpellPanel() {
		return spellPanel;
	}

	public boolean isEmpty() {
		return spellPanel == null;
	}

	public void convertToLabel() {
		JLabel l = buttonToLabel((JButton) spellPanel.getComponents()[0]);
		removeSpell();
		add(l);
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		return l;
	}

}
