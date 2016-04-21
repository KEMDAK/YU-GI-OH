package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.listeners.GameListener;

public class PhasePanel extends JPanel {
	private JLabel phaselabel;
	private JButton endphase, endTurn;

	public PhasePanel() {
		super();
		setSize(new Dimension(65, 210));
		setLayout(new GridLayout(3, 1));
		setOpaque(false);

		phaselabel = new JLabel();
		endphase = new JButton();
		endphase.setIcon(new ImageIcon("Endphase.png"));
		endphase.setContentAreaFilled(false);
		// endphase.setBorderPainted(false);
		endphase.setOpaque(false);
		endTurn = new JButton();
		endTurn.setIcon(new ImageIcon("Endturn.png"));
		endTurn.setContentAreaFilled(false);
		// endTurn.setBorderPainted(false);
		endTurn.setOpaque(false);

		add(phaselabel);
		add(endphase);
		add(endTurn);

	}

	public JLabel getPhaselabel() {
		return phaselabel;
	}

	public JButton getEndphase() {
		return endphase;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public void assignListeners(GameListener g) {
		endphase.addMouseListener(g);
		endTurn.addMouseListener(g);
	}

	public void convertTolabels() {
		JLabel EndPhaseLabel = buttonToLabel(endphase);
		remove(endphase);
		add(EndPhaseLabel);

		JLabel EndTurnLabel = buttonToLabel(endTurn);
		remove(endTurn);
		add(EndTurnLabel);
	}

	public JLabel buttonToLabel(JButton b) {
		JLabel l = new JLabel();
		l.setIcon(b.getIcon());
		l.setLocation(b.getLocation());
		return l;
	}
}
